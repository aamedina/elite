(ns net.wikipunk.elite
  (:require
   [clojure.data.json :as json]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clojure.tools.logging :as log]
   [clojure.walk :as walk]
   [datomic.client.api :as d]
   [net.wikipunk.rdf.elite]))

(def vocab
  (-> (group-by :db/ident (map deref (vals (ns-publics 'net.wikipunk.rdf.elite))))
      (update-vals first)
      (update-keys name)))

(defn systems
  ([^java.io.File file]
   (println (.getName file))
   (with-open [r (io/reader file)]
     (->> (line-seq r)
          (pmap (fn [s] (try (json/read-str s :key-fn (fn [k] (get-in vocab [k :db/ident] k)))
                             (catch Throwable _ nil))))
          (filter some?)        
          (filter :elite/id64)
          (filter (comp seq :elite/bodies))
          (pmap #(select-keys % [:elite/coords :elite/bodies :elite/id64]))
          (pmap #(update % :elite/coords (fn [{:strs [x y z]}] [x y z])))
          (pmap #(update % :elite/bodies
                         (fn [bodies]
                           (let [index (group-by :elite/bodyId bodies)]
                             (vec (pmap (fn [body]
                                          (cond-> (dissoc (assoc body :db/id (str (:elite/id64 body)))
                                                          "stations" "updateTime" "name" "signals"
                                                          "terraformingState" "isLandable")
                                            (:elite/belts body)
                                            (update :elite/belts (fn [belts]
                                                                   (into []
                                                                         (comp
                                                                           (map (fn [belt]
                                                                                  (-> belt
                                                                                      (update :elite/signals
                                                                                              :elite/signals)
                                                                                      (update :elite/signals
                                                                                              (fn [m]
                                                                                                (not-empty (into []
                                                                                                                 (map
                                                                                                                   (fn [[k v]]
                                                                                                                     [k (double v)]))
                                                                                                                 (seq m)))))
                                                                                      (dissoc "name")))))
                                                                         belts)))
                                            (:elite/rings body)
                                            (update :elite/rings (fn [rings]
                                                                   (into []
                                                                         (comp
                                                                           (map (fn [ring]
                                                                                  (-> ring
                                                                                      (update :elite/signals
                                                                                              :elite/signals)
                                                                                      (update :elite/signals
                                                                                              (fn [m]
                                                                                                (not-empty (into []
                                                                                                                 (map
                                                                                                                   (fn [[k v]]
                                                                                                                     [k (double v)]))
                                                                                                                 (seq m)))))
                                                                                      (dissoc "name")))))
                                                                         rings)))
                                            (:elite/parents body)
                                            (update :elite/parents (fn [parents]
                                                                     (not-empty
                                                                       (into []
                                                                             (comp
                                                                               (mapcat identity)
                                                                               (keep (fn [[k v]]
                                                                                       (some-> (get index v)
                                                                                               (first)
                                                                                               (:elite/id64)
                                                                                               (str)))))
                                                                             parents))))
                                            (:elite/signals body)
                                            (-> (update :elite/signals :elite/signals)
                                                (update :elite/signals (fn [m] (into []
                                                                                     (map (fn [[k v]]
                                                                                            [k (double v)]))
                                                                                     (seq m)))))

                                            (:elite/atmosphereComposition body)
                                            (update :elite/atmosphereComposition seq)

                                            (:elite/solidComposition body)
                                            (update :elite/solidComposition seq)

                                            (:elite/materials body)
                                            (update :elite/materials seq)))
                                        bodies))))))
          (pmap #(walk/postwalk (fn [form]
                                  (if (map? form)
                                    (reduce-kv (fn [m k v]
                                                 (if (nil? v)
                                                   m
                                                   (case k
                                                     (assoc m k v))))
                                               {} form)
                                    form))
                                %))
          (doall)))))



(defn bootstrap
  [component dir]
  (->> (file-seq (io/file dir))
       (filter #(.isFile %))
       (sort-by #(.getName %))
       (mapcat systems)
       (partition-all 224)
       (pmap #(try
                (d/transact component {:tx-data %})
                (catch Throwable ex
                  (log/error ex))))
       (take-while (complement nil?))
       (dorun)))

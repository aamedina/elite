(ns net.wikipunk.elite
  (:require
   [clojure.data.json :as json]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [net.wikipunk.rdf.elite]))

[:coords :bodies :id64]

(def vocab
  (-> (group-by :db/ident (map deref (vals (ns-publics 'net.wikipunk.rdf.elite))))
      (update-vals first)
      (update-keys name)))

(defn systems
  [reader]
  (->> (line-seq reader)
       (keep (fn [s] (try (json/read-str s :key-fn (fn [k] (get-in vocab [k :db/ident] k)))
                          (catch Throwable _ nil))))
       (map #(select-keys % [:elite/coords :elite/bodies :elite/id64]))
       (filter :elite/id64)
       (filter (comp seq :elite/bodies))
       (map #(assoc % :db/id "0"))
       (map #(update % :elite/coords (fn [{:strs [x y z]}] [x y z])))
       (map #(update % :elite/bodies (fn [bodies]
                                       (mapv (fn [body]
                                               (cond-> (dissoc (assoc body :db/id (str (:elite/bodyId body)))
                                                               "stations" "updateTime" "name" "signals"
                                                               "terraformingState")
                                                 (:elite/belts body)
                                                 (update :elite/belts (fn [belts]
                                                                        (mapv (fn [belt]
                                                                                (dissoc belt "name"))
                                                                              belts)))
                                                 (:elite/rings body)
                                                 (update :elite/rings (fn [belts]
                                                                        (mapv (fn [belt]
                                                                                (dissoc belt "name"))
                                                                              belts)))
                                                 (:elite/parents body)
                                                 (update :elite/parents (fn [parents]
                                                                          (into []
                                                                                (comp
                                                                                  (mapcat vals)
                                                                                  (map (fn [id]
                                                                                         {:db/id (str id)})))
                                                                                parents)))))
                                             bodies))))))



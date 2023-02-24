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

(defn sample-bootstrap
  [component dir prob]
  (->> (file-seq (io/file dir))
       (filter #(.isFile %))
       (sort-by #(.getName %))
       (random-sample prob)
       (mapcat systems)
       (partition-all 224)
       (pmap #(try
                (d/transact component {:tx-data %})
                (catch Throwable ex
                  (log/error ex))))
       (take-while (complement nil?))
       (dorun)))

(def planet-subtypes
  #{"Class II gas giant"
    "Class IV gas giant"
    "Rocky body"
    "Gas giant with ammonia-based life"
    "Rocky Ice world"
    "Metal-rich body"
    "Helium-rich gas giant"
    "Gas giant with water-based life"
    "Earth-like world"
    "Water giant"
    "Icy body"
    "Helium gas giant"
    "Ammonia world"
    "Class I gas giant"
    "Class V gas giant"
    "Class III gas giant"
    "Water world"
    "High metal content world"})

(def star-subtypes
  #{"Y (Brown dwarf) Star"
    "Wolf-Rayet O Star"
    "White Dwarf (DBZ) Star"
    "C Star"
    "B (Blue-White) Star"
    "White Dwarf (DAV) Star"
    "K (Yellow-Orange giant) Star"
    "White Dwarf (D) Star"
    "Wolf-Rayet NC Star"
    "L (Brown dwarf) Star"
    "M (Red dwarf) Star"
    "K (Yellow-Orange) Star"
    "M (Red super giant) Star"
    "O (Blue-White) Star"
    "M (Red giant) Star"
    "White Dwarf (DQ) Star"
    "G (White-Yellow super giant) Star"
    "Black Hole"
    "White Dwarf (DC) Star"
    "F (White) Star"
    "Wolf-Rayet N Star"
    "A (Blue-White super giant) Star"
    "MS-type Star"
    "CN Star"
    "White Dwarf (DA) Star"
    "S-type Star"
    "CJ Star"
    "F (White super giant) Star"
    "T Tauri Star"
    "Supermassive Black Hole"
    "White Dwarf (DAZ) Star"
    "White Dwarf (DBV) Star"
    "T (Brown dwarf) Star"
    "Herbig Ae/Be Star"
    "White Dwarf (DCV) Star"
    "Neutron Star"
    "A (Blue-White) Star"
    "Wolf-Rayet C Star"
    "G (White-Yellow) Star"
    "Wolf-Rayet Star"
    "White Dwarf (DAB) Star"
    "B (Blue-White super giant) Star"
    "White Dwarf (DB) Star"})

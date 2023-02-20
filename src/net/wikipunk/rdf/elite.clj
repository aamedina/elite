(ns net.wikipunk.rdf.elite
  "RDF vocabulary for Elite"
  {:rdf/type :owl/Ontology}
  (:refer-clojure :exclude [parents type]))

(def ObjectClass
  {:db/ident :elite/Object
   :rdf/type :owl/Class})

(def SystemClass
  {:db/ident        :elite/System
   :rdf/type        :owl/Class
   :rdfs/subClassOf :elite/Object})

(def Body
  {:db/ident        :elite/Body
   :rdf/type        :owl/Class
   :rdfs/subClassOf :elite/Object})

(def Star
  {:db/ident        :elite/Star
   :rdf/type        :owl/Class
   :rdfs/subClassOf :elite/Body})

(def Planet
  {:db/ident        :elite/Planet
   :rdf/type        :owl/Class
   :rdfs/subClassOf :elite/Body})

(def Ring
  {:db/ident        :elite/Ring
   :rdf/type        :owl/Class
   :rdfs/subClassOf :elite/Object})

(def Belt
  {:db/ident        :elite/Belt
   :rdf/type        :owl/Class
   :rdfs/subClassOf :elite/Object})

(def Barycentre
  "Barycentre"
  {:db/ident        :elite/Barycentre
   :rdf/type        :owl/Class
   :rdfs/subClassOf :elite/Body})

(def id64
  {:db/ident       :elite/id64
   :db/valueType   :db.type/long
   :db/cardinality :db.cardinality/one
   :db/unique      :db.unique/identity
   :rdf/type       :owl/ObjectProperty
   :rdfs/domain    :elite/Object})

(def coords
  {:db/ident       :elite/coords
   :db/valueType   :db.type/tuple
   :db/cardinality :db.cardinality/one
   :db/tupleTypes  [:db.type/double
                    :db.type/double
                    :db.type/double]
   :rdf/type       :owl/ObjectProperty
   :rdfs/domain    :elite/System})

(def absoluteMagnitude
  "absoluteMagnitude"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/absoluteMagnitude,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def age
  "age"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/age,
   :db/valueType   :db.type/long,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def argOfPeriapsis
  "argOfPeriapsis"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/argOfPeriapsis,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def ascendingNode
  "ascendingNode"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/ascendingNode,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def atmosphereComposition
  "atmosphereComposition"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :elite/atmosphereComposition,
   :db/valueType   :db.type/tuple,
   :db/tupleTypes  [:db.type/string :db.type/double]
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def atmosphereType
  "atmosphereType"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/atmosphereType,
   :db/isComponent true,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Planet})

(def axialTilt
  "axialTilt"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/axialTilt,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def belts
  "belts"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :elite/belts,
   :db/isComponent true,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def bodies
  "bodies"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :elite/bodies,
   :db/isComponent true,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/System})

(def bodyId
  "bodyId"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/bodyId,
   :db/valueType   :db.type/long,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def distanceToArrival
  "distanceToArrival"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/distanceToArrival,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def earthMasses
  "earthMasses"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/earthMasses,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def gravity
  "gravity"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/gravity,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def luminosity
  "luminosity"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/luminosity,
   :db/isComponent true,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def mainStar
  "mainStar"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/mainStar,
   :db/valueType   :db.type/boolean,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Star})

(def materials
  "materials"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :elite/materials,
   :db/valueType   :db.type/tuple,
   :db/tupleTypes  [:db.type/string :db.type/double]
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def meanAnomaly
  "meanAnomaly"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/meanAnomaly,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def orbitalEccentricity
  "orbitalEccentricity"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/orbitalEccentricity,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def orbitalInclination
  "orbitalInclination"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/orbitalInclination,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def orbitalPeriod
  "orbitalPeriod"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/orbitalPeriod,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def parents
  "parents"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :elite/parents,
   :db/valueType   :db.type/tuple,
   :db/tupleTypes  [:db.type/string :db.type/long]
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def radius
  "radius"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/radius,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def reserveLevel
  "reserveLevel"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/reserveLevel,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def rings
  "rings"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :elite/rings,
   :db/isComponent true,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def rotationalPeriod
  "rotationalPeriod"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/rotationalPeriod,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def rotationalPeriodTidallyLocked
  "rotationalPeriodTidallyLocked"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/rotationalPeriodTidallyLocked,
   :db/valueType   :db.type/boolean,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def semiMajorAxis
  "semiMajorAxis"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/semiMajorAxis,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def signals
  "signals"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :elite/signals,
   :db/isComponent true,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def solarMasses
  "solarMasses"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/solarMasses,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Star})

(def solarRadius
  "solarRadius"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/solarRadius,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Star})

(def solidComposition
  "solidComposition"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :elite/solidComposition,
   :db/valueType   :db.type/tuple,
   :db/tupleTypes  [:db.type/string :db.type/double]
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Planet})

(def spectralClass
  "spectralClass"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/spectralClass,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Star})

(def subType
  "subType"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/subType,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def surfacePressure
  "surfacePressure"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/surfacePressure,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Planet})

(def surfaceTemperature
  "surfaceTemperature"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/surfaceTemperature,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def type
  "type"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/type,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

(def volcanismType
  "volcanismType"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :elite/volcanismType,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :elite/Body})

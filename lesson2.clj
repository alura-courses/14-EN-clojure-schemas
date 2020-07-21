(ns hospital3.lesson2
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

;(s/defrecord Patient
;;  [id :- Long, name :- s/Str])
;;
;;(pprint (Patient. 15 "William"))
;;(pprint (Patient. "15" "William"))
;;(pprint (->Patient 15 "William"))
;;(pprint (->Patient "15" "William"))
;;(pprint (map->Patient {15 "William"}))
;;(pprint (map->Patient {"15" "William"}))

(def Patient
  "Schema for a Patient"
  {:id s/Num, :name s/Str}
  )

(pprint (s/explain Patient))

(pprint (s/validate Patient {:id 15, :name "William"}))
;(pprint (s/validate Patient {:id 15, :neme "William"}))
;(pprint (s/validate Patient {:id 15, :name "William", :insurance [:x-ray]}))
;(pprint (s/validate Patient {:id 15}))

(s/defn new-patient :- Patient
  [id :- s/Num, name :- s/Str]
  {:id id, :name name})

(pprint (new-patient 15 "William"))

(defn strictly-positive?
  [x]
  (> x 0))

(def StrictlyPositive
  (s/pred strictly-positive? 'strictly-positive))

(pprint (s/validate StrictlyPositive 15))
;(pprint (s/validate StrictlyPositive 0))
;(pprint (s/validate StrictlyPositive -15))


(def Patient
  "Schema for a Patient"
  {:id (s/constrained s/Int pos?), :name s/Str}
  )

(pprint (s/validate Patient {:id 15, :name "William"}))
;(pprint (s/validate Patient {:id -15, :name "William"}))


;(def Patient
;  "Schema for a Patient"
;  {:id (s/constrained s/Int #(> % 0) 'positive-integer), :name s/Str}
;  )
;
;(pprint (s/validate Patient {:id 15, :name "William"}))
;(pprint (s/validate Patient {:id -15, :name "William"}))
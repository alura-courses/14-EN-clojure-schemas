(ns hospital3.lesson3
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PosInt
  (s/pred pos-int? 'positive-integer))

(def Patient
  {:id PosInt, :name s/Str})

(s/defn new-patient :- Patient
  [id :- PosInt, name :- s/Str]
  {:id id, :name name})

(pprint (new-patient 15 "William"))
;(pprint (new-patient -15 "William"))

(defn greater-than-or-equal-to-zero?
  [x]
  (>= x 0))

(def FinancialValue
  (s/constrained s/Num greater-than-or-equal-to-zero?))

(def Request
  {:patient Patient
   :cost FinancialValue
   :exam s/Keyword})

(s/defn new-request :- Request
  [patient :- Patient, cost :- FinancialValue, exam :- s/Keyword]
  {:patient patient, :cost cost, :exam exam})

(pprint (new-request (new-patient 15 "William"),
                     15.67
                     :x-ray))

(pprint (new-request (new-patient 15 "William"),
                     0
                     :x-ray))

; insurance [:x-ray, :ultrasound]

(def Numbers [s/Num])
(pprint (s/validate Numbers [15]))
(pprint (s/validate Numbers [15, 20]))
(pprint (s/validate Numbers [15, 20, 39, 76, 94, 73.6]))
(pprint (s/validate Numbers [0]))
;(pprint (s/validate Numbers [nil]))
(pprint (s/validate Numbers []))
(pprint (s/validate Numbers nil))

(def Insurance [s/Keyword])
(pprint (s/validate Insurance [:x-ray]))

(def Patient
  {:id PosInt, :name s/Str, :insurance Insurance})

(pprint (s/validate Patient {:id 15, :name "William", :insurance [:x-ray, :ultrasound]}))
(pprint (s/validate Patient {:id 15, :name "William", :insurance []}))
(pprint (s/validate Patient {:id 15, :name "William", :insurance nil}))
;(pprint (s/validate Patient {:id 15, :name "William"}))
(ns hospital3.lesson4
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PosInt
  (s/pred pos-int? 'positive-integer))

(def Insurance [s/Keyword])

(def Patient
  {:id                         PosInt,
   :name                       s/Str,
   :insurance                  Insurance
   (s/optional-key :birthdate) s/Str})

(pprint (s/validate Patient {:id 15, :name "William", :insurance [:x-ray, :ultrasound]}))
(pprint (s/validate Patient {:id 15, :name "William", :insurance []}))
(pprint (s/validate Patient {:id 15, :name "William", :insurance nil}))
(pprint (s/validate Patient {:id 15, :name "William", :insurance [], :birthdate "11/17/1988"}))

; { 15 {15 "William"}, 20 {20 "Daniela"} }

(def Patients
  {PosInt Patient})

(pprint (s/validate Patients {}))

(let [william {:id 15, :name "William", :insurance [:x-ray, :ultrasound]}
      daniela {:id 20, :name "Daniela", :insurance []}]

  (pprint (s/validate Patients {15 william}))
  ;(pprint (s/validate Patients {-15 william}))
  ;(pprint (s/validate Patients {15 15}))
  ;(pprint (s/validate Patients {15 {:id 15, :name "William"}}))
  (pprint (s/validate Patients {15 william, 20 daniela}))
  )

(def Appointments
  {PosInt [s/Str]})

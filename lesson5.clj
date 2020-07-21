(ns hospital3.lesson5
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

(def Patients
  {PosInt Patient})

(def Appointments
  {PosInt [s/Str]})

(s/defn adds-patient :- Patients
  [patients :- Patients, patient :- Patient]
  (let [id (:id patient)]
    (assoc patients id patient))
  )

(s/defn adds-appointment :- Appointments
  [appointments :- Appointments, patient :- PosInt, new-appointments :- [s/Str]]
  (if (contains? appointments patient)
    (update appointments patient concat new-appointments)
    (assoc appointments patient new-appointments)
    )
  )

(s/defn prints-patient-report
  [appointments :- Appointments, patient :- PosInt]
  (println "Patient" patient "has the following appointments:" (get appointments patient))
  )

(defn tests-patients
  []
  (let [william {:id 15, :name "William", :insurance []}
        daniela {:id 20, :name "Daniela", :insurance []}
        paul {:id 25, :name "Paul", :insurance []}

        patients (reduce adds-patient {} [william, daniela, paul])
        appointments {}
        appointments (adds-appointment appointments, 15, ["01/01/2020"])
        appointments (adds-appointment appointments, 20, ["02/01/2020", "02/05/2020"])
        appointments (adds-appointment appointments, 15, ["03/01/2020"])]

    (pprint patients)
    (pprint appointments)
    (prints-patient-report appointments 15)
    )
  )

(tests-patients)

(pprint (s/validate Patient {:id 15, :name "William", :insurance []}))
;(pprint (s/validate Patient {:id nil, :name "William", :insurance []}))
(ns hospital3.lesson1
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

;{:id 15, :name "William"}
(defn adds-patient
  [patients patient]
  (if-let [id (:id patient)]
    (assoc patients id patient)
    (throw (ex-info "Patient had no ID" {:patient patient})))
  )

; {15 [apponts], 20 [apoonts], 25 [apponts]}
(defn adds-appointment
  [appointments patient new-appointments]
  (if (contains? appointments patient)
    (update appointments patient concat new-appointments)
    (assoc appointments patient new-appointments)
    )
  )

;(defn prints-patient-report
;  [appointments patient]
;  (println "Patient" patient "has the following appointments:" (get appointments patient))
;  )

(s/defn prints-patient-report
  [appointments, patient :- Long]
  (println "Patient" patient "has the following appointments:" (get appointments patient))
  )

(defn tests-patients
  []
  (let [william {:id 15, :name "William"}
        daniela {:id 20, :name "Daniela"}
        paul {:id 25, :name "Paul"}
        patients (reduce adds-patient {} [william, daniela, paul])
        appointments {}
        appointments (adds-appointment appointments, 15, ["01/01/2020"])
        appointments (adds-appointment appointments, 20, ["02/01/2020", "02/05/2020"])
        appointments (adds-appointment appointments, 15, ["03/01/2020"])]

    (pprint patients)
    (pprint appointments)
    (prints-patient-report appointments 15)
    (println (get appointments 15))
    )
  )

(tests-patients)

(pprint (s/validate Long 15))
;(pprint (s/validate Long "William"))
;(pprint (s/validate Long [15 20]))

(s/defn simple-test
  [x :- Long]
  (println x))

(simple-test 15)
;(simple-test "William")

(s/defn new-patient
  [id :- Long, name :- s/Str]
  {:id id, :name name})

(pprint (new-patient 15 "William"))
(pprint (new-patient "William" 15))
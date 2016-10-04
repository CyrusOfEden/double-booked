(ns double-booked.test-helper
  (:require [double-booked.core :as d]
            [clojure.string :as str]
            [clj-time.core :as t]))

(defn- event-interval [base]
  (let [unit (rand-nth [#(t/minutes (* % 5)) t/hours])
        start (t/plus base (unit (rand-int 12)))
        end (t/plus start (unit (rand-int 12)))]
    [start end]))

(def occasion-names ["birthday"
                     "meeting"
                     "baby shower"
                     "coffee date"
                     "date night"
                     "movie date"
                     "boring meeting"
                     "fun times"
                     "unfortunate meetup"])

(def person-names ["raoul"
                   "pedro"
                   "paulo"
                   "antoine"
                   "roman"
                   "soraya"
                   "jessica"
                   "beatrice"
                   "celine"])

(defn- event-name [occasion person]
  (str (str/capitalize occasion) " with " (str/capitalize person)))

(defn event-stub []
  (let [[start end] (event-interval (t/now))
        name (event-name (rand-nth occasion-names) (rand-nth person-names))]
    (d/->Event name start end)))

(defn event-stubs [n]
  (into [] (take n (repeatedly event-stub))))

(defn prepared-event-stubs []
  (let [now (t/now)
        half-hour (t/plus now (t/minutes 30))
        hour (t/plus now (t/hours 1))
        two-hours (t/plus now (t/hours 2))
        four-hours (t/plus now (t/hours 4))
        six-hours (t/plus now (t/hours 6))]
    [(merge (event-stub) {:start now :end half-hour})
     (merge (event-stub) {:start two-hours :end four-hours})
     (merge (event-stub) {:start hour :end six-hours})
     (merge (event-stub) {:start now :end two-hours})]))

;; EXPECTED
;; format:
;;   [[[first range] [second range]] & range pairs]
;; solution:
;;   [[[now half-hour] [now two-hours]]
;;    [[now two-hours] [hour six-hours]]
;;    [[two-hours four-hours] [hour six-hours]]]


(ns double-booked.test-helper
  (:require [clojure.string :as str]
            [clj-time.core :as t]))

(defn- event-interval [base]
  (let [unit (rand-nth [#(t/minutes (* % 5)) t/hours])
        start (t/plus base (unit (rand-int 12)))
        end (t/plus start (unit (rand-int 12)))]
    (t/interval start end)))

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

(defn event-name [occasion person]
  (str (str/capitalize occasion) " with " (str/capitalize person)))

(defn event-stub []
  {:name (event-name (rand-nth occasion-names) (rand-nth person-names))
   :interval (event-interval (t/now))})

(defn stubbed-events [n]
  (into [] (take n (repeatedly event-stub))))


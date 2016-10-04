(ns double-booked.core
  (:require [clj-time.core :as t]))

(def start-time (comp t/start :time))
(def end-time (comp t/end :time))

(defn overlap? [event-a event-b]
  (t/overlaps? (:time event-a) (:time event-b)))

(defn pair [event-a event-b]
  (if (t/before? (start-time event-a) (start-time event-b))
    [event-a event-b]
    [event-b event-a]))

(defn- overlapping-pairs [event events]
  (let [xf (comp (take-while #(overlap? event %))
                 (map #(vec (pair event %))))]
    (into [] xf events)))

(defn pairs [events]
  (loop [[e & es] (sort-by start-time t/before? events)
         ps (list)]
    (if (seq es)
      (recur es (lazy-cat ps (overlapping-pairs e es)))
      ps)))


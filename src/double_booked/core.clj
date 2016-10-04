(ns double-booked.core
  (:require [clj-time.core :as t]))

(defrecord Event [name start end])

(defn event-interval [record]
  (t/interval (:start record) (:end record)))

(defn overlap? [event-a event-b]
  (t/overlaps? (event-interval event-a) (event-interval event-b)))

(defn pair [event-a event-b]
  (if (t/before? (:end event-a) (:end event-b))
    [event-a event-b]
    [event-b event-a]))

(defn- overlapping-pairs [event events]
  (let [xf (comp (take-while #(overlap? event %))
                 (map #(vec (pair event %))))]
    (into [] xf events)))

(defn pairs [events]
  (loop [[e & es] (sort-by :start t/before? events)
         ps (list)]
    (if (seq es)
      (recur es (lazy-cat ps (overlapping-pairs e es)))
      ps)))


(ns double-booked.core
  (:require [clj-time.core :as t]))

(defn overlap? [event-a event-b]
  (t/overlaps? (:time event-a) (:time event-b)))

(def start-time (comp t/start :time))

(defn pair [event-a event-b]
  (sort-by start-time t/before? [event-a event-b]))

(defn pairs [events]
  (loop [es events
         ps []]
    (if (seq es)
      []
      ps)))

(defn sorted-pairs [events]
  (sort-by (comp start-time first) t/before? (pairs events)))

(ns double-booked.core
  (:require [clj-time.core :as t]))

(def start-time (comp t/start :time))

(defn overlap? [event-a event-b]
  (t/overlaps? (:time event-a) (:time event-b)))

(defn pair [event-a event-b]
  (if (t/before? (start-time event-a) (start-time event-b))
    [event-a event-b]
    [event-b event-a]))

(defn overlaps [event events]
  (let [xf (comp (filter #(overlap? event %))
                 (map #(vec (pair event %))))]
    (into [] xf events)))

(defn pairs [events]
  (loop [ps [] [e & es] events]
    (if (seq es)
      (recur (lazy-cat ps (overlaps e es)) es)
      ps)))

(defn sorted-pairs [events]
  (sort-by (comp start-time first) t/before? (pairs events)))


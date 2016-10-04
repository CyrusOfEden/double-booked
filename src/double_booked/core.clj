(ns double-booked.core
  (:require [clj-time.core :as t]))

(defrecord Event [name start end])

(defn event-interval [record]
  """Convert a map with :start and :end keys into a Joda interval"""
  (t/interval (:start record) (:end record)))

(defn overlap? [event-a event-b]
  """Check whether or not two events overlap"""
  (t/overlaps? (event-interval event-a) (event-interval event-b)))

(defn- pair [event-a event-b]
  """Organize a pair of events such that the first one ends before the second one"""
  (if (t/before? (:end event-a) (:end event-b))
    [event-a event-b]
    [event-b event-a]))

(defn- pairs [event]
  """Pair an event with every other event in a sequence that overlaps with it"""
  (comp (take-while #(overlap? event %))
        (map #(pair event %))))

(defn overlapping-pairs [events]
  """
  Given a sequence of events, each having a start and end time, return the sequence of all pairs of overlapping events.
  """
  (loop [ps '()
         [e & es] (sort-by :start t/before? events)]
    (if (empty? es)
      ps
      (recur (into ps (pairs e) es) es))))


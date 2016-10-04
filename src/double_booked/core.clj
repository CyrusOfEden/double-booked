(ns double-booked.core
  (:require [clj-time.core :as t]))

(defrecord Event [name start end])

(defn overlap? [event-a event-b]
  """Check whether or not two events overlap"""
  (and (t/before? (:start event-a) (:end event-b))
       (t/before? (:start event-b) (:end event-a))))

(defn- sort-pair-by [key event-a event-b]
  """Sort a given pair of events by key (:start or :end)"""
  (if (t/before? (key event-a) (key event-b))
    [event-a event-b]
    [event-b event-a]))

(defn- overlaps-for [event {:keys [sort-key sort-pairs]}]
  """Pair an event with every other event in a sequence that overlaps with it"""
  (let [predfn #(overlap? event %)
        mapfn (if sort-pairs
                #(sort-pair-by sort-key % event)
                #(-> [% event]))]
    (comp (take-while predfn)
          (map mapfn))))

(defn overlapping-pairs [events & {:keys [sort-pairs sort-key]
                                   :or [sort-pairs true
                                        sort-key :end]
                                   :as options}]
  """
  Given a sequence of events, each having a start and end time, return the sequence of all pairs of overlapping events.
  """
  (loop [pairs '()
         [event & events] (sort-by :start t/before? events)]
    (if (empty? events)
      pairs
      (recur (into pairs (overlaps-for event options) events) events))))


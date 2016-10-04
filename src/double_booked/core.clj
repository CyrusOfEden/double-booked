(ns double-booked.core
  (:require [clj-time.core :as t]))

(declare overlap? sort-pair overlaps-for overlapping-pairs)

(defn- sort-pair [sort? map-key event]
  """Return a sorter for a given pair of events by key (:start or :end)"""
  (if sort?
    #(if (t/before? (map-key event) (map-key %))
       [event %]
       [% event])
    #(-> [event %])))

(defn- overlaps-for [event {:keys [sort-pairs sort-key]}]
  """Pair an event with every other event in a sequence that overlaps with it"""
  (comp (take-while #(overlap? event %))
        (map (sort-pair sort-pairs sort-key event))))

(defrecord Event [name start end])

(defn overlap? [event-a event-b]
  """Check whether or not two events overlap"""
  (and (t/before? (:start event-a) (:end event-b))
       (t/before? (:start event-b) (:end event-a))))

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


(ns double-booked.core
  (:require [clj-time.core :as t]))

(defn overlapping-pairs [events]
  (if-let [events (seq events)]
    []
    []))

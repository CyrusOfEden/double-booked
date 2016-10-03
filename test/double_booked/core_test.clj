(ns double-booked.core-test
  (:require [clojure.test :refer :all]
            [double-booked.test-helper :as helper]
            [double-booked.core :as d]))

(def events (helper/stubbed-events 16))

(deftest empty-events-seq
  (testing "An empty events list should return an empty list"
    (is (= (d/overlapping-pairs []) []))))

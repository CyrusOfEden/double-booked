(ns double-booked.core-test
  (:require [clojure.test :refer :all]
            [clj-time.core :as t]
            [double-booked.test-helper :as helper]
            [double-booked.core :as d]))

(deftest events-pair
  (testing "A pair of events is sorted by start time"
    (let [[event-a event-b] (helper/event-stubs 2)
          actual (d/pair event-a event-b)]
      (if (t/before? (d/start-time event-a) (d/start-time event-b))
        (is (= [event-a event-b] actual))
        (is (= [event-b event-a] actual))))))

(deftest empty-events-seq
  (testing "An empty events sequence should return an empty list"
    (let [expected []
          actual (d/pairs [])]
      (is (= expected actual)))))

(deftest events-seq-no-false-positives
  (testing "Expect all pairs to overlap, don't expect any false positives"
    (letfn [(check [[event-a event-b]]
              (t/overlaps? (:time event-a) (:time event-b)))]
      (is (every? check (d/pairs (helper/event-stubs 16)))))))

(deftest events-seq-exhaustive
  (testing "Expect overlapping pairs function to return all overlaps"
    (is (= 3 (count (d/pairs (helper/prepared-event-stubs)))))))


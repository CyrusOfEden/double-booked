(ns double-booked.core-test
  (:require [clojure.test :refer :all]
            [clj-time.core :as t]
            [double-booked.test-helper :as helper]
            [double-booked.core :as d]))

(deftest empty-events-seq
  (testing "An empty events sequence should return an empty list"
    (is (= (d/overlapping-pairs []) []))))

(deftest events-seq-no-false-positives
  (testing "Expect all pairs to overlap, don't expect any false positives"
    (is (every? #(apply d/overlap? %) (d/overlapping-pairs (helper/event-stubs 16))))))

(deftest events-seq-exhaustive
  (testing "Expect overlapping pairs function to return all overlaps"
    (is (= 3 (count (d/overlapping-pairs (helper/prepared-event-stubs)))))))


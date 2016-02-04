(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer :all]))

(deftest world-test
  (testing "A world is a map of dimensions and live cells."
    (let [expected-world {:height 5 :width 10 :cells #{}}]
      (is (= expected-world (create-world 5 10 #{}))))))


(deftest get-neighbors-test
  (testing "A cell has 8 neighbors."
    (let [x 1 y 1
          neighbors (get-neighbors [x y])]
      (is (= 8 (count neighbors))))))

(deftest world-wrap-val-test
  (testing "Given a value that's too low, return the max value."
    (let [too-low -1
          max-value 2
          expected 2]
      (is (= expected (world-wrap-val too-low max-value)))))
  
  (testing "Given a value that's too high, return 0"
    (let [too-high 3
          max-value 2
          expected 0]
      (is (= expected (world-wrap-val too-high max-value))))))

  (testing "Given a value that's between 0 and max-value, return the value."
    (let [just-right 1
          max-value 2
          expected 1]
      (is (= expected (world-wrap-val just-right max-value)))))


(ns game-of-life.world-test
  (:require [clojure.test :refer :all]
            [game-of-life.world :refer :all]))

(deftest world-test
  (testing "A cell is a vector of x,y coordinates."
    (let [x 1 y 2
          cell [x y]]
      (is (= 2 (count cell)))))

  (testing "A world is a map of height, width, and live cells."
    (let [expected-world {:height 10 :width 5 :cells #{}}]
      (is (= expected-world (create-world 10 5 #{}))))))


(deftest get-neighbors-test
  (testing "A cell has neighbors."
    (let [x 1 y 1
          cell [x y]
          left-neighbor [0,1]
          neighbors (get-neighbors cell)]
      (is (some #(= % left-neighbor) neighbors))))

  (testing "A cell has 8 neighbors."
    (let [x 1 y 1
          neighbors (get-neighbors [x y])]
    (is (= 8 (count neighbors))))))

(deftest world-wrap-val-test
  (testing "Given a value less than zero, return max-value."
    (let [too-low -1
          max-value 2]
      (is (= max-value (world-wrap-val too-low max-value)))))

  (testing "Given a value greater than max-value, return 0."
    (let [too-high 3
          max-value 2]
      (is (= 0 (world-wrap-val too-high max-value)))))

  (testing "Given a value between 0 and max-value, return the value."
    (let [just-right 1
          max-value 2]
      (is (= just-right (world-wrap-val just-right max-value))))))


(deftest world-wrap-neighbors-test
  (testing "Given a 3x3 world: cells on opposite sites of the world are adjacent."
    (let [top-left-cell [0,0]
          world (create-world 3 3 #{})
          neighbors (get-neighbors top-left-cell)
          wrapped-neighbors (world-wrap-neighbors neighbors world)
          ;; Expected neighbors named after their place on the world grid.
          top-right [2,0]
          top-middle [1,0]
          bottom-left [0,2]
          bottom-right [2,2]]

      (is (some #{top-right top-middle bottom-left bottom-right}
                wrapped-neighbors)))))

(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer :all]))

(deftest world-test
  (testing "A world is a map of dimensions and live cells"
    (let [expected-world {:height 10 :width 10 :cells #{[0 1]}}]
      (is (= expected-world (create-world 10 10 #{[0 1]}))))))

(deftest neighbors-test
  (testing "A cell at x,y has eight neighbors."
    (let [x 1 y 1
          world (create-world 3 3 #{})]
      (is (= 8 (count (neighbors x y)))))))

(deftest wrapped-world-test
  (testing "Cells on opposite sides of the board are adjacent."
    (let [world (create-world 3 3 #{})
          x 0 y 0
          the-neighbors (world-wrap-neighbors (neighbors x y) world)
          ;; expected neighbors
          top [0,2]
          left [2 0]
          top-left [2,2]]
      (is (contains? (set the-neighbors) top))
      (is (contains? (set the-neighbors) left))
      (is (contains? (set the-neighbors) top-left)))))

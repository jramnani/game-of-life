(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.world :refer :all]
            [game-of-life.core :refer :all]))


(deftest next-cells-test
  (testing "Given an empty set of cells return the empty set."
    (let [empty-cells #{}
          neighbors []]
    (is (= #{} (next-cells empty-cells neighbors)))))

  (testing "Given one cell, return the empty set."
    (let [one-cell #{[1,1]}
          neighbors (mapcat get-neighbors one-cell)]
      (is (= #{} (next-cells one-cell neighbors)))))

  (testing "A cell with 3 neighbors is alive."
    (let [cells [[0,1] [1,1] [2,1]]
          neighbors (mapcat get-neighbors cells)
          expected-cell [1,0]]
      (is (contains? (next-cells cells neighbors)
                     expected-cell)))))


(deftest live-test
  (let [alive true
        dead false]

  (testing "A cell with 3 neighbors is alive."
    (is (= alive (live 3 alive)))
    (is (= alive (live 3 dead))))

  (testing "A live cell with 2 neighbors is alive"
    (is (= alive (live 2 alive))))

  (testing "A dead cell with 2 neighbors is dead"
    (is (= dead (live 2 dead))))

  (testing "A cell with more than 3 neighbors dies of overpopulation."
    (is (= dead (live 4 alive))))

  (testing "A cell with less than 2 neighbors dies of overpopulation."
    (is (= dead (live 1 alive))))))


(deftest step-test
  (testing "A blinker oscillates between a horizontal and vertical line."
    (let [horizontal-blinker #{[0,1] [1,1] [2,1]}
          vertical-blinker #{[1,0] [1,1] [1,2]}
          world (create-world 5 5 horizontal-blinker)]
      (is (= vertical-blinker (:cells (step world))))
      (is (= horizontal-blinker (:cells (step (step world))))))))


(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.world :refer :all]
            [game-of-life.core :refer :all]))

(deftest next-cells-test
  (testing "Given no cells, return the empty set"
    (is (= #{} (next-cells #{} []))))

  (testing "Given one cell, return the empty set"
    (let [cells #{[1,1]}
          neighbors (mapcat get-neighbors cells)]
      (is (= #{} (next-cells cells neighbors)))))

  (testing "A cell with three neighbors becomes alive"
    (let [cells #{[0,1] [1,1] [2,1]}
          neighbors (mapcat get-neighbors cells)
          expected-cell [1,0]]
      (is (contains? (next-cells cells neighbors)
                     expected-cell)))))

(deftest live-test
  (let [alive true
        dead false]
    (testing "Lonlieness"
      (is (= dead (live 1 live)))
      (is (= dead (live 1 dead))))

    (testing "Overcrowding"
      (is (= dead (live 4 live)))
      (is (= dead (live 4 dead))))

    (testing "Reproduction"
      (is (= alive (live 3 dead)))
      (is (= alive (live 3 alive))))

    (testing "Stasis"
      (is (= alive (live 2 alive)))
      (is (= dead (live 2 dead))))))

(deftest step-test
  (testing "A blinker oscillates between a horizontal and vertical line."
    (let [horizontal-blinker #{[0,1] [1,1] [2,1]}
          vertical-blinker #{[1,0] [1,1] [1,2]}
          world (create-world 5 5 horizontal-blinker)]
      (is (= vertical-blinker (:cells (step world))))
      (is (= horizontal-blinker (:cells (step (step world))))))))


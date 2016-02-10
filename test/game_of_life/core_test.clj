(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.world :refer :all]
            [game-of-life.core :refer :all]))


(deftest live-test
  (let [alive true
        dead false]

    (testing "A dead cell with fewer than three neighbors stays dead."
      (is (= dead (live 2 dead))))

    (testing "A dead cell with three neighbors becomes alive."
      (is (= alive (live 3 dead))))

    (testing "A live cell with two or three neighbors stays alive."
      (is (= alive (live 2 alive))))

    (testing "A live cell with greater than three neighbors dies of overpopulation."
      (is (= dead (live 4 alive))))

    (testing "A live cell with less than two neighbors dies of underpopulation."
      (is (= dead (live 1 alive))))))

(deftest next-cells-test
  (testing "Given an empty set of cells and neighbors, return the empty set."
    (is (= #{} (next-cells #{} []))))

  (testing "Given a cell with no neighbors, return the empty set."
    (is (= #{} (next-cells #{[1,1]} []))))

  (testing "Given a live cell with two live neighbors, return the cell."
    (let [cells #{[0,1] [1,1] [2,1]}
          neighbors (mapcat get-neighbors cells)
          new-cells (next-cells cells neighbors)]
      (is (contains? new-cells [1,1]))))

  (testing "A 'blinker' transitions from a horizontal to a vertical line."
    (let [horizontal-blinker #{[0,1] [1,1] [2,1]}
          vertical-blinker #{[1,0] [1,1] [1,2]}
          neighbors (mapcat get-neighbors horizontal-blinker)]
      (is (= vertical-blinker (next-cells horizontal-blinker neighbors))))))

(deftest step-test
  (testing "A blinker oscillates between a horizontal and vertical line."
    (let [horizontal-blinker #{[0,1] [1,1] [2,1]}
          vertical-blinker #{[1,0] [1,1] [1,2]}
          world (create-world 5 5 horizontal-blinker)]

      (is (= vertical-blinker (:cells (step world))))
      (is (= horizontal-blinker (:cells (step (step world))))))))

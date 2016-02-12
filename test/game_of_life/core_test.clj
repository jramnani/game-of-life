(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.world :refer :all]
            [game-of-life.core :refer :all]))


(deftest next-cells-test
  (testing "Given an empty set of cells and neighbors, return the empty set."
    (is (= #{} (next-cells #{} []))))

  (testing "Given one cell return the empty set."
    (let [cells #{[1,1]}
          neighbors (get-neighbors (first cells))]
      (is (= #{} (next-cells cells neighbors)))))

  (testing "A live cell with two neighbors stays alive."
    (let [cells #{[0,1] [1,1] [2,1]}
          neighbors (mapcat get-neighbors cells)
          expected-cell [1,1]]
      (is (contains? (next-cells cells neighbors)
                     expected-cell)))))

(deftest live-test
  (let [alive true
        dead false]
    (testing "A cell with three neighbors becomes alive."
      (is (= alive (live 3 dead))))

    (testing "A live cell with two neighbors stays alive."
      (is (= alive (live 2 alive))))

    (testing "A dead cell with two neighbors stays dead."
      (is (= dead (live 2 dead))))

    (testing "A cell with fewer than two neighbors dies of underpopulation."
      (is (= dead (live 1 alive)))
      (is (= dead (live 1 dead))))

    (testing "A cell with more than three neighbors dies of overpopulation."
      (is (= dead (live 4 alive)))
      (is (= dead (live 4 dead))))))

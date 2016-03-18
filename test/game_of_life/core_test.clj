(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.world :refer :all]
            [game-of-life.core :refer :all]))

(deftest next-cells-test
  (testing "Lonliness"
    (is (= #{} (next-cells #{} []))))

  (testing "Reproduction"
    (let [cells #{[0,1] [1,1] [2,1]}
          neighbors (mapcat get-neighbors cells)
          expected-cell [1,0]]
      (is (contains? (next-cells cells neighbors)
                     expected-cell)))))

(deftest live-test
  (let [alive true
        dead false]
    (testing "Lonliness"
      (is (= dead (live 1 alive))))

    (testing "Reproduction"
      (is (= alive (live 3 alive)))
      (is (= alive (live 3 dead))))

    (testing "Stasis"
      (is (= alive (live 2 alive)))
      (is (= dead (live 2 dead))))

    (testing "Overpopulation"
      (is (= dead (live 4 alive)))
      (is (= dead (live 4 dead))))))

(deftest step-test
  (testing "A blinker oscillates between a horizontal and vertical line."
    (let [horizontal-blinker #{[0,1] [1,1] [2,1]}
          vertical-blinker #{[1,0] [1,1] [1,2]}
          world (create-world 5 5 horizontal-blinker)]
      (is (= vertical-blinker (:cells (step world))))
      (is (= horizontal-blinker (:cells (step (step world))))))))


(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.world :refer :all]
            [game-of-life.core :refer :all]))


(deftest live-test
  (let [alive true
        dead false]
  (testing "A dead cell with two neighbors stays dead."
    (is (= dead (live 2 dead))))

  (testing "A dead cell with three neighbors becomes alive."
    (is (= alive (live 3 dead))))

  (testing "A live cell with two or three neighbors stays alive."
    (is (= alive (live 2 alive)))
    (is (= alive (live 3 alive))))

  (testing "A live cell with more than three neighbors dies of overpopulation."
    (is (= dead (live 4 alive))))

  (testing "A live cell with less than two neighbors dies of underpopulation."
    (is (= dead (live 1 alive))))))


(deftest apply-game-rules-test
  (testing "A dead world has no live cells in any generation."
    (let [cells #{}
          neighbors []]
    (is (= #{} (apply-game-rules cells neighbors)))))

  (testing "A world with one cell, turns into a dead world."
    (let [cells #{[1,1]}
          neighbors (mapcat get-neighbors cells)]
    (is (= #{} (apply-game-rules cells neighbors)))))

  (testing "A 'blinker' transforms from a horizontal line to a vertical line."
    (let [horizontal-blinker #{[1,1] [2,1] [3,1]}
          neighbors (mapcat get-neighbors horizontal-blinker)
          vertical-blinker #{[2,0] [2,1] [2,2]}]
      (is (= vertical-blinker (apply-game-rules horizontal-blinker
                                                neighbors))))))

(deftest step-test
  (testing "A 'blinker' oscillates between a horizontal and vertical line."
    (let [horizontal-blinker #{[1,1] [2,1] [3,1]}
          vertical-blinker #{[2,0] [2,1] [2,2]}
          world (create-world 5 5 horizontal-blinker)]
      (is (= vertical-blinker (:cells (step world)))))))


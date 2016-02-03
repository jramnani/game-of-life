(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer :all]))


(deftest world-test
  (testing "A world is a map of height, width, and live cells."
    (let [expected-world {:height 10 :width 10 :cells #{[0,1]}}]
      (is (= expected-world (create-world 10 10 #{[0,1]}))))))

(deftest neighbors-test
  (testing "A cell at x,y has eight neighbors."
    (let [x 1 y 1]
      (is (= 8 (count (neighbors [x y])))))))

(deftest wrapped-world-test
  (testing "Cells on opposite sites of the board are adjacent."
    (let [world (create-world 3 3 #{})
          x 0 y 0
          the-neighbors (world-wrap-neighbors (neighbors [x y]) world)
          bottom [0,2]
          left [2,0]
          bottom-right [2,2]]
      (is (contains? (set the-neighbors) bottom))
      (is (contains? (set the-neighbors) left))
      (is (contains? (set the-neighbors) bottom-right)))))

(deftest live-test
  (let [alive true
        dead false]
  (testing "A dead cell with two neighbors is still dead."
    (is (= dead (live 2 dead))))
  
  (testing "A dead cell with three neighbors comes to life."
    (is (= alive (live 3 dead))))
    
  (testing "A cell with two or three neighbors stays alive."
    (is (= alive (live 3 alive))))
    (is (= alive (live 2 alive)))
    
  (testing "A cell with more than three neighbors dies of overpopulation."
    (is (= dead (live 4 alive))))
    
  (testing "A cell will fewer than two neighbors dies of underpopulation."
    (is (= dead (live 1 alive))))))

(deftest blinker-test
  (testing "A 'blinker' oscillates between a horizontal and vertical line."
    (let [horizontal-blinker #{[1,1] [2,1] [3,1]}
          vertical-blinker #{[2,0] [2,1] [2,2]}
          initial-world (create-world 5 5 horizontal-blinker)]
      (is (= vertical-blinker (:cells (step initial-world))))
      (is (= horizontal-blinker (:cells (step (step initial-world))))))))

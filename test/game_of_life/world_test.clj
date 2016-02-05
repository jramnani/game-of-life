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

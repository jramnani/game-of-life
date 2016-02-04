(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer :all]))

(deftest world-test
  (testing "A world is a map of dimensions and live cells."
    (let [expected-world {:height 5 :width 10 :cells #{}}]
      (is (= expected-world (create-world 5 10 #{}))))))


(deftest get-neighbors-test
  (testing "A cell has 8 neighbors."
    (let [x 1 y 1
          neighbors (get-neighbors [x y])]
      (is (= 8 (count neighbors))))))

(deftest world-wrap-val-test
  (testing "Given a max width of 3 a coordinate at -1 will wrap into 2."
    (let [x -1
          max-width 2
          expected 2]
      (is (= expected (world-wrap-val x max-width))))))


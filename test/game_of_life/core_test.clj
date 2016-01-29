(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer :all]))

(deftest world-test
  (testing "A world is a map of dimensions and live cells"
    (let [expected-world {:height 10 :width 10 :cells #{[0 1]}}]
      (is (= expected-world (create-world 10 10 #{[0 1]}))))))

(deftest neighbors-test
  (testing "A cell at x,y has eight neighbors."
    (let [x 1 y 1]
      (is (= 8 (count (neighbors x y)))))))

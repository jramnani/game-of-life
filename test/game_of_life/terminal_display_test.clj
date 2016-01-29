(ns game-of-life.terminal-display-test
  (:require [clojure.test :refer :all]
            [game-of-life.terminal-display :refer :all]))

(deftest row-to-str-test
  (let [row [false true true false]]
    (is (= ".**." (row->str row)))))


(deftest convert-world-to-rows-test
  (testing "Convert a sparse matrix world into a dense matrix of booleans."
    (let [world {:height 3 :width 3 :cells #{[0,0] [2,0]}}
          expected [true false true]]
      (is (= expected (first (world->rows world)))))))

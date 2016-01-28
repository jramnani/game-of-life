(ns game-of-life.terminal-display-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer [create-world]]
            [game-of-life.terminal-display :refer :all]))

(deftest row-to-str-test
  (let [row [false true true false]]
    (is (= ".**." (row->str row)))))


(deftest convert-world-to-rows-test
  (testing "Convert a sparse matrix world into a list of lists of booleans."
    (let [world (create-world 3 3 #{[0 0] [2 0]})
          expected [true false true]]
      (is (= expected (first (world->rows world)))))))

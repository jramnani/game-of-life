(ns game-of-life.terminal-display-test
  (:require [clojure.test :refer :all]
            [game-of-life.terminal-display :refer :all]))

(deftest row-to-str-test
  (let [row [false true true false]]
    (is (= ".**." (row->str row)))))


(deftest print-board-to-string-test
  (let [board [[false false false]
               [true  true  true]
               [false false false]]
        expected "...\n***\n..."]
    (testing "Printing the board to the screen."
      (is (= expected (board->str board))))))
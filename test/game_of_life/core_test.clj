(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer :all]))

(deftest create-a-game-board
  (let [board (create-board 5 5)]
    (testing "A 5x5 board has 5 rows."
      (is (= 5 (count board))))
    (testing "A 5x5 board has 5 columns."
      (is (= 5 (count (first board)))))))

(deftest get-cell-test
  (let [board (create-board 5 5)]
    (testing "Get the value of the middle cell of the board."
      (is (= false (get-cell 3 3 board))))))

(deftest get-neighbors-for-middle-cell
  ; On a 5x5 board let's test the middle cell. It's the easiest place on
  ; the board to test because it doesn't overlap any edges on the board.
  (let [board (create-board 5 5)]
    (testing "A cell should have 8 neighbors."
      (is (= 8 (count (get-neighbors 3 3 board)
                      ))))))

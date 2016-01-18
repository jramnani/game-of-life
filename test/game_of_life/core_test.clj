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
      (is (= 8 (count (get-neighbors 3 3 board)))))))

(deftest index-above-test
  (let [board [[true]
               [false]]
        top 0
        bottom 1]
    (testing "index-above should return the index of cell (y - 1) on the board."
      (is  (= top (index-above bottom board))))

    (testing "index-above should wrap around from the top to the bottom."
      (is (= bottom (index-above top board))))))

(deftest living-cell-test
  (let [two-alive-neighbors [true true
                         false false false false false false]
        three-alive-neighbors [true true true
                           false false false false false]]

    (testing "A cell that is alive should stay alive if two of its neighbors are alive."
      (is (= true (stay-alive? two-alive-neighbors))))

    (testing "A cell that is alive should stay alive if three of its neighbors are alive."
      (is (= true (stay-alive? three-alive-neighbors))))))

(deftest cell-becomes-alive-test
  (let [alive-neighbors [true  true  true
                         false false false false false]
        dead-neighbors [true  true
                        false false false false false false]]
    (testing "If a cell is dead then it becomes alive if it has three alive neighbors."
      (is (= true (become-alive? alive-neighbors))))
    (testing "If a dead cell has two alive neighbors it is still dead."
      (is (= false (become-alive? dead-neighbors))))))

(deftest dead-cell-test
  (let [no-alive-neighbors [true false false false
                            false false false false]]
    (testing "A cell dies if it has no living neighbors."
      (= true (stay-alive? no-alive-neighbors)))))

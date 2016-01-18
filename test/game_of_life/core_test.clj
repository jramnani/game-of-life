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

(deftest index-above-test
  (let [board [[true]
               [false]]
        top 0
        bottom 1]
    (testing "index-above should return the index of cell (y - 1) on the board."
      (is  (= top (index-above bottom board))))

    (testing "index-above should wrap around from the top to the bottom."
      (is (= bottom (index-above top board))))))

(deftest index-below-test
  (let [board [[true]
               [false]]
        top 0
        bottom 1]
    (testing "index-below should return the index of cell (y + 1) on the board."
      (is (= bottom (index-below top board))))

    (testing "index-below should wrap around from the bottom to the top."
      (is (= top (index-below bottom board))))))

(deftest index-left-test
  (let [board [[true false]]
        left 0
        right 1]
    (testing "index-left should return the index of (x - 1) on the board."
      (is (= left (index-left right board))))

    (testing "index-left should wrap around from the left to the right."
      (is (= right (index-left left board))))))

(deftest index-right-test
  (let [board [[true false]]
        left 0
        right 1]
    (testing "index-right should return the index of cell (x + 1) on the board."
      (is (= right (index-right left board))))

    (testing "index-right should wrap around the board."
      (is (= left (index-right right board))))))

(deftest get-neighbors-for-middle-cell-test
  ; On a 5x5 board let's test the middle cell. It's the easiest place on
  ; the board to test because it doesn't overlap any edges on the board.
  (let [board (create-board 5 5)]
    (testing "A cell should have 8 neighbors."
      (is (= 8 (count (get-neighbors 3 3 board)))))))

(deftest get-neighbors-for-edge-cell-test
  (let [board [[true  true  true]
               [false false false]
               [true  true  true]]]
    (testing "A cell on the top edge has 8 neighbors."
      (is (= 8 (count (get-neighbors 1 0 board)))))

    (testing "The top-middle cell has 5 neighbors alive."
      (is (= 5 (count (filter alive? (get-neighbors 1 0 board))))))

    (testing "The right-middle cell has 6 neighbors alive."
      (is (= 6 (count (filter alive? (get-neighbors 2 1 board))))))

    (testing "The bottom-middle cell has 5 neighbors alive."
      (is (= 5 (count (filter alive? (get-neighbors 1 2 board))))))

    (testing "The left-middle cell has 6 neighbors alive."
      (is (= 6 (count (filter alive? (get-neighbors 0 1 board))))))

    (testing "The top-left cell has 6 neighbors alive."
      (is (= 5 (count (filter alive? (get-neighbors 0 0 board))))))

    (testing "The bottom-right cell has 5 neighbors alive."
      (is (= 5 (count (filter alive? (get-neighbors 2 2 board))))))))

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

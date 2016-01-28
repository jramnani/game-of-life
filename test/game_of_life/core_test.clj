(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer :all]))

;; Given that a 'cell' is a two item vector of integers containing x,y coordinates.
;; e.g. [1 2]

;; And the 'world' is a set of cells.
;; e.g. #{[0 0] [0 1] [0 2] [0 1] [1 1] [1 2]}

;; And only 'live' cells are contained in the set.  Dead cells are omitted.
;; For example, a 'blinker' automata would oscillate between these two states.
;; [0 0] [1 0] [2 0] -> [1 0] [1 1] [1 2]

(deftest create-world-test
  (testing "Creating a world with only height and width will return no living cells."
    (let [world (create-world 10 10)]
      (is (= #{} (:cells world)))))

  (testing "Can create a world with a set of living cells."
    (let [world (create-world 10 10 #{[1 1]})]
      (is (contains? (:cells world) [1 1])))))


(deftest neighbors-test
  (testing "neighbors should find the eight neighbors for a given cell."
    (is (= (sort '([0 0] [1 0] [2 0] [0 1] [2 1] [0 2] [1 2] [2 2]))
           (sort (neighbors [1 1])))))

  (testing "A cell should not be a neighbor to itself."
    (let [x 1
          y 1
          the-neighbors (neighbors [x y])]
      (is (not (contains? (set the-neighbors) [x y]))))))

(deftest wrapping-neighbors-test
  ;; Given a 3x3 world.
  ;; | 0,0 | 1,0 | 2,0 |
  ;; | 0,1 | 1,1 | 2,1 |
  ;; | 0,2 | 1,2 | 2,2 |

  (testing "The world wraps from right to left."
    (let [right [2,1]
          left [0,1]
          world (create-world 3 3 #{right})
          test-neighbors (neighbors right)
          the-neighbors (wrap-neighbors test-neighbors world)]
      (is (contains? (set the-neighbors) left))))

  (testing "The world wraps from left to right."
    (let [right [2,1]
          left [0,1]
          world (create-world 3 3 #{left})
          the-neighbors (wrap-neighbors (neighbors left)
                                        world)]
      (is (contains? (set the-neighbors) right))))

  (testing "The world wraps from top to bottom."
    (let [top [1,0]
          bottom [1,2]
          world (create-world 3 3 #{top})
          the-neighbors (wrap-neighbors (neighbors top)
                                        world)]
      (is (contains? (set the-neighbors) bottom))))

  (testing "The world wraps from bottom to top."
    (let [top [1,0]
          bottom [1,2]
          world (create-world 3 3 #{bottom})
          the-neighbors (wrap-neighbors (neighbors bottom)
                                        world)]
      (is (contains? (set the-neighbors) top))))

  (testing "The world wraps from top right to bottom left."
    (let [top-right [2,0]
          bottom-left [0,2]
          world (create-world 3 3 #{top-right})
          the-neighbors (wrap-neighbors (neighbors top-right)
                                        world)]
      (is (contains? (set the-neighbors) bottom-left))))

  (testing "The world wraps from bottom left to top right."
    (let [top-right [2,0]
          bottom-left [0,2]
          world (create-world 3 3 #{bottom-left})
          the-neighbors (wrap-neighbors (neighbors bottom-left)
                                        world)]
      (is (contains? (set the-neighbors) top-right))))

  (testing "The world wraps from top left to bottom right."
    (let [top-left [0 0]
          bottom-right [2,2]
          world (create-world 3 3 #{top-left})
          the-neighbors (wrap-neighbors (neighbors top-left)
                                        world)]
      (is (contains? (set the-neighbors) bottom-right))))

  (testing "The world wraps from bottom right to top left."
    (let [top-left [0 0]
          bottom-right [2,2]
          world (create-world 3 3 #{bottom-right})
          the-neighbors (wrap-neighbors (neighbors bottom-right)
                                        world)]
      (is (contains? (set the-neighbors) top-left))))

  (testing "wrap-neighbors returns eight nighbors."
    (let [world (create-world 3 3 #{[1 1]})
          the-neighbors (neighbors [1 1])]
      (is (= 8 (count (wrap-neighbors the-neighbors world)))))))


(deftest game-of-life-rules-test
  (let [alive true
        dead false]

    (testing "A dead cell with two live neighbors should stay dead."
      (is (= dead (live 2 dead))))

    (testing "A dead cell with three live neighbors should become alive."
      (is (= alive (live 3 dead))))

    (testing "A live cell with two or three live neighbors stays alive."
      (is (= alive (live 2 alive)))
      (is (= alive (live 3 alive))))

    (testing "A live cell with fewer than two live neighbors should die of underpopulation."
      (is (= dead (live 1 alive))))

    (testing "A cell with more than three live neighbors should die of overpopulation."
      (is (= dead (live 4 alive))))))


(deftest blinker-test
  ;; Given a 5x5 world.
  ;; | 0,0 | 1,0 | 2,0 | 3,0 | 4,0 |
  ;; | 0,1 | 1,1 | 2,1 | 3,1 | 4,1 |
  ;; | 0,2 | 1,2 | 2,2 | 3,2 | 4,2 |
  ;; | 0,3 | 1,3 | 2,3 | 3,3 | 4,3 |
  ;; | 0,4 | 1,4 | 2,4 | 3,4 | 4,4 |
  (testing "A blinker oscillates between a horizontal and vertial line of 3."
    (let [horizonal-blinker #{[1 1] [2 1] [3 1]}
          vertical-blinker  #{[2 0] [2 1] [2 2]}
          initial-world (create-world 5 5 horizonal-blinker)]
      (is (= (sort vertical-blinker)
             (sort (:cells (step initial-world)))))
      (is (= (sort horizonal-blinker)
             (sort (:cells (step (step initial-world)))))))))

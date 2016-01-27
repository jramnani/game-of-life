(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.core :refer :all]))

;; Given that a 'cell' is a two item vector of integers containing x,y coordinates.
;; e.g. [1 2]

;; And the 'world' is a set of two item vectors containing x,y
;; coordinates.
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
      (is (contains? (set the-neighbors) top-right)))))

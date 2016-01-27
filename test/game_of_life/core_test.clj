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

;; (deftest wrapping-world-test
;;   (testing "A cell on the right edge of the world has a neighbor on the left edge."
;;     ;; Given a 3x3 world.
;;     (let [world #{[3 1] [0 1]}
;;           right-x 3
;;           right-y 1
;;           left-x 0
;;           left-y 1
;;           the-neighbors (neighbors [right-x right-y])]
;;       (is (contains? (set the-neighbors) [left-x left-y])))))

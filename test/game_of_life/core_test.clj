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

(deftest neighbors-test
  (testing "neighbors should find the eight neighbors for a given cell."
    (is (= (sort '([0 0] [1 0] [2 0] [0 1] [2 1] [0 2] [1 2] [2 2]))
           (sort (neighbors [1 1]))))))

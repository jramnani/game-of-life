(ns game-of-life.core
  (:require [game-of-life.terminal-display :refer :all]))

(defn neighbors
  "([[x y]])
  Takes a two-item vector of integers representing the cell at coordinates x and y.
  Returns a list of coordinates of the cell's eight neighbors."
  [[x y]]
  (for [dx [-1 0 1]  ;; left, center, and right.
        ;; dx = 0, dy = 0 is the same as x,y.
        ;; Don't let a cell be a neighbor to itself.
        dy (if (zero? dx)
             [-1 1]
             [-1 0 1])]
    [(+ dx x) (+ dy y)]))

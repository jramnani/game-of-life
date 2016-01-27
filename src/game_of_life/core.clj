(ns game-of-life.core
  (:require [game-of-life.terminal-display :refer :all]))

(defn create-world
  "Create a HashMap with the keys:
  height: integer
  width: integer
  cells: set of [x,y] coordinate pairs of live cells.
         e.g. #{[0 0] [0 1] [0 2] [1 2]}"
  ([height width]
   {:height height :width width :cells #{}})
  ([height width cells]
   {:height height :width width :cells (set cells)}))


(defn neighbors
  "Takes a two-item vector of integers representing the cell at coordinates x and y.
  Returns a list of coordinates of the cell's eight neighbors."
  [[x y]]
  (for [dx [-1 0 1]  ;; left, center, and right.
        ;; dx = 0, dy = 0 is the same as x,y.
        ;; Don't let a cell be a neighbor to itself.
        dy (if (zero? dx)
             [-1 1]
             [-1 0 1])]
    [(+ dx x) (+ dy y)]))

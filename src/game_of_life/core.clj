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
  "First arg is a two-item vector of integers representing the cell at coordinates x and y.
  Second arg is a HashMap of the world as created by 'create-world'.
  Returns a list of coordinates of the cell's eight neighbors."
  [[x y]]
  (for [dx [-1 0 1]  ;; left, center, and right.
        ;; dx = 0, dy = 0 is the same as x,y.
        ;; Don't let a cell be a neighbor to itself.
        dy (if (zero? dx)
             [-1 1]
             [-1 0 1])]
    [(+ dx x) (+ dy y)]))

(defn wrap-neighbors
  "Clamp the list of neighbors to be inside the world."
  [neighbors world]
  ;; Because 0,0 is a valid coordinate, we have to decrement the width and
  ;; height to get valid boundary indices.
  (let [height-index (dec (:height world))
        width-index (dec (:width world))
        cells (:cells world)]
    (map (fn [xy]
           (let [x (first xy)
                 y (second xy)]
             (cond
               (>= x width-index) [0 y]
               (<= x 0) [width-index y]
               :else [x y]))
           )
         cells)))

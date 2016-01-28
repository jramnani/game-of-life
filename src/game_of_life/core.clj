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
  "Given a list of neighbors compute their coordinates to be within
  the dimensions of the world, and that the edges of the board touch.
  In the Game of Life, this is called a 'wrapped' world."
  [neighbors world]
  ;; Because 0,0 is a valid coordinate, we have to decrement the width and
  ;; height to get valid boundary indices.
  (let [max-height (dec (:height world))
        max-width (dec (:width world))]
    (map (fn [xy]
           (let [x (first xy)
                 y (second xy)]
             (cond
               ;; wrap top-right to bottom-left.
               (and (> x max-width)
                    (< y 0)) [0 max-height]
               ;; wrap bottom-left to top-right.
               (and (< x 0)
                    (> y max-height)) [max-width 0]
               ;; wrap top-left to bottom-right.
               (and (< x 0)
                    (< y 0)) [max-width max-height]
               ;; wrap bottom-right to top-left
               (and (> x max-width)
                    (> y max-height)) [0 0]
               (> x max-width) [0 y] ;; wrap right to left.
               (< x 0) [max-width y] ;; wrap left to right.
               (< y 0) [x max-height] ;; wrap top to bottom
               (> y max-height) [x 0] ;; wrap bottom to top.
               :else [x y]))
           )
         neighbors)))


(defn live
  "Determine whether a cell should be alive.
  n: (integer) value of live neighbors.
  alive?: (boolean) is the cell alive?
  Returns a boolean."
  [n alive?]
  (or (= n 3)
      (and (= n 2) alive?)))


(defn step
  [world]
  (let [cells (:cells world)
        the-neighbors (mapcat neighbors cells)
        wrapped-neighbors (wrap-neighbors the-neighbors world)
        new-cells (for [[cell n] (frequencies wrapped-neighbors)
              :when (live n (contains? cells cell))]
                    cell)]
    (assoc world :cells (set new-cells))))


(defn generate-random-cells
  []
  (set (repeatedly (/ (* height width)
                      4)
                   #(vec [(rand-int width) (rand-int height)]))))

(defn -main
  []
  (def height 20)
  (def width 80)
  (def blinker-cells #{[1 1] [2 1] [3 1]})
  (def random-cells (generate-random-cells))
  (def initial-world (create-world height width random-cells))

  (display-world-to-terminal initial-world step))

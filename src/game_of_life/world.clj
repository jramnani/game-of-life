(ns game-of-life.world)

;; This implementation of Conway's Game of Life world as inspired by:
;; http://tech.puredanger.com/2011/11/17/clojure-and-processing/

(defn create-world [height width cells]
  "A world is a map with keys, :height, :width, and :cells. The height and
  width are the dimensions of the world grid, and the cells are a set of [x,y]
  coordinate pairs that represent living cells."
  {:height height :width width :cells cells})


(defn get-neighbors [[x y]]
  "Given an [x,y] coordintate of a cell, return it's eight neighbors on the
  world grid as a list of coordinate pairs."
  (for [dx [-1 0 1]
        dy (if (zero? dx)
             [-1 1]
             [-1 0 1])]
    [(+ dx x) (+ dy y)]))


(defn world-wrap-val [value max-value]
  "Given the int's value and max-value, clamp the value to be within:
  0 <= value <= max-value."
  (cond
    (< value 0) max-value
    (> value max-value) 0
    :else value))


(defn world-wrap-neighbors [neighbors world]
  "Given a list of neighbors and a world map, transform the neighbors to be
  within the dimensions of the world's grid. This makes cells on the opposite
  of the grid be adjacent.
  Implements a 'wrapped world' in Conway's Game of Life terminology."
  (let [max-height (dec (:height world))
        max-width (dec (:width world))
        xs (map first neighbors)
        ys (map second neighbors)
        wrapped-xs (map #(world-wrap-val %1 max-width)
                        xs)
        wrapped-ys (map #(world-wrap-val %1 max-height)
                        ys)]
    (map #(vec [%1 %2]) wrapped-xs wrapped-ys)))

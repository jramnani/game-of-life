(ns game-of-life.core
  (:require [game-of-life.terminal-display :refer :all]))

(defn create-world [height width cells]
  {:height height :width width :cells cells})

(defn get-neighbors [[x y]]
  (for [dx [-1 0 1]
        dy (if ( zero? dx)
             [-1 1]
             [-1 0 1])]
    [(+ dx x) (+ dy y)]))

(defn world-wrap-val [value max-value]
  (cond
    (< value 0) max-value
    (> value max-value) 0
    :else value))

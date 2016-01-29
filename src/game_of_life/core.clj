(ns game-of-life.core
  (:require [game-of-life.terminal-display :refer :all]))


(defn create-world
  [height width cells]
  {:height height :width width :cells cells})


(defn neighbors
  [x y]
  (for [dx [-1 0 1]
        dy (if (zero? dx)
             [-1 1]
             [-1 0 1])]
    [(+ dx x) (+ dy y)]))

(defn world-wrap-neighbors
  [neighbors world]
  (let [max-height (dec (:height world))
        max-width (dec (:width world))
        xs (map first neighbors)
        ys (map second neighbors)
        wrapped-xs (map (fn [x]
                          (cond
                            (< 0 x) max-width
                            (> x max-width) 0
                            :else x))
                        xs)
        wrapped-ys (map (fn [y]
                          (cond
                            (< 0 y) max-height
                            (> y max-height) 0
                            :else y))
                        ys)]
    (map #(vec [%1 %2]) wrapped-xs wrapped-ys)))

(defn live
  [n alive?]
  (or (= n 3)
      (and (= n 2)
           alive?)))

(defn step
  [world]
  (let [cells (:cells world)
        the-neighbors ]))

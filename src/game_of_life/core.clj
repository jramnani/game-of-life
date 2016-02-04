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

(defn world-wrap-neighbors [neighbors world]
  (let [max-height (dec (:height world))
        max-width (dec (:width world))
        xs (map first neighbors)
        ys (map second neighbors)
        wrapped-xs (map #(world-wrap-val %1 max-width) xs)
        wrapped-ys (map #(world-wrap-val %1 max-height) ys)]
    (map #(vec [%1 %2]) wrapped-xs wrapped-ys)))


(defn live [n alive?]
  (or (= n 3)
      (and (= n 2)
           alive?)))

(defn step [world]
  (let [cells (:cells world)
        neighbors (mapcat get-neighbors cells)
        wrapped-neighbors (world-wrap-neighbors neighbors world)
        new-cells (for [[cell n] (frequencies wrapped-neighbors)
                        :when (live n (contains? cells cell))]
                    cell)]
    (assoc world :cells (set new-cells))))


(defn -main []
  (def height 20)
  (def width 80)
  (def random-cells (set (repeatedly (/ (* height width)
                                   4)
                                #(vec [(rand-int width) (rand-int height)]))))
  (def world (create-world height width random-cells))
  (display-world-to-terminal world step))

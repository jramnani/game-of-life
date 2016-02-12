(ns game-of-life.core
  (:require [game-of-life.terminal-display :refer :all]
            [game-of-life.world :refer :all]))

(defn live [n alive?]
  (or (= n 3)
      (and (= n 2)
           alive?)))

(defn next-cells [cells neighbors]
  (let [neighbors-per-cell (frequencies neighbors)]
    (set (keys (filter (fn [[cell n]]
                         (live n (contains? cells cell)))
                 neighbors-per-cell)))))

(defn step [world]
  (let [cells (:cells world)
        neighbors (mapcat get-neighbors cells)
        new-cells (next-cells cells neighbors)]
    (assoc world :cells new-cells)))


(defn -main []
  (def height 20)
  (def width 80)
  (def total-cells (* height width))
  (def random-cells (set (repeatedly (/ total-cells 4)
                                #(vec [(rand-int width) (rand-int height)]))))

  (def world (create-world height width random-cells))

  (display-world-to-terminal world step))

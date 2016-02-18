(ns game-of-life.core
  (:require [game-of-life.world :refer :all]))

(defn live [n alive?]
  (or (= n 3)
      (and (= n 2)
           alive?)))

(defn next-cells [cells neighbors]
  (let [neighbors-per-cell (frequencies neighbors)
        new-cells (keys (filter (fn [[cell n]] (live n (contains? cells cell)))
                                neighbors-per-cell))]
    (set new-cells)))

(defn step [world]
  (let [cells (:cells world)
        neighbors (world-wrap-neighbors (mapcat get-neighbors cells)
                                        world)
        new-cells (next-cells cells neighbors)]
    (assoc world :cells new-cells)))

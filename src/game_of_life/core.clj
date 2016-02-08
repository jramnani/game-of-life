(ns game-of-life.core
  (:require [game-of-life.terminal-display :refer :all]
            [game-of-life.world :refer :all]))

(defn live [n alive?]
  (or (= n 3)
      (and (= n 2)
           alive?)))

(defn step [world]
  (let [cells (:cells world)
        neighbors (world-wrap-neighbors (mapcat get-neighbors cells)
                                        world)
        new-cells (for [[cell n] (frequencies neighbors)
                        :when (live n (contains? cells cell))]
                    cell)]
    (assoc world :cells (set new-cells))))

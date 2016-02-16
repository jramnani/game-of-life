(ns game-of-life.core
  (:require [game-of-life.world :refer :all]))


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
  world)

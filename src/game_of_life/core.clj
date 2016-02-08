(ns game-of-life.core
  (:require [game-of-life.terminal-display :refer :all]))

(defn live [n alive?]
  (or (= n 3)
      (and (= n 2)
           alive?)))

(ns game-of-life.main
  (:require [game-of-life.core :refer :all]
            [game-of-life.terminal-display :refer :all]
            [game-of-life.world :refer :all]))


(defn -main []
  (def height 20)
  (def width 80)
  (def total-cells (* height width))
  (def random-cells (set (repeatedly (/ total-cells 4)
                                #(vec [(rand-int width) (rand-int height)]))))

  (def world (create-world height width random-cells))

  (display-world-to-terminal world step))

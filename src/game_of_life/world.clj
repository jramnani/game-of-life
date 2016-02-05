(ns game-of-life.world)

(defn create-world [height width cells]
  {:height height :width width :cells cells})


(defn get-neighbors [[x y]]
  (for [dx [-1 0 1]
        dy (if (zero? dx)
             [-1 1]
             [-1 0 1])]
    [(+ dx x) (+ dy y)]))

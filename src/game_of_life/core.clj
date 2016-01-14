(ns game-of-life.core)

; Board dimensions
(def rows 10)
(def columns 10)

(defn create-board
  "Create a new board. Initializes all cells to be dead."
  [num-rows num-columns]
  (let [row (vec (repeat num-columns false))
        board (vec (repeat num-rows row))]
    board))

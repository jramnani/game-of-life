(ns game-of-life.core)

; Board dimensions
(def rows 5)
(def columns 5)

(defn create-board
  "Create a new board. Initializes all cells to be dead."
  [num-columns num-rows]
  (let [row (vec (repeat num-columns false))
        board (vec (repeat num-rows row))]
    board))

(defn get-neighbors
  "Get the neighbors for a given cell. The cell is determined by X and
  Y coordinates on a 2d vector board."
  [board x y]
  (vec (repeat 8 nil)))

(defn display-board-terminal
  "Print the board to a terminal."
  [board]
  (map println board))

(defn -main []
  (display-board-terminal (create-board columns rows)))

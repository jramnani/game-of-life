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

(def alive? true?)

(defn get-cell
    "Get the value of a cell at position x and y on the board."
  [x y board]
  (let [row (nth board y)]
    (nth row x)))

(defn get-neighbors
  "Get the neighbors for a given cell. The cell is determined by X and
  Y coordinates on a 2d vector board.
  Returns a vector of neighbor cells."
  [x y board]
  (let [top (get-cell x (- y 1) board)
        bottom (get-cell x (+ y 1) board)
        left (get-cell (- x 1) y board)
        right (get-cell (+ x 1) y board)
        top-left (get-cell (- x 1) (+ y 1) board)
        top-right (get-cell (+ x 1) (+ y 1) board)
        bottom-left (get-cell (- x 1) (- y 1) board)
        bottom-right (get-cell (+ x 1) (- y 1) board)]
    ; Return a vector of all the neighbors in clockwise order.
    [top top-right right bottom-right bottom bottom-left left top-left]))

(defn become-alive?
  [cells]
  (let [alive-neighbors (count (filter alive? cells))]
    (= 3 alive-neighbors)))

(defn stay-alive?
  [cells]
  (let [alive-neighbors (count (filter alive? cells))]
    (or (= 2 alive-neighbors)
        (= 3 alive-neighbors))))

(defn display-board-terminal
  "Print the board to a terminal."
  [board]
  (dorun
   (map println board)))

(defn -main []
  (display-board-terminal (create-board columns rows)))

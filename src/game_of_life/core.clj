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

(defn get-cell
    "Get the value of a cell at position x and y on the board."
  [x y board]
  (let [row (nth board y)]
    (nth row x)))

(defn index-above
  [y board]
  (if (<= y 0)
    (dec (count board)) ;; wrap around to the bottom of the board.
    (dec y)))

(defn index-below
  [y board]
  (if (>= y (dec (count board)))
    0 ;; wrap around to the top of the board.
    (inc y)))

(defn index-left
  [x board]
  (let [row (first board)]
    (if (<= x 0)
      (dec (count row))
      (dec x))))

(defn index-right
  [x board]
  (let [row (first board)]
    (if (>= x (dec (count row)))
      0 ;; wrap around to the first column.
      (inc x))))

(defn get-neighbors
  "Get the neighbors for a given cell. The cell is determined by X and
  Y coordinates on a 2d vector board.
  Returns a vector of neighbor cells."
  [x y board]
  (let [top (get-cell x (index-above y board) board)
        bottom (get-cell x (index-below y board) board)
        left (get-cell (index-left x board) y board)
        right (get-cell (index-right x board) y board)
        top-left (get-cell (index-left x board)
                           (index-above y board)
                           board)
        top-right (get-cell (index-right x board)
                            (index-above y board)
                            board)
        bottom-left (get-cell (index-left x board)
                              (index-below y board)
                              board)
        bottom-right (get-cell (index-right x board)
                               (index-below y board)
                               board)]
    ; Return a vector of all the neighbors in clockwise order.
    [top top-right right bottom-right bottom bottom-left left top-left]))

(def alive? true?)
(def dead? false?)

(defn become-alive?
  [cells]
  (let [alive-neighbors (count (filter alive? cells))]
    (= 3 alive-neighbors)))

(defn stay-alive?
  [cells]
  (let [alive-neighbors (count (filter alive? cells))]
    (or (= 2 alive-neighbors)
        (= 3 alive-neighbors))))

(def alive true)
(def dead false)

(defn eval-cell
  [x y board]
  (let [cell (get-cell x y board)
        alive-neighbors (count (filter alive?
                                       (get-neighbors x y board)))]
    (cond
      (and (alive? cell)
           (or (= 2 alive-neighbors)
               (= 3 alive-neighbors))) alive
      (and (dead? cell)
           (= 3 alive-neighbors)) alive
      (< 2 alive-neighbors) dead  ;; underpopulation
      (> 3 alive-neighbors) dead ;; overcrowding
      )))

(defn next-iteration
 [board]
  (let [row (first board)
        next-board (for [y (range (count board))
                         x (range (count row))]
                     (do
                       ;; (println "evaluating cell - x: " x ", y: " y)
                       (eval-cell x y board)))]
    (partition (count row) next-board)))

(defn display-board-terminal
  "Print the board to a terminal."
  [board]
  (dorun
   (map println board)))

(defn -main []
  (display-board-terminal (create-board columns rows)))

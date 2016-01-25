(ns game-of-life.core
  (:require [game-of-life.terminal-display :refer :all]))


(defn create-board
  "([cols rows] [cols rows row-func)

  Create a new board with dimensions using cols for width and rows for
  height.  By default it initializes all board cells to be dead.

  Also takes a row-func which will generate the cells for each row.
  The arity for the row-func should be one and accept the number of
  columns as its argument (fn [cols]).  It should return a vector of
  booleans the size of cols."
  ([num-columns num-rows]
   (let [row (vec (repeat num-columns false))
         board (vec (repeat num-rows row))]
     board))
  ([num-columns num-rows row-func]
   (let [board (vec (repeatedly num-rows
                                #(row-func num-columns)))]
     board)))


(defn generate-random-row
  "A row generating function for use in create-board.  Given the
  number of columns, return a vector of random booleans."
  [num-columns]
  (repeatedly num-columns #(if (>= 0.5 (rand 1)) true false)))


(defn create-random-board
  "Create a new board. Randomly assigns cells to be alive or dead."
  [num-columns num-rows]
  (let [board (vec (repeatedly num-rows
                               #(generate-random-row num-columns)))]
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

(def alive true)
(def dead false)
(def alive? true?)
(def dead? false?)

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
                     (eval-cell x y board))]
    (partition (count row) next-board)))


(defn -main []
  ;; Board dimensions.
  (def columns 80)
  (def rows 24)

  ;; Use a blinker to test the display loop.
  (def test-board [[false false false false false]
                   [false false false false false]
                   [false true  true  true  false]
                   [false false false false false]
                   [false false false false false]])
  (display-board-to-terminal (create-random-board columns rows) next-iteration))

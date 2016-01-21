(ns game-of-life.terminal-display)

(defn row->str
  "Given a row on the board, return its string representation.
  e.g. [false true true false] -> '.**.'"
  [row]
  (clojure.string/join ""
                       (map #(if (true? %) "*" ".") row)))

(defn board->str
  [board]
  (let [string-rows (for [row board]
                      (row->str row))]
    (clojure.string/join "\n" string-rows)))

(defn display-board-terminal
  "Print the board to a terminal."
  [board]
  (dorun
   (print (board->str board) "\n")))

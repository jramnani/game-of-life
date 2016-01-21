(ns game-of-life.terminal-display)

(defn row->str
  [row]
  (clojure.string/join ""
                       (map #(if (true? %) "*" ".") row)))

(defn print-board-to-str
  [board]
  (let [string-rows (for [row board]
                      (row->str row))]
    (clojure.string/join "\n" string-rows)))

(defn display-board-terminal
  "Print the board to a terminal."
  [board]
  (dorun
   (print-board-to-str board)))

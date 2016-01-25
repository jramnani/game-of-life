(ns game-of-life.terminal-display
  (:require [lanterna.terminal :as t]
            [lanterna.screen :as s]))

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

(defn print-board
  "Print the board to a terminal."
  [board]
  (dorun
   (print (board->str board) "\n")))

(defn display-board-to-terminal
  [board next-iteration-func]
  (let [cols (count (first board))
        rows (count board)
        scr (s/get-screen :swing
                          {:cols cols
                           :rows rows})]
    (s/in-screen scr
                 (loop [next-board board]
                   (dorun
                    (map-indexed #(s/put-string scr 0 %1 (row->str %2)) next-board))
                   (s/redraw scr)
                   (Thread/sleep 1000)
                   (recur (next-iteration-func next-board))))))

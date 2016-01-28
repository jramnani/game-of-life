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


(defn world->rows
  "Convert a sparse matrix world into a list of lists of booleans.
  This is necessary to plug into the display code."
  [world]
  (let [list-of-bools (for [y (range (:height world))
                            x (range (:width world))]
                        (contains? (:cells world) [x y]))]
    (partition (:width world) list-of-bools)))


(defn display-board-to-terminal
  [world next-iteration-func]
  (let [width (:width world)
        height (:height world)
        scr (s/get-screen :swing
                          {:cols width
                           :rows height})]
    (s/in-screen scr
                 (loop [next-world world]
                   (let [rows (world->rows next-world)]
                     (dorun
                      (map-indexed #(s/put-string scr 0 %1 (row->str %2)) rows))
                     (s/redraw scr)
                     (Thread/sleep 1000)
                     (recur (next-iteration-func next-world)))))))

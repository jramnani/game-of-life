(ns game-of-life.terminal-display
  (:require [lanterna.terminal :as t]
            [lanterna.screen :as s]))

(defn world->rows
  "Convert a sparse matrix world into a list of lists of booleans.
  This is necessary to plug into the display code.
  Given You want a 3x3 grid displayed.
  Then convert a world that looks like this:
    #{[0 0] [0 2]}
  To:
    [[true  false true]
     [false false false]
     [false false false]]
  "
  [world]
  (let [list-of-bools (for [y (range (:height world))
                            x (range (:width world))]
                        (contains? (:cells world) [x y]))]
    (partition (:width world) list-of-bools)))


(defn row->str
  "Given a row on the board, return its string representation.
  e.g. [false true true false] -> '.**.'"
  [row]
  (clojure.string/join ""
                       (map #(if (true? %) "*" ".") row)))

(defn display-loop-terminal
  [screen world next-iteration-func]
  (loop [next-world world]
    (let [rows (world->rows next-world)]
      (dorun
       (map-indexed #(s/put-string screen 0 %1 (row->str %2)) rows))
      (s/redraw screen)
      (Thread/sleep 1000)
      (recur (next-iteration-func next-world)))))


(defn display-board-to-terminal
  [world next-iteration-func]
  (let [width (:width world)
        height (:height world)
        screen (s/get-screen :swing
                          {:cols width
                           :rows height})]
    (s/in-screen screen
                 (display-loop-terminal screen world next-iteration-func))))

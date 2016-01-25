(defproject game-of-life "0.1.0-SNAPSHOT"
  :description "Conway's Game of Life"
  :url "http://example.com/FIXME"
  :license {:name "MIT License"
            :url "http://choosealicense.com/licenses/mit/"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 ;; Terminal output for showing the game.
                 [clojure-lanterna "0.9.4"]]
  :main game-of-life.core)

(ns game-of-life.core-test
  (:require [clojure.test :refer :all]
            [game-of-life.world :refer :all]
            [game-of-life.core :refer :all]))


;(deftest step-test
  ;(testing "A blinker oscillates between a horizontal and vertical line."
    ;(let [horizontal-blinker #{[0,1] [1,1] [2,1]}
          ;vertical-blinker #{[1,0] [1,1] [1,2]}
          ;world (create-world 5 5 horizontal-blinker)]
      ;(is (= vertical-blinker (:cells (step world))))
      ;(is (= horizontal-blinker (:cells (step (step world))))))))


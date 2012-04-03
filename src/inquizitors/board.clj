(ns inquizitors.board
  (:require [clojure.string :as string]))

(def board-x 100)
(def board
  (agent (vec (string/replace 
                (slurp "resources/boards/600w-3.board")
                #"\n" ""))))
(def directions {:n (- board-x) :w -1 :s board-x :e 1})

(defn inspect-board [board]
  (doseq [x (partition board-x board)]
    (println x)))

(defn valid-move? [board direction x-old x-new]
  (and
    (<= 0 x-old (dec (count board)))
    (<= 0 x-new (dec (count board)))
    (= (board x-new) \#)
    (if (#{:e :w} direction)
      (= (int (/ x-old board-x)) (int (/ x-new board-x)))
      true)))

; there must be a better way to do this, but I'm away from the clojure docs right now
(defn index-of [coll item]
  (first (keep-indexed #(if (= %2 item) %1) coll)))

(defn *move
  [board player direction callback]
  (let [x1 (index-of board player)
        x2 (+ x1 (directions direction))]
    (if (valid-move? board direction x1 x2)
      (let [new-board (assoc board x1 (nth board x2) x2 player)]
        (callback new-board)
        new-board)
      board)))

(defn move [player direction callback]
  (send-off board *move player direction callback))

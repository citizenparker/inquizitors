(ns inquizitors.board
  (:require [clojure.string :as string]))

(def board-x 100)
(def board (ref (vec (map (fn [x] {:tile x :item nil})
                   (string/replace
                     (slurp "resources/boards/600w-3.board")
                     #"\n" "")))))

(def directions {:n (- board-x) :w -1 :s board-x :e 1})

(defn rand-free-space
  "Finds a free, valid space on the board. Performance degrades as board fills up. Will never terminate on a completely full board."
  [board]
  (loop [x (rand-int (count board))]
    (let [element (nth board x)]
      (if (and
            (nil? (element :item))
            (= (element :tile) \#))
        x
        (recur (rand-int (count board)))))))

(defn inspect-board [board]
  (doseq [x (partition board-x board)]
    (println x)))

(defn valid-move? [board direction x-old x-new]
  (and
    (<= 0 x-old (dec (count board)))
    (<= 0 x-new (dec (count board)))
    (= ((board x-new) :tile) \#)
    (if (#{:e :w} direction)
      (= (int (/ x-old board-x)) (int (/ x-new board-x)))
      true)))

; there must be a better way to do this, but I'm away from the clojure docs right now
(defn item-index-of [coll item]
  (first (keep-indexed #(if (= (%2 :item) item) %1) coll)))

(defn *move
  [board player direction]
  (let [x1 (item-index-of board player)
        x2 (+ x1 (directions direction))]
    (if (valid-move? board direction x1 x2)
      (assoc board x1 (nth board x2) x2 (nth board x1))
      board)))

(defn move! [player direction]
  (dosync (alter board *move player direction)))

(defn- *add-player [board player]
  (let [x (rand-free-space board)
        element (nth board x)]
    (assoc board x (assoc element :item player))))

(defn add-player! [player]
  (dosync (alter board *add-player player)))

(defn stringify-board [board]
  (string/join (map #(or (:symbol (:item %1)) (:tile %1)) board)))

(defn on-board-changed [f]
  (add-watch board rand
             (fn [k r old-board new-board]
               (if (not= old-board new-board)
                 (f (stringify-board new-board))))))


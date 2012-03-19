(ns inquizitors.game)

(def world-x 4)
(def world-y 3)
(def world (agent (vec "#####d######")))
(def directions {:n (- world-x) :w -1 :s world-x :e 1})

(defn inspect-world [world]
  (doall
    (for [x (partition world-x world)]
      (println x))))

(defn valid-move? [world direction x-old x-new]
  (and
    (<= 0 x-old (dec (count world)))
    (<= 0 x-new (dec (count world)))
    (if (#{:e :w} direction)
      (= (int (/ x-old world-x)) (int (/ x-new world-x)))
      true)))

; there must be a better way to do this, but I'm away from the clojure docs right now
(defn index-of [coll item]
  (first (keep-indexed #(if (= %2 item) %1) coll)))

(defn *move
  [world player direction callback]
  (let [x1 (index-of world player)
        x2 (+ x1 (directions direction))]
    (if (valid-move? world direction x1 x2)
      (let [new-world (assoc (assoc world x1 (nth world x2)) x2 player)]
        (callback new-world)
        new-world)
      world)))

(defn move [player direction callback]
  (send-off world *move player direction callback))

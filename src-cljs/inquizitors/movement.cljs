(ns inquizitors.movement
  (:require [inquizitors.dom :as dom]
            [inquizitors.sockets :as sockets]
            [inquizitors.terminal :as terminal]))

(defmethod sockets/respond-to :map-update [msg]
  (terminal/clear)
  (let [world ((msg :payload) :world)
        world-x ((msg :payload) :world-x)]
    (doall
      (for [x (range 0 (count world))]
        (terminal/draw-glyph (nth world x) (mod x world-x) (.floor js/Math (/ x world-x)))))))

(def directions {37 :w 38 :n 39 :e 40 :s})

(defn process-keypress [keypress]
  (when-let [direction (directions keypress)]
    (sockets/send :movement direction)
    true))

(dom/on-keypress process-keypress)

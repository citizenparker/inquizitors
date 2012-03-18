(ns inquizitors.movement
  (:require [inquizitors.dom :as dom]
            [inquizitors.sockets :as sockets]))

(def directions {37 :w 38 :n 39 :e 40 :s})

(defn process-keypress [keypress]
  (when-let [direction (directions keypress)]
    (sockets/send :movement direction)
    true))

(dom/on-keypress process-keypress)

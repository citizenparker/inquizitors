(ns inquizitors.movement
  (:require [inquizitors.dom :as dom]
            [inquizitors.sockets :as sockets]
            [inquizitors.terminal :as terminal]
            [inquizitors.gamestate :as gamestate]))

(defmethod sockets/respond-to :map-update [msg]
  (let [world ((msg :payload) :world)
        world-x ((msg :payload) :world-x)]
    (terminal/draw-screen world world-x)))

(def directions {37 :w 38 :n 39 :e 40 :s})

(defn process-keypress [keypress]
  (when-let [direction (directions keypress)]
    (sockets/send :movement direction)
    true))

(defmethod gamestate/on-state-entered :playing []
  (dom/on-keypress process-keypress))

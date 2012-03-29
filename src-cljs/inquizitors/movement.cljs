(ns inquizitors.movement
  (:require [inquizitors.dom :as dom]
            [inquizitors.sockets :as sockets]
            [inquizitors.terminal :as terminal]))

(defmethod sockets/respond-to :map-update [msg]
  (let [world ((msg :payload) :world)
        world-x ((msg :payload) :world-x)]
    (terminal/draw-screen world world-x)))

(def directions {37 :w 38 :n 39 :e 40 :s})

(defn process-keypress [keypress]
  (when-let [direction (directions keypress)]
    (sockets/send :movement direction)
    true))

(def event-scope "movement")

(defn start-movement []
  (dom/on-keypress process-keypress event-scope))

(ns inquizitors.states.initializing
  (:require [inquizitors.terminal :as terminal]
            [inquizitors.gamestate :as gamestate]))

(defmethod gamestate/on-state-entered :initializing []
  (terminal/clear)
  (gamestate/signal-event :initialized))

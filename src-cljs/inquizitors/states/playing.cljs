(ns inquizitors.states.playing
  (:require [inquizitors.gamestate :as gamestate]
            [inquizitors.chat :as chat]
            [inquizitors.movement :as movement]))

(defmethod gamestate/on-state-entered :playing []
  (movement/start-movement)
  (chat/start-chat))

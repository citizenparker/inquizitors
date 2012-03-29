(ns inquizitors.states.registering
  (:require [inquizitors.registration :as registration]
            [inquizitors.gamestate :as gamestate]))

(defmethod gamestate/on-state-entered :registering []
  (registration/start-registration)
  (registration/draw-welcome-screen))

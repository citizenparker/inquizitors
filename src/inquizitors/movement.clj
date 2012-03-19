(ns inquizitors.movement
  (:require [inquizitors.game :as game])
  (:require [inquizitors.sockets :as sockets]))

(defn world-changed [new-world]
  (sockets/broadcast :map-update {:world new-world :world-x game/world-x}))

(defmethod sockets/respond-to :movement [msg]
  (game/move \d (msg :payload) world-changed))

(ns inquizitors.movement
  (:require [inquizitors.game :as game])
  (:require [inquizitors.communication :as communication]))

(defn world-changed [new-world]
  (communication/broadcast :map-update {:world new-world :world-x game/world-x}))

(defmethod communication/respond-to :movement [msg]
  (game/move \d (msg :payload) world-changed))

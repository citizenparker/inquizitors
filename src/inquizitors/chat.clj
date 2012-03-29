(ns inquizitors.chat
  (:require [inquizitors.communication :as communication]))

(defmethod communication/respond-to :chat [msg]
  (let [broadcast-chat (str (msg :name) ": " (msg :payload))]
    (communication/broadcast :chat broadcast-chat)))

(ns inquizitors.chat
  (:require [inquizitors.sockets :as sockets]))

(defmethod sockets/respond-to :chat [msg]
  (let [broadcast-chat (str (msg :name) ": " (msg :payload))]
    (sockets/broadcast :chat broadcast-chat)))

(ns inquizitors.chat
  (:require [inquizitors.sockets :as sockets]))

(defmethod sockets/respond-to :chat [msg]
  (let [broadcast-chat (str (msg :name) ": " (msg :payload))
        broadcast-msg (assoc msg :payload broadcast-chat)]
  (sockets/broadcast (pr-str broadcast-msg))))

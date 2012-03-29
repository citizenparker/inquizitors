(ns inquizitors.sockets
  (:use [lamina.core :only [receive]])
  (:require [inquizitors.communication :as communication]))

(defn connection-router [ch handshake]
  (receive ch
    (fn [message]
      (when message ; some WS clients seem to send null strings on browser close
        (communication/add-player ch message)))))

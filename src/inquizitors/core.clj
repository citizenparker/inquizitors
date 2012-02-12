(ns inquizitors.core
  (:use lamina.core aleph.http))

(def broadcast-channel (channel))

(defn chat-handler [ch handshake]
  (receive ch
    (fn [name]
      (println (str name " connected"))
      (siphon (map* #(str name ": " %) ch) broadcast-channel)
      (siphon broadcast-channel ch))
    (fn [name]
      (on-closed ch (fn [] (println (str name " disconnected")))))))

(ns inquizitors.communication
  (:use [lamina.core :only [enqueue siphon receive-all permanent-channel on-closed]])
  (:require [inquizitors.players :as players]))

(def broadcast-channel (permanent-channel))
(def player-channels (ref {}))

(defn broadcast [identifier message]
  (let [serialized-message (pr-str {:identifier identifier :payload message})]
    (println (str "OUTBOUND: " serialized-message))
    (enqueue broadcast-channel serialized-message)))

(defn add-channel [pchannels ch player]
  (assoc pchannels player ch))

(defn send-to-player [player identifier message]
  (let [serialized-message (pr-str {:identifier identifier :payload message})
        ch (player-channels player)]
    (println (str "OUTBOUND (" (player :name) "): " serialized-message))
    (enqueue ch serialized-message)))

(defmulti respond-to :identifier)

(defn responder-for-player [p]
  (fn [msg-str]
    (when msg-str
      (println (str "INBOUND: " msg-str))
      (let [msg (read-string msg-str)]
        (respond-to (assoc msg :player p))))))

(defn add-new-connection [ch msg-str]
  (siphon broadcast-channel ch)
  (println msg-str)
  (let [msg (read-string msg-str)
        player (players/add-player (msg :payload))]
    (dosync (alter player-channels add-channel ch player))
    (receive-all ch (responder-for-player player))
    (send-to-player player :registered player)))

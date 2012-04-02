(ns inquizitors.communication
  (:require [lamina.core :as lamina]
            [inquizitors.board :as board]
            [inquizitors.players :as players]))

(def broadcast-channel (lamina/permanent-channel))

(defn broadcast
  "Sends a message to all known players"
  [identifier message]
  (let [serialized-message (pr-str {:identifier identifier :payload message})]
    (println (str "OUTBOUND: " serialized-message))
    (lamina/enqueue broadcast-channel serialized-message)))

(def player-channels (ref {}))

(defn add-channel [pchannels ch player]
  (assoc pchannels player ch))

(defn send-to-player [player identifier message]
  (let [serialized-message (pr-str {:identifier identifier :payload message})
        ch (player-channels player)]
    (println (str "OUTBOUND (" (:name player) "): " serialized-message))
    (lamina/enqueue ch serialized-message)))

(defn board-changed [new-board]
  (broadcast :map-update {:world new-board :world-x board/board-x}))

(defn respond-to [msg]
  (if (= :chat (msg :identifier))
    (broadcast :chat
               (str (msg :name) ": " (msg :payload)))
    (board/move \d (msg :payload) board-changed)))

(defn responder-for-player [p]
  (fn [msg-str]
    (when msg-str
      (println (str "INBOUND: " msg-str))
      (let [msg (read-string msg-str)]
        (respond-to (assoc msg :player p))))))

(defn register!
  "Create a player for the new connection and wire it up appropriately"
  [ch msg-str]
  (lamina/siphon broadcast-channel ch)
  (println msg-str)
  (let [msg (read-string msg-str)
        name (msg :payload)
        player (players/player name)]
    (dosync
      (players/add! player)
      (alter player-channels assoc player ch))
    (lamina/receive-all ch (responder-for-player player))
    (send-to-player player :registered player)))

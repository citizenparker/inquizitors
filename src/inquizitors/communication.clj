(ns inquizitors.communication
  (:require [lamina.core :as lamina]
            [inquizitors.board :as board]
            [inquizitors.players :as players]
            [clojure.string :as string]))

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

(defn respond-to [msg]
  (if (= :chat (msg :identifier))
    (broadcast :chat
               (str ((msg :player) :name) ": " (msg :payload)))
    (board/move! (msg :player) (msg :payload))))

(board/on-board-changed
  (fn [new-board]
    (broadcast :map-update {:world new-board :world-x board/board-x})))

(defn responder-for-player [p]
  (fn [msg-str]
    (when msg-str
      (println (str "INBOUND: " msg-str))
      (let [msg (read-string msg-str)]
        (respond-to (assoc msg :player p))))))

(defn remover-for-player [p]
  (fn []
    (dosync
      (players/remove! p)
      (board/remove-player! p)
      (alter player-channels dissoc p))
    (broadcast :chat (str (:name p) " has disconnected. Booo!"))))

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
      (board/add-player! player)
      (alter player-channels assoc player ch))
    (lamina/receive-all ch (responder-for-player player))
    (lamina/on-closed ch (remover-for-player player))
    (send-to-player player :registered player)
    (broadcast :chat (str name " has joined!"))))

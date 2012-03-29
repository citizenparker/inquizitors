(ns inquizitors.communication
  (:use [lamina.core :only [enqueue siphon receive-all permanent-channel on-closed]])
  (:require [inquizitors.players :as players]))

(def broadcast-channel (permanent-channel))

(defn broadcast [identifier message]
  (let [serialized-message (pr-str {:identifier identifier :payload message})]
    (println (str "OUTBOUND: " serialized-message))
    (enqueue broadcast-channel serialized-message)))

(defmulti respond-to :identifier)

(defn responder-fn-for [name]
  (fn [msg-str]
    (when msg-str
      (println (str "INBOUND: " msg-str))
      (let [msg (read-string msg-str)]
        (respond-to (assoc msg :name name))))))

(defn add-player [ch message]
  (let [{name :payload} (read-string message)]
    (println (str name " connected"))
    (on-closed ch #(println (str name " disconnected")))
    (siphon broadcast-channel ch)
    (receive-all ch (responder-fn-for name))))

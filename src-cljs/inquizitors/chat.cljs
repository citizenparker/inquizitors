(ns inquizitors.chat
  (:require [inquizitors.dom :as dom]
            [inquizitors.sockets :as sockets]))

(def event-scope "chat")

(defn send-chat [msg]
  (sockets/send :chat msg)
  (dom/clear-user-input))

(defmethod sockets/respond-to :chat [{:keys [payload]}]
  (dom/append-to-log payload))

(defn start-chat []
  (dom/on-user-input send-chat event-scope))

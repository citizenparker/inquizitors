(ns inquizitors.core
  (:require [inquizitors.dom :as dom]
            [inquizitors.sockets :as sockets]))

(defn receive-message [msg]
  (dom/append-to-log msg)
  (dom/clear-user-input))

(def server (sockets/create-socket receive-message))

(defn send-message [msg]
  (dom/append-to-log (str "SENDING " msg))
  (.send server msg))

(dom/on-user-input (fn [e] (send-message e)))

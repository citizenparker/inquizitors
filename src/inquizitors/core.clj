(ns inquizitors.core
  (:use lamina.core aleph.http)
  (:require [ring.middleware.resource :as resource]))

(def broadcast-channel (channel))

(defn chat-handler [ch handshake]
  (receive ch
    (fn [name]
      (println (str name " connected"))
      (siphon (map* #(str name ": " %) ch) broadcast-channel)
      (siphon broadcast-channel ch))
    (fn [name]
      (on-closed ch (fn [] (println (str name " disconnected")))))))

(defn default-handler [request]
  {:status 404
   :headers {"content-type" "text/plain"}
   :body "Not found"})

(def static-files
  (-> default-handler
    (resource/wrap-resource "public")
    wrap-ring-handler))

(ns inquizitors.core
  (:use lamina.core aleph.http)
  (:require [ring.middleware.resource :as resource]
            [ring.middleware.file-info :as file-info]))

(def broadcast-channel (permanent-channel))

(defn chat-handler [ch handshake]
  (receive ch
    (fn [name]
      (println (str name " connected"))
      (on-closed ch (fn [] (println (str name " disconnected"))))
      (siphon (map* #(do (println (str name ": " %)) (str name ": " %)) ch) broadcast-channel)
      (siphon broadcast-channel ch))
    ))

(defn default-handler [request]
  {:status 404
   :headers {"content-type" "text/plain"}
   :body "Not found"})

(def static-files
  (-> default-handler
    (resource/wrap-resource "public")
    (file-info/wrap-file-info)
    wrap-ring-handler))

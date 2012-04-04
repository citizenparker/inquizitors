(ns inquizitors.server
  (:require [aleph.http :as aleph]
            [lamina.core :as lamina]
            [ring.middleware.resource :as resource]
            [ring.middleware.file-info :as file-info]
            [inquizitors.communication :as communication]))

(defn connection-router
  "Receives new connections. Some browsers will send null messages on close, causing a seemingly needless guard clause"
  [ch handshake]
  (lamina/receive ch
    (fn [message]
      (when message
        (communication/register! ch message)))))

(defn default-handler [request]
  {:status 404
   :headers {"content-type" "text/plain"}
   :body "Not found"})

(defn wrap-to-index [handler]
  (fn [req]
    (handler
      (update-in req [:uri] #(if (= "/" %) "/index.html" %)))))

(def static-file-server
  (-> default-handler
      (resource/wrap-resource "public")
      (file-info/wrap-file-info)
      (wrap-to-index)
      aleph/wrap-ring-handler))

(defn -main []
  (aleph/start-http-server
    (var connection-router)
    {:port 8080 :websocket true})
  (aleph/start-http-server
    static-file-server
    {:port 8888}))

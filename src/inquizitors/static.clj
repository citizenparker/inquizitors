(ns inquizitors.static
  (:use aleph.http)
  (:require [ring.middleware.resource :as resource]
            [ring.middleware.file-info :as file-info]))

(defn default-handler [request]
  {:status 404
   :headers {"content-type" "text/plain"}
   :body "Not found"})

(def static-file-server
  (-> default-handler
    (resource/wrap-resource "public")
    (file-info/wrap-file-info)
    wrap-ring-handler))

(ns inquizitors.server
  (:use aleph.http)
  (:require [inquizitors.sockets :as sockets]
            [inquizitors.static :as static]
            [inquizitors.chat]
            [inquizitors.movement]
            ))

(defn -main []
  (start-http-server sockets/connection-router {:port 8080 :websocket true})
  (start-http-server static/static-file-server {:port 8888} ))

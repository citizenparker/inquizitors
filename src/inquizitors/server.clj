(ns inquizitors.server
  (:use [aleph.http :only [start-http-server]])
  (:require [inquizitors.sockets :as sockets]
            [inquizitors.static :as static]
            [inquizitors.chat]
            [inquizitors.movement]
            ))

(defn -main []
  (start-http-server (var sockets/connection-router) {:port 8080 :websocket true})
  (start-http-server (var static/static-file-server) {:port 8888} ))

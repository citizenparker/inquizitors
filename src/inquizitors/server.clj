(ns inquizitors.server
  (:use inquizitors.core aleph.http))

(defn -main []
  (start-http-server chat-handler {:port 8080 :websocket true})
  (start-http-server static-files {:port 8888} ))

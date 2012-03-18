(ns inquizitors.sockets
  (:use [cljs.reader :only [read-string]]))

(def web-socket (or (.-WebSocket js/window) (.-MozWebSocket js/window)))

(defn create-socket [f]
  (let [ws (new web-socket "ws://localhost:8080")]
    (set! (.-onmessage ws) (fn [e] (f (.-data e))))
    ws
    ))

(defmulti respond-to :identifier)

(defn receive-message [msg]
  (let [data (read-string msg)]
    (respond-to data)))

(def server (create-socket receive-message ))

(defn send [identifier payload]
  (.send server (pr-str {:identifier identifier :payload payload})))

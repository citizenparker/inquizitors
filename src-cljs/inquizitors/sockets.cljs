(ns inquizitors.sockets
  (:use [cljs.reader :only [read-string]]))

(def web-socket (or (.-WebSocket js/window) (.-MozWebSocket js/window)))

(def host (str "ws://" (-> (.-location js/window) .-hostname) ":8080"))

(defn create-socket [f]
  (let [ws (new web-socket host)]
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

(ns inquizitors.sockets)

(def web-socket (or (.-WebSocket js/window) (.-MozWebSocket js/window)))

(defn create-socket [f]
  (let [ws (new web-socket "ws://localhost:8080")]
    (set! (.-onmessage ws) (fn [e] (f (.-data e))))
    ws
    ))

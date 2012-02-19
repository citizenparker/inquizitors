(ns inquizitors.sockets)

(def web-socket (or (.-WebSocket js/window) (.-MozWebSocket js/window)))

(defn create-socket []
  (let [ws (new web-socket "ws://localhost:8080")]
    (set! (.-onmessage ws) (fn [e] (.log js/console (str "Server: " (.-data e)))))
    (set! (.-onopen ws) (fn [e] (.send ws "sparker") (.send ws "Hello")))))

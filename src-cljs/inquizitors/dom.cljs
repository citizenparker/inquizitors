(ns inquizitors.dom
  (:use [jayq.core :only [$]]))

(defn on-load [f]
  (.load ($ js/window) f))

(defn on-user-input [f scope]
  (-> ($ :#chatbox)
    (.on (str "submit." scope)
      (fn [e] (.preventDefault e)
              (f (user-input))))))

(defn remove-user-input-fn [scope]
  (-> ($ :#chatbox)
    (.off (str "submit." scope))))

(defn on-keypress [f scope]
  (-> ($ (.-body js/document))
    (.on (str "keydown." scope)
      (fn [e]
        (when (f (.-keyCode e))
          (.preventDefault e)
          )))))

(defn clear-user-input [f]
  (-> ($ :#message)
    (.val "")))

(defn user-input []
  (.val ($ :#message)))

(defn append-to-log [msg]
  (let [el ($ :#log)
        obj (.get el 0)]
    (-> el
      (.append (str msg "<br>")))
    (set! (.-scrollTop obj) (.-scrollHeight obj))))

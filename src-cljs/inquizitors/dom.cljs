(ns inquizitors.dom
  (:use [jayq.core :only [$]]))

(defn on-load [f]
  (.load ($ js/window) f))

(defn on-user-input [f]
  (-> ($ :#chatbox)
    (.submit
      (fn [e] (.preventDefault e)
              (f (user-input))))))

(defn on-keypress [f]
  (-> ($ (.-body js/document))
    (.keydown
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
  (-> ($ :#log)
    (.append (str msg "<br>"))))

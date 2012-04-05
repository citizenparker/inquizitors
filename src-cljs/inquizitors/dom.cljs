(ns inquizitors.dom
  (:use [jayq.core :only [$]])
  (:require [clojure.string :as string]))

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

(defn html-escape [input]
  (reduce (fn [result [match replacement]] (string/replace result match replacement))
          input [["&" "&amp;"]
                 ["\"" "&quot;"]
                 ["'" "&#39;"]
                 ["<" "&lt;"]
                 [">" "&gt;"]]))

(defn append-to-log [msg]
  (let [el ($ :#log)
        obj (.get el 0)]
    (-> el
      (.append (str (html-escape msg) "<br>")))
    (set! (.-scrollTop obj) (.-scrollHeight obj))))

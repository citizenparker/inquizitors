(ns inquizitors.terminal
  (:require [clojure.string :as string]
            [inquizitors.dom :as dom]))

(def canvas  (.getElementById js/document "terminal"))
(def context (.getContext canvas "2d"))

(defn set-fill [& rgba]
  (set! (.-fillStyle context) (str "rgb(" (string/join "," rgba) ")")))

(defn init-context []
  (set-fill 0 0 0)
  (clear 0 0 (.-width canvas) (.-height canvas))
  (.save context))

(dom/on-ready init-context)

(defn rect [x y width height]
  (.fillRect context x y width height))

(defn clear
  ([] (.restore context))
  ([x y width height]
    (set-fill 0 0 0)
    (rect x y width height)))

(defn text [msg x y]
  (set! (.-font context) " 20px 'Anonymous Pro', 'Andale Mono', monospace")
  (set! (.-textBaseline context) "top")
  (set-fill 130 130 130)
  (.fillText context msg x y))

(def x-scale 10)
(def y-scale 18)

(defn draw-glyph [character x y]
  (let [x-actual (* x-scale x)
        y-actual (* y-scale y)]
    (println (str x-actual ":" y-actual))
    (clear x-actual y-actual x-scale y-scale)
    (text character x-actual y-actual)))

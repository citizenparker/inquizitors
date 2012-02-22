(ns inquizitors.terminal
  (:require [clojure.string :as string]))

(def canvas  (.getElementById js/document "terminal"))
(def context (.getContext canvas "2d"))

(defn set-fill [color]
  (set! (.-fillStyle context) (str "rgb(" (string/join "," color) ")")))

(defn rect [dimensions]
  (apply #(.fillRect context %1 %2 %3 %4) dimensions))

(defn clear []
  (set-fill [0 0 0])
  (rect [0 0 150 150]))

(defn text [msg x y]
  (set! (.-font context) " 20px 'Anonymous Pro', 'Andale Mono', monospace")
  (set! (.-textBaseline context) "top")
  (set-fill [90 90 90])
  (.fillText context msg x y))

; so, some lessons learned.
; With the settings above, (text "H" 40 40) will draw an H with a bounding box 20 px high and approximately 7-10 across. The top-left corner of this box will be located at 40, 40
; leaning towards an interface something like so:
; (draw-glyph :corridor [1 2])
;   This would translate to roughly
;   (clear [11 44] [10 20]) ; [x y] [width height]
;   (text "#" [11 44])

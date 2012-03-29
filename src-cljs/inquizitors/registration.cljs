(ns inquizitors.registration
  (:require [inquizitors.terminal :as terminal]
            [inquizitors.dom :as dom]
            [inquizitors.sockets :as sockets]
            [inquizitors.gamestate :as gamestate]))

(def event-scope "registration")

(defn send-registration [name]
  (sockets/send :registration name)
  (dom/clear-user-input))

(defn start-registration []
  (dom/on-user-input send-registration event-scope))

(defmethod sockets/respond-to :registered [_]
  (gamestate/signal-event :registered))

; this is a hilariously poor representation. However, as this is the only place it seems logical to have the client render a "view" in the terminal, I can live with it. Anyone with a better idea is still welcome to suggest it!
(def intro-text "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    __    __   __    ______    __  __    __    ______    __    ______   ______    ______    ______    /\\ \\  /\\ \"-.\\ \\  /\\  __ \\  /\\ \\/\\ \\  /\\ \\  /\\___  \\  /\\ \\  /\\__  _\\ /\\  __ \\  /\\  == \\  /\\  ___\\   \\ \\ \\ \\ \\ \\-.  \\ \\ \\ \\/\\_\\ \\ \\ \\_\\ \\ \\ \\ \\ \\/_/  /__ \\ \\ \\ \\/_/\\ \\/ \\ \\ \\/\\ \\ \\ \\  __<  \\ \\___  \\   \\ \\_\\ \\ \\_\\\\\"\\_\\ \\ \\___\\_\\ \\ \\_____\\ \\ \\_\\  /\\_____\\ \\ \\_\\   \\ \\_\\  \\ \\_____\\ \\ \\_\\ \\_\\ \\/\\_____\\   \\/_/  \\/_/ \\/_/  \\/___/_/  \\/_____/  \\/_/  \\/_____/  \\/_/    \\/_/   \\/_____/  \\/_/ /_/  \\/_____/                                                                                                                                                                                                                                                                                                                          IN THE GRIM DARKNESS OF THE FAR FUTURE, THERE IS ONLY TRIVIA.                                                                                                                                                                                                                                                          PLEASE ENTER YOUR NAME BELOW TO BEGIN.                        ")

(defn draw-welcome-screen []
  (terminal/draw-screen intro-text 99))

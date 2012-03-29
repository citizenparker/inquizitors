(ns inquizitors.gamestate
  (:require [inquizitors.dom :as dom]))

(def transitions [{:event :started :to-state :initializing}
                  {:event :initialized :to-state :registering}
                  {:event :registered :to-state :playing}])

(defmulti on-state-entered identity)

(defn signal-event [event-type]
  (.log js/console (str "Event: " (pr-str event-type)))
  (doseq [event (filter #(= (:event %) event-type) transitions)]
    (on-state-entered (event :to-state))))

(dom/on-load #(signal-event :started))

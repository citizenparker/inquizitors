(ns script.repl
  (:require [cljs.repl :as repl]
            [cljs.repl.browser :as browser]
            [inquizitors.server :as server]))

(defn cljs []
  (def env (browser/repl-env))
  (repl/repl env))

(server/-main)

(ns script.repl
  (:require [cljs.repl :as repl])
  (:require [cljs.repl.browser :as browser]))

(defn cljs []
  (def env (browser/repl-env))
  (repl/repl env))

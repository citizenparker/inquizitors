(defproject inquizitors "0.1.0-SNAPSHOT"
  :description "This is a thing"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/clojurescript "0.0-971"]
                 [aleph "0.3.0-beta11"]
                 [domina "1.0.0-alpha1"]
                 [ring "1.0.2"]
                 [jayq "0.1.0-SNAPSHOT"]]
  :plugins [[lein-cljsbuild "0.0.13"]]
  :cljsbuild {
    :source-path "src-cljs"
    :compiler {
      :output-to "resources/public/js/main.js"
      :pretty-print true}}
  :main inquizitors.server
  :repl-init script.repl)

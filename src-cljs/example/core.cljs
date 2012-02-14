(ns example.core
  (:use [example.util :only [by-id by-tag set-html!]]))

(defn ^:export play []
  (let [foo (by-id :foo)
        paras (by-tag :p)]
    (set-html! foo "Hello")
    (js/alert "ClojureScript says 'Boo!'")
    (doseq [p paras] (set-html! p "Gone!"))))

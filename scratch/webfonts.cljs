; I think at this time I've found a workaround for the "Webfonts loading slowly" problem and so this isn't needed.
; However, I still want to make sure this enters the official record of SCM given the complexity, if I'm mistaken.
(ns inquizitors.webfonts)

(defn cljmap->js
  "Convert a clojure map into a JavaScript object"
  [obj]
  (.-strobj (into {} (map (fn [[k v]]
                            (let [k (if (keyword? k) (name k) k)
                                  v (if (keyword? v) (name v) v)]
                              [k (clj->js v)]
                              ))
                          obj))))

(defn clj->js
  "Recursively transforms ClojureScript maps into Javascript objects,
   other ClojureScript colls into JavaScript arrays, and ClojureScript
   keywords into JavaScript strings."
  [x]
  (cond
    (string? x) x
    (keyword? x) (name x)
    (map? x) (cljmap->js x)
    (coll? x) (apply array (map clj->js x))
    :else x))

(def config {:google {:families ["Anonymous Pro"]} :active #(.log js/console "loaded")})

(dom/on-ready #(.load js/WebFont (clj->js config))

(ns inquizitors.players)

(def players (ref []))
(def symbols [\@ \$ \% \a \b \c \d \e \f \g \h \j \k \l])
(def colors [:blue :green :red :white])

(defn- new-player [name]
  {:name name :symbol (rand-nth symbols) :color (rand-nth colors)})

(defn- add-player* [all-players p]
  (conj all-players p))

(defn add-player [name]
  (let [p (new-player name)]
    (dosync (alter players add-player* p))
    p))

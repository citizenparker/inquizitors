(ns inquizitors.players)

(def players (ref []))
(def symbols [\@ \$ \% \a \b \c \d \e \f \g \h \j \k \l])
(def colors [:blue :green :red :white])

(defn player [name]
  {:name name :symbol (rand-nth symbols) :colors (rand-nth colors)})

(defn add! [player]
  (dosync
    (alter players conj player)))

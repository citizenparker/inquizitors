(ns inquizitors.players)

(def players (ref []))
(def symbols [\! \@ \$ \% \^ \& \* \> \< \? \= \a \A \b \B \c \C \d \D \e \E \f \F \g \G \h \H \i \I \j \J \k \K \L \m \M \n \N \o \O \p \P \q \Q \r \R \s \S \t \T \u \U \v \V \w \W \x \X \y \Y \z \Z])
(def colors [:blue :green :red :white :cyan :yellow :purple])

(defn player [name]
  {:name name :symbol (rand-nth symbols) :colors (rand-nth colors)})

(defn add! [player]
  (dosync
    (alter players conj player)))

(ns conway.rules)

(defn n [world x y] (get-in (:cells world) [(dec y) x]))
(defn ne [world x y] (get-in (:cells world) [(dec y) (inc x)]))
(defn e [world x y] (get-in (:cells world) [y (inc x)]))
(defn se [world x y] (get-in (:cells world) [(inc y) (inc x)]))
(defn s [world x y] (get-in (:cells world) [(inc y) x]))
(defn sw [world x y] (get-in (:cells world) [(inc y) (dec x)]))
(defn w [world x y] (get-in (:cells world) [y (dec x)]))
(defn nw [world x y] (get-in (:cells world) [(dec y) (dec x)]))

(defn live-neighbors [world x y]
  (count (keep #{:live} ((juxt n ne e se s sw w nw) world x y))))

(defn under-populated? [world x y]
  (let [cell (get-in (:cells world) [y x])]
    (and (= :live cell)
         (< (live-neighbors world x y) 2))))

(defn lives-on? [world x y]
  (let [cell (get-in (:cells world) [y x])]
    (and (= :live cell)
         (or (= (live-neighbors world x y) 2)
             (= (live-neighbors world x y) 3)))))

(defn over-crowded? [world x y]
  (let [cell (get-in (:cells world) [y x])]
    (and (= :live cell)
         (> (live-neighbors world x y) 3))))

(defn just-right? [world x y]
  (let [cell (get-in (:cells world) [y x])]
    (and (= :dead cell)
         (= (live-neighbors world x y) 3))))

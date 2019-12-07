(ns conway.rules)

(defn n [world x y] (get-in world [(dec y) x]))
(defn ne [world x y] (get-in world [(dec y) (inc x)]))
(defn e [world x y] (get-in world [y (inc x)]))
(defn se [world x y] (get-in world [(inc y) (inc x)]))
(defn s [world x y] (get-in world [(inc y) x]))
(defn sw [world x y] (get-in world [(inc y) (dec x)]))
(defn w [world x y] (get-in world [y (dec x)]))
(defn nw [world x y] (get-in world [(dec y) (dec x)]))

(defn neighbors [world x y]
  ((juxt n ne e se s sw w nw) world x y))

(defn live-neighbors [world x y]
  (count (keep #{:live} (neighbors world x y))))

(defn under-populated? [world x y]
  (let [cell (get-in world [y x])]
    (and (= :live cell)
         (< (live-neighbors world x y) 2))))

(defn lives-on? [world x y]
  (let [cell (get-in world [y x])]
    (and (= :live cell)
         (or (= (live-neighbors world x y) 2)
             (= (live-neighbors world x y) 3)))))

(defn over-crowded? [world x y]
  (let [cell (get-in world [y x])]
    (and (= :live cell)
         (> (live-neighbors world x y) 3))))

(defn reproduce? [world x y]
  (let [cell (get-in world [y x])]
    (and (= :dead cell)
         (= (live-neighbors world x y) 3))))

(defn live-or-die [world x y]
  (cond
    (under-populated? world x y) :dead
    (lives-on? world x y) :live
    (over-crowded? world x y) :dead
    (reproduce? world x y) :live
    :else :dead))

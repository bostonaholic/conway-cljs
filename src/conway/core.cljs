(ns conway.core
  (:require [conway.gui :as gui]))

(def columns 100)
(def rows 100)

(defn build-world [world]
  {:cells (into [] (map (partial into [])
                        (partition columns world)))
   :columns columns
   :rows rows})

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

(defonce seed-world
  (build-world
   (for [x (range columns)
         y (range rows)]
     (if (zero? (rand-int 10))
       :live
       :dead))))

(defonce g-world (atom seed-world))

(defn generate [ctx]
  (js/setInterval
   #(let [world @g-world
          new-world (build-world
                     (for [y (range rows)
                           x (range columns)]
                       (cond
                         (under-populated? world x y) :dead
                         (lives-on? world x y) :live
                         (over-crowded? world x y) :dead
                         (just-right? world x y) :live
                         :else :dead)))]
      (reset! g-world new-world)
      (gui/draw ctx new-world))
   1000))

(defn ^:export main []
  (let [canvas (.getElementById js/document "conway")
        ctx (.getContext canvas "2d")]
    (doto ctx
      (gui/draw @g-world)
      (generate))))

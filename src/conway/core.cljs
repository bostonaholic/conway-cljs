(ns conway.core
  (:require [conway.gui :as gui]
            [conway.rules :as rules]))

(def columns 100)
(def rows 100)

(defn build-world [world]
  {:cells (into [] (map (partial into [])
                        (partition columns world)))
   :columns columns
   :rows rows})

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
                         (rules/under-populated? world x y) :dead
                         (rules/lives-on? world x y) :live
                         (rules/over-crowded? world x y) :dead
                         (rules/just-right? world x y) :live
                         :else :dead)))]
      (reset! g-world new-world)
      (gui/draw ctx new-world))
   150))

(defn ^:export main []
  (let [canvas (.getElementById js/document "conway")
        ctx (.getContext canvas "2d")]
    (doto ctx
      (gui/draw @g-world)
      (generate))))

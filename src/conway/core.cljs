(ns conway.core
  (:require [conway.gui :as gui]
            [conway.rules :as rules]))

(defonce columns (dec (Math/floor (/ (.-width gui/canvas) gui/cell-width))))
(defonce rows (dec (Math/floor (/ (.-height gui/canvas) gui/cell-height))))

(defn build-world [cells]
  (mapv vec (partition columns cells)))

(defonce seed-world
  (build-world
   (for [_ (range (* columns rows))]
     (if (zero? (rand-int 10))
       :live
       :dead))))

(def world (atom seed-world))

(defn compute-diff
  "Takes two world objects as parameters and computes a 'diff'. This reduces the number of draws that need to occur on the canvas.

  The 'diff' object is structured as such:

  `[{:x 1 :y 2 :state :live} {:x 4 :y 7 :state :live} {:x 7 :y 2 :state :dead} {:x 9 :y 9 :state :live}]`"
  ([new-world]
   (compute-diff nil new-world))
  ([old-world new-world]
   (remove nil? (for [y (range rows)
                      x (range columns)]
                  (when-not (= (get-in old-world [y x])
                               (get-in new-world [y x]))
                    {:x x :y y :state (get-in new-world [y x])})))))

(defn generate []
  (let [old-world @world
        new-world (build-world
                   (for [y (range rows)
                         x (range columns)]
                     (rules/live-or-die old-world x y)))]
    (reset! world new-world)
    (compute-diff old-world new-world)))

(defn main []
  (gui/draw (compute-diff @world))
  (js/setInterval (comp gui/draw generate) 150))

(ns conway.core
  (:require [conway.gui :as gui]
            [conway.rules :as rules]))

(defonce seed-world
  (rules/dimensionalize (for [_ (range (* gui/columns gui/rows))]
                          (if (zero? (rand-int 5))
                            :live
                            :dead))
                        gui/columns))

(def world (atom seed-world))

(defn compute-diff
  "Takes two world objects as parameters and computes a 'diff'. This reduces the number of draws that need to occur on the canvas.

  The 'diff' object is structured as such:

  `[{:x 1 :y 2 :state :live} {:x 4 :y 7 :state :live} {:x 7 :y 2 :state :dead} {:x 9 :y 9 :state :live}]`"
  ([new-world]
   (compute-diff nil new-world))
  ([old-world new-world]
   (remove nil? (for [y (range gui/rows)
                      x (range gui/columns)]
                  (when-not (= (get-in old-world [y x])
                               (get-in new-world [y x]))
                    {:x x :y y :state (get-in new-world [y x])})))))

(defn generate []
  (let [old-world @world
        new-world (rules/generate old-world)]
    (reset! world new-world)
    (compute-diff old-world new-world)))

(def evolve (comp gui/draw generate))

(defn main []
  (gui/draw (compute-diff @world))
  (js/setInterval evolve 150))

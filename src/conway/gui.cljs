(ns conway.gui)

(defonce canvas (.getElementById js/document "conway"))
(defonce ctx (.getContext canvas "2d"))

(defonce cell-width 7)
(defonce cell-height 7)

(defn draw [diffs]
  (doall (for [diff diffs]
           (do (if (= :dead (:state diff))
                 (set! (.-fillStyle ctx) "#FAFAFA")
                 (set! (.-fillStyle ctx) (rand-nth ["#0A35C7" "#0A6CD1" "#008CBA" "#0AD1CE" "#0AC78E"])))
               (.fillRect ctx
                          (* (:x diff) cell-width) (* (:y diff) cell-height) 5 5)))))

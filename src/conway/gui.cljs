(ns conway.gui)

(defonce width 5)
(defonce height 5)
(defonce spacing 7)

(defonce canvas (.getElementById js/document "conway"))
(defonce ctx (.getContext canvas "2d"))

(defn draw [diffs]
  (doall (for [diff diffs]
           (do (if (= :dead (:state diff))
                 (set! (.-fillStyle ctx) "#FAFAFA")
                 (set! (.-fillStyle ctx) "#008CBA"))
               (.fillRect ctx
                          (* (:x diff) spacing) (* (:y diff) spacing) width height)))))

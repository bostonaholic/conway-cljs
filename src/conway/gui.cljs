(ns conway.gui)

(defonce canvas (.getElementById js/document "conway"))
(defonce ctx (.getContext canvas "2d"))

(defonce cell-width 7)
(defonce cell-height 7)

(defn draw [diffs]
  (doall (for [diff diffs]
           (do (if (= :dead (:state diff))
                 (set! (.-fillStyle ctx) "#FAFAFA")
                 (set! (.-fillStyle ctx) "#008CBA"))
               (.fillRect ctx
                          (* (:x diff) cell-width) (* (:y diff) cell-height) 5 5)))))

(ns conway.gui)

(defonce canvas (.getElementById js/document "conway"))
(defonce ctx (.getContext canvas "2d"))

(defn draw [diffs]
  (doall (for [diff diffs]
           (do (if (= :dead (:state diff))
                 (set! (.-fillStyle ctx) "#FAFAFA")
                 (set! (.-fillStyle ctx) "#008CBA"))
               (.fillRect ctx
                          (* (:x diff) 7) (* (:y diff) 7) 5 5)))))

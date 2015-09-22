(ns conway.gui
  (:require [goog.object :as object]))

(defonce cell-dimensions {:width 5 :height 5
                          :spacing 7})

(defn draw [diffs]
  (let [canvas (.getElementById js/document "conway")
        ctx (.getContext canvas "2d")]
    (object/set ctx "fillStyle" "#FAFAFA")
    (doall (map (fn [diff]
                  (.fillRect ctx
                             (* (:x diff) (:spacing cell-dimensions)) (* (:y diff) (:spacing cell-dimensions))
                             (:width cell-dimensions) (:height cell-dimensions)))
                (filter (fn [diff] (= :dead (:state diff))) diffs)))
    (object/set ctx "fillStyle" "#008CBA")
    (doall (map (fn [diff]
                  (.fillRect ctx
                             (* (:x diff) (:spacing cell-dimensions)) (* (:y diff) (:spacing cell-dimensions))
                             (:width cell-dimensions) (:height cell-dimensions)))
                (filter (fn [d] (= :live (:state d))) diffs)))))

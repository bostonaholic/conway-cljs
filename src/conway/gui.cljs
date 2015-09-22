(ns conway.gui
  (:require [goog.object :as object]))

(defonce cell-dimensions {:width 5 :height 5
                          :spacing 7})

(defn draw [ctx diffs]
  (object/set ctx "fillStyle" "#eee")
  (doall (map (fn [diff]
                (.fillRect ctx
                           (* (:x diff) (:spacing cell-dimensions)) (* (:y diff) (:spacing cell-dimensions))
                           (:width cell-dimensions) (:height cell-dimensions)))
              (filter (fn [diff] (= :dead (:state diff))) diffs)))
  (object/set ctx "fillStyle" "#c00")
  (doall (map (fn [diff]
                (.fillRect ctx
                           (* (:x diff) (:spacing cell-dimensions)) (* (:y diff) (:spacing cell-dimensions))
                           (:width cell-dimensions) (:height cell-dimensions)))
              (filter (fn [d] (= :live (:state d))) diffs))))

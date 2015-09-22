(ns conway.gui
  (:require [goog.object :as object]))

(defonce cell-dimensions {:width 5 :height 5
                          :spacing 7})

(defn draw [diffs]
  (let [tmp_canvas (.createElement js/document "canvas")
        tmp_ctx (.getContext tmp_canvas "2d")
        canvas (.getElementById js/document "conway")]
    (object/set tmp_canvas "width" (object/get canvas "width"))
    (object/set tmp_canvas "height" (object/get canvas "height"))
    (object/set tmp_ctx "fillStyle" "#FAFAFA")
    (doall (map (fn [diff]
                  (.fillRect tmp_ctx
                             (* (:x diff) (:spacing cell-dimensions)) (* (:y diff) (:spacing cell-dimensions))
                             (:width cell-dimensions) (:height cell-dimensions)))
                (filter (fn [diff] (= :dead (:state diff))) diffs)))
    (object/set tmp_ctx "fillStyle" "#008CBA")
    (doall (map (fn [diff]
                  (.fillRect tmp_ctx
                             (* (:x diff) (:spacing cell-dimensions)) (* (:y diff) (:spacing cell-dimensions))
                             (:width cell-dimensions) (:height cell-dimensions)))
                (filter (fn [d] (= :live (:state d))) diffs)))
    (.drawImage (.getContext canvas "2d") tmp_canvas 0 0)))

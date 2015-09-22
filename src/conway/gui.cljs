(ns conway.gui
  (:require [goog.object :as object]))

(defonce cell-dimensions {:width 5 :height 5
                          :spacing 7})

(defn draw [ctx world]
  (object/set ctx "fillStyle" "#eee")
  (dotimes [x (:columns world)]
    (dotimes [y (:rows world)]
      (if (= :dead (get-in (:cells world) [y x]))
        (.fillRect ctx
                   (* x (:spacing cell-dimensions)) (* y (:spacing cell-dimensions))
                   (:width cell-dimensions) (:height cell-dimensions)))))
  (object/set ctx "fillStyle" "#c00")
  (dotimes [x (:columns world)]
    (dotimes [y (:rows world)]
      (if (= :live (get-in (:cells world) [y x]))
        (.fillRect ctx
                   (* x (:spacing cell-dimensions)) (* y (:spacing cell-dimensions))
                   (:width cell-dimensions) (:height cell-dimensions))))))

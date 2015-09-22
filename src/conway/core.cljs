(ns conway.core)

(def columns 100)
(def rows 100)

(defonce cell-dimensions {:width 5 :height 5
                          :spacing 7})

(defn generate [ctx]
  (dotimes [x columns]
    (dotimes [y rows])))

(defn draw [ctx world]
  (dotimes [x columns]
    (dotimes [y rows]
      (if (= :dead (get-in world [y x]))
        (aset ctx "fillStyle" "#eee")
        (aset ctx "fillStyle" "#c00"))
      (.fillRect ctx
                 (* x (:spacing cell-dimensions)) (* y (:spacing cell-dimensions))
                 (:width cell-dimensions) (:height cell-dimensions)))))

(def seed-world
  (into [] (map (partial into [])
                (partition columns
                           (for [x (range (inc columns))
                                 y (range (inc rows))]
                             (if (zero? (rand-int 10))
                               :alive
                               :dead))))))

(defn ^:export main []
  (let [canvas (.getElementById js/document "conway")
        ctx (.getContext canvas "2d")]
    (doto ctx
      (draw seed-world))))

(main)

(ns conway.gui)

(defn- get-canvas []
  (.getElementById js/document "conway"))

(defn- create-canvas []
  (let [canvas (doto (.createElement js/document "canvas")
                 (aset "id" "conway"))]
    (when (.-body js/document)
      (.appendChild (.-body js/document) canvas))
    canvas))

(defonce ^:private canvas (or (get-canvas)
                              (create-canvas)))


(defonce ^:private ctx (.getContext canvas "2d"))

(defonce ^:private cell-width 5)
(defonce ^:private cell-height 5)
(defonce ^:private cell-border 2)

(defn- set-color [context color]
  (set! (.-fillStyle context) color))

(defn- draw-cell [context color x y]
  (doto context
    (set-color color)
    (.fillRect x y cell-width cell-height)))

(defn- draw-dead-cell [context x y]
  (draw-cell context "#FAFAFA" x y))

(defn- draw-live-cell [context x y]
  (draw-cell context (rand-nth ["#0A35C7" "#0A6CD1" "#008CBA" "#0AD1CE" "#0AC78E"]) x y))

(defn- draw-diff [diff]
  (if (= :dead (:state diff))
    (draw-dead-cell ctx (* (:x diff) (+ cell-width cell-border)) (* (:y diff) (+ cell-height cell-border)))
    (draw-live-cell ctx (* (:x diff) (+ cell-width cell-border)) (* (:y diff) (+ cell-height cell-border)))))

(defonce columns (dec (Math/floor (/ (.-width canvas) (+ cell-width cell-border)))))
(defonce rows (dec (Math/floor (/ (.-height canvas) (+ cell-height cell-border)))))

(defn draw [diffs]
  (doall (map draw-diff diffs)))

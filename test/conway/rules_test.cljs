(ns conway.rules-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [conway.rules :as rules]))

(deftest live-or-die-test
  (testing "under populated"
    (testing "with no live neighbors"
      (let [world [[:dead :dead :dead]
                   [:dead :live :dead]
                   [:dead :dead :dead]]]
        (is (= :dead (rules/live-or-die world 1 1)))))

    (testing "with one live neighbor"
      (let [world [[:dead :live :dead]
                   [:dead :live :dead]
                   [:dead :dead :dead]]]
        (is (= :dead (rules/live-or-die world 1 1))))))

  (testing "lives on"
    (testing "with two live neighbors"
      (let [world [[:dead :live :live]
                   [:dead :live :dead]
                   [:dead :dead :dead]]]
        (is (= :live (rules/live-or-die world 1 1)))))

    (testing "with three live neighbors"
      (let [world [[:live :live :live]
                   [:dead :live :dead]
                   [:dead :dead :dead]]]
        (is (= :live (rules/live-or-die world 1 1))))))

  (testing "over crowded"
    (testing "with four live neighbors"
      (let [world [[:live :live :live]
                   [:live :live :dead]
                   [:dead :dead :dead]]]
        (is (= :dead (rules/live-or-die world 1 1))))))

  (testing "reproduce"
    (testing "with three live neighbors"
      (let [world [[:live :live :live]
                   [:dead :dead :dead]
                   [:dead :dead :dead]]]
        (is (= :live (rules/live-or-die world 1 1)))))))

(deftest still-lifes-test
  (testing "block"
    (let [world [[:dead :dead :dead :dead]
                 [:dead :live :live :dead]
                 [:dead :live :live :dead]
                 [:dead :dead :dead :dead]]]
      (is (= world (rules/generate world)))))

  (testing "beehive"
    (let [world [[:dead :dead :dead :dead :dead :dead]
                 [:dead :dead :live :live :dead :dead]
                 [:dead :live :dead :dead :live :dead]
                 [:dead :dead :live :live :dead :dead]
                 [:dead :dead :dead :dead :dead :dead]]]
      (is (= world (rules/generate world)))))

  (testing "loaf"
    (let [world [[:dead :dead :dead :dead :dead :dead]
                 [:dead :dead :live :live :dead :dead]
                 [:dead :live :dead :dead :live :dead]
                 [:dead :dead :live :dead :live :dead]
                 [:dead :dead :dead :live :dead :dead]
                 [:dead :dead :dead :dead :dead :dead]]]
      (is (= world (rules/generate world)))))

  (testing "boat"
    (let [world [[:dead :dead :dead :dead :dead]
                 [:dead :live :live :dead :dead]
                 [:dead :live :dead :live :dead]
                 [:dead :dead :live :dead :dead]
                 [:dead :dead :dead :dead :dead]]]
      (is (= world (rules/generate world)))))

  (testing "tub"
    (let [world [[:dead :dead :dead :dead :dead]
                 [:dead :dead :live :dead :dead]
                 [:dead :live :dead :live :dead]
                 [:dead :dead :live :dead :dead]
                 [:dead :dead :dead :dead :dead]]]
      (is (= world (rules/generate world))))))

(deftest oscillators-test
  (testing "blinker"
    (let [period1 [[:dead :dead :dead :dead :dead]
                   [:dead :dead :live :dead :dead]
                   [:dead :dead :live :dead :dead]
                   [:dead :dead :live :dead :dead]
                   [:dead :dead :dead :dead :dead]]

          period2 [[:dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead]
                   [:dead :live :live :live :dead]
                   [:dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead]]]
      (is (= period2 (rules/generate period1)))
      (is (= period1 (rules/generate period2)))))

  (testing "toad"
    (let [period1 [[:dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead]
                   [:dead :dead :live :live :live :dead]
                   [:dead :live :live :live :dead :dead]
                   [:dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead]]

          period2 [[:dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :live :dead :dead]
                   [:dead :live :dead :dead :live :dead]
                   [:dead :live :dead :dead :live :dead]
                   [:dead :dead :live :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead]]]
      (is (= period2 (rules/generate period1)))
      (is (= period1 (rules/generate period2)))))

  (testing "beacon"
    (let [period1 [[:dead :dead :dead :dead :dead :dead]
                   [:dead :live :live :dead :dead :dead]
                   [:dead :live :live :dead :dead :dead]
                   [:dead :dead :dead :live :live :dead]
                   [:dead :dead :dead :live :live :dead]
                   [:dead :dead :dead :dead :dead :dead]]

          period2 [[:dead :dead :dead :dead :dead :dead]
                   [:dead :live :live :dead :dead :dead]
                   [:dead :live :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :live :dead]
                   [:dead :dead :dead :live :live :dead]
                   [:dead :dead :dead :dead :dead :dead]]]
      (is (= period2 (rules/generate period1)))
      (is (= period1 (rules/generate period2)))))

  (testing "pulsar"
    (let [period1 [[:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :live :live :live :dead :dead :dead :live :live :live :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :live :dead :dead :dead :dead :live :dead :live :dead :dead :dead :dead :live :dead :dead]
                   [:dead :dead :live :dead :dead :dead :dead :live :dead :live :dead :dead :dead :dead :live :dead :dead]
                   [:dead :dead :live :dead :dead :dead :dead :live :dead :live :dead :dead :dead :dead :live :dead :dead]
                   [:dead :dead :dead :dead :live :live :live :dead :dead :dead :live :live :live :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :live :live :live :dead :dead :dead :live :live :live :dead :dead :dead :dead]
                   [:dead :dead :live :dead :dead :dead :dead :live :dead :live :dead :dead :dead :dead :live :dead :dead]
                   [:dead :dead :live :dead :dead :dead :dead :live :dead :live :dead :dead :dead :dead :live :dead :dead]
                   [:dead :dead :live :dead :dead :dead :dead :live :dead :live :dead :dead :dead :dead :live :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :live :live :live :dead :dead :dead :live :live :live :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]]

          period2 [[:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :live :dead :dead :dead :dead :dead :live :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :live :dead :dead :dead :dead :dead :live :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :live :live :dead :dead :dead :live :live :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :live :live :live :dead :dead :live :live :dead :live :live :dead :dead :live :live :live :dead]
                   [:dead :dead :dead :live :dead :live :dead :live :dead :live :dead :live :dead :live :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :live :live :dead :dead :dead :live :live :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :live :live :dead :dead :dead :live :live :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :live :dead :live :dead :live :dead :live :dead :live :dead :live :dead :dead :dead]
                   [:dead :live :live :live :dead :dead :live :live :dead :live :live :dead :dead :live :live :live :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :live :live :dead :dead :dead :live :live :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :live :dead :dead :dead :dead :dead :live :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :live :dead :dead :dead :dead :dead :live :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]]

          period3 [[:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :live :live :dead :dead :dead :dead :dead :live :live :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :live :live :dead :dead :dead :live :live :dead :dead :dead :dead :dead]
                   [:dead :dead :live :dead :dead :live :dead :live :dead :live :dead :live :dead :dead :live :dead :dead]
                   [:dead :dead :live :live :live :dead :live :live :dead :live :live :dead :live :live :live :dead :dead]
                   [:dead :dead :dead :live :dead :live :dead :live :dead :live :dead :live :dead :live :dead :dead :dead]
                   [:dead :dead :dead :dead :live :live :live :dead :dead :dead :live :live :live :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :live :live :live :dead :dead :dead :live :live :live :dead :dead :dead :dead]
                   [:dead :dead :dead :live :dead :live :dead :live :dead :live :dead :live :dead :live :dead :dead :dead]
                   [:dead :dead :live :live :live :dead :live :live :dead :live :live :dead :live :live :live :dead :dead]
                   [:dead :dead :live :dead :dead :live :dead :live :dead :live :dead :live :dead :dead :live :dead :dead]
                   [:dead :dead :dead :dead :dead :live :live :dead :dead :dead :live :live :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :live :live :dead :dead :dead :dead :dead :live :live :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead :dead]]]
      (is (= period2 (rules/generate period1)))
      (is (= period3 (rules/generate period2)))
      (is (= period1 (rules/generate period3)))))

  (testing "pentadecathlon"
    ;; TODO: 15 period oscillator :O
    ))

(deftest spaceships-test
  (testing "glider"
    (let [period1 [[:dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :live :dead :dead :dead :dead]
                   [:dead :dead :dead :live :dead :dead :dead]
                   [:dead :live :live :live :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead]]
          period2 [[:dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead]
                   [:dead :live :dead :live :dead :dead :dead]
                   [:dead :dead :live :live :dead :dead :dead]
                   [:dead :dead :live :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead]]
          period3 [[:dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :live :dead :dead :dead]
                   [:dead :live :dead :live :dead :dead :dead]
                   [:dead :dead :live :live :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead]]
          period4 [[:dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :live :dead :dead :dead :dead]
                   [:dead :dead :dead :live :live :dead :dead]
                   [:dead :dead :live :live :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead]]
          ;; NOTE: period5 is period 1 shifted down one, right one
          period5 [[:dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead]
                   [:dead :dead :dead :live :dead :dead :dead]
                   [:dead :dead :dead :dead :live :dead :dead]
                   [:dead :dead :live :live :live :dead :dead]
                   [:dead :dead :dead :dead :dead :dead :dead]]]
      (is (= period2 (rules/generate period1)))
      (is (= period3 (rules/generate period2)))
      (is (= period4 (rules/generate period3)))
      (is (= period5 (rules/generate period4)))))

  (testing "light-weight spaceship (LWSS)")

  (testing "middle-weight spaceship (MWSS)")

  (testing "heavy-weight spaceship (HWSS)"))

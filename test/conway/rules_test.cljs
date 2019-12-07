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

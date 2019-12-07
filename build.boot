(set-env!
 :source-paths #{"src"}
 :resource-paths #{"html"}
 :dependencies '[[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.597"]

                 ;; test
                 [adzerk/boot-cljs "2.1.5" :scope "test"]
                 [crisptrutski/boot-cljs-test "0.3.4" :scope "test"]
                 [pandeiro/boot-http "0.8.3" :scope "test"]])

(require
 '[adzerk.boot-cljs :refer [cljs]]
 '[crisptrutski.boot-cljs-test :refer [test-cljs]]
 '[pandeiro.boot-http :refer [serve]])

(task-options! test-cljs {:js-env :phantom})

(deftask deps [] identity)

(deftask test-once []
  (merge-env! :source-paths #{"test"})
  (test-cljs))

(deftask test-auto []
  (merge-env! :source-paths #{"test"})
  (comp (watch)
        (test-cljs)))

(deftask dev []
  (comp (serve)
        (watch)
        (cljs)))

(deftask dist []
  (comp (cljs :optimizations :advanced)
        (target)))

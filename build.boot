(set-env!
 :source-paths #{"src"}
 :resource-paths #{"html"}
 :dependencies '[[adzerk/boot-cljs "1.7.228-1" :scope "test"]
                 [adzerk/boot-reload "0.4.7" :scope "test"]
                 [crisptrutski/boot-cljs-test "0.2.1" :scope "test"]
                 [pandeiro/boot-http "0.7.3" :scope "test"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.51"]])

(require
 '[adzerk.boot-cljs :refer [cljs]]
 '[adzerk.boot-reload :refer [reload]]
 '[crisptrutski.boot-cljs-test :refer [test-cljs]]
 '[pandeiro.boot-http :refer [serve]])

(task-options! test-cljs {:js-env :phantom})

(deftask test []
  (merge-env! :source-paths #{"test"})
  (test-cljs))

(deftask test-auto []
  (merge-env! :source-paths #{"test"})
  (comp (watch)
        (test-cljs)))

(deftask dev []
  (comp (serve :dir "target/")
        (watch)
        (reload :on-jsload 'conway.core/main)
        (cljs)))

(deftask dist []
  (cljs :optimizations :advanced))

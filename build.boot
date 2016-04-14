(set-env!
 :source-paths #{"src"}
 :resource-paths #{"html"}
 :dependencies '[[adzerk/boot-cljs "1.7.48-5" :scope "test"]
                 [adzerk/boot-reload "0.4.7" :scope "test"]
                 [pandeiro/boot-http "0.7.3" :scope "test"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.40"]])

(require
 '[adzerk.boot-cljs :refer [cljs]]
 '[adzerk.boot-reload :refer [reload]]
 '[pandeiro.boot-http :refer [serve]])

(deftask dev []
  (comp (serve :dir "target/")
        (watch)
        (reload :on-jsload 'conway.core/main)
        (cljs :source-map true :optimizations :whitespace)))

(deftask dist []
  (cljs :optimizations :advanced))

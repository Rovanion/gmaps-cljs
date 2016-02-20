(defproject gmaps-cljs "0.0.2"

  :description "Clojurescript wrapper for Google Maps API v3"

  :url "https://github.com/tgetgood/gmaps-cljs"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :scm {:name "git"
        :url "https://github.com/tgetgood/gmaps-cljs"}

  :profiles {:dev {:plugins [[com.cemerick/austin "0.1.6"]]}}

  :plugins [[lein-cljsbuild "1.1.2" :exclusions [[org.clojure/clojure]]]
            [com.cemerick/clojurescript.test "0.3.3"]]

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]

                 [org.clojure/core.async "0.2.374"
                  :exclusions [org.clojure/tools.reader]]

                 [org.omcljs/om "0.8.8"]
                 [prismatic/om-tools "0.3.10"]

                 [cljsjs/google-maps "3.18-1"]

                 [com.cemerick/double-check "0.6.1"]

                 [reagent "0.5.1"]]

  :source-paths ["src"]
  
  :cljsbuild {
              :builds [{:id "dev"
                        :source-paths ["src"]
                        :compiler {:pretty-print true
                                   :optimizations :whitespace
                                   :cache-analysis true
                                   :output-to "resources/public/js/compiled/gmaps.js"}}]}
  )

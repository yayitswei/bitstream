(defproject froggychat "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-devel "1.1.8"]
                 [ring/ring-core "1.1.8"]
                 [compojure "1.1.5"]
                 [org.clojure/tools.logging "0.2.3"]
                 [hiccup "1.0.2"]
                 [http-kit "2.0.0"]]
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler froggychat.handler/app}
  :main froggychat.handler
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})

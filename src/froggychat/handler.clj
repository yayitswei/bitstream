(ns froggychat.handler
  (:gen-class)
  (:use compojure.core
        [hiccup.page :only [html5 include-css include-js]]
        [clojure.tools.logging :only [info debug warn error]]
        [hiccup.core :only [html]]
        org.httpkit.server)
  (:require [compojure.handler :as handler]
            [ring.middleware.reload :as reload]
            [compojure.route :as route]))

(def prod? (System/getenv "LEIN_NO_DEV"))

(defn layout [& {:keys [content script]}]
  (html
    [:head
     [:title "Froggychat"]]
    [:body.froggychat
     content
     (include-css "/css/styles.css")
     (include-js "http://simplewebrtc.com/latest.js")
     (include-css "//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/css/bootstrap-combined.min.css")
     (include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js")
     (include-js "//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js")
     (include-js "/js/init.js")
     script]))

(defn index []
  (layout :content
          [:div.container
           [:h1 "Wei and Dawn's Froggychat"]
           [:div#remotesVideos.videobox]
           [:div#localVideo.videobox]]))

(defroutes app-routes
  (GET "/" [] (index))
  (route/resources "/")
  (route/not-found "Not Found"))

(defn- wrap-request-logging [handler]
  (fn [{:keys [request-method uri] :as req}]
    (let [resp (handler req)]
      (info (name request-method) (:status resp)
            (if-let [qs (:query-string req)]
              (str uri "?" qs) uri))
      resp)))

(def app
  (wrap-request-logging
    (if prod?
      (handler/site app-routes)
      (reload/wrap-reload (handler/site #'app-routes)))))


(defn start-app [port]
  (run-server app {:port port :join? false}))

(defn -main [& args]
  (let [port (Integer/parseInt
               (or (System/getenv "PORT") "8080"))]
    (start-app port)))

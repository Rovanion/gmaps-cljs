(ns gmaps.map.markers
  (:require [clojure.set :as s]
            [gmaps.location :as loc]
            [markerclusterer]))

(def ^:private markers (atom {}))

(defn- create-infowindow
  [content]
  (let [cnt (clj->js {"content" content})
        infowindow (google.maps.InfoWindow. cnt)]
    infowindow))

(defn- create-marker
  [data map-obj]
  (let [marker (google.maps.Marker. (clj->js data))]

    (when (contains? data :infowindow)
      (let [infowindow (create-infowindow (get data :infowindow))]
        (.addListener marker "click" (fn []
                                       (.open infowindow map-obj marker)))))
    marker))

(defn- create-clusterer
  [map]
  (new js/MarkerClusterer map))

;; TODO: How much memory do these guys take up? Do I need to care?
(defn- get-marker
  [data map-obj]
  (when-not (contains? @markers (get data :title))
    (swap! markers assoc (get data :title) (create-marker data map-obj)))
  (get @markers (get data :title)))

(defn- setify [coll]
  (if (set? coll)
    coll
    (into #{} coll)))

(defn update-markers! [map-obj old new]
  (when-not (= old new)
    (let [old (setify old)
          new (setify new)]
      (when-not (= old new)
        ;; Remove markers no longer present
        (doseq [md (s/difference old new)]
          (.setMap (get-marker md map-obj) nil))
        ;; Add new markers to map
        (doseq [md (s/difference new old)]
          (.setMap (get-marker md map-obj) map-obj))))))

(defn update-markers-clusterer! [clusterer map-obj old new]
  (when-not (= old new)
    (let [old (setify old)
          new (setify new)]
      (when-not (= old new)
        ;; Remove markers no longer present
        (doseq [md (s/difference old new)]
          (.removeMarker clusterer (get-marker md map-obj)))
        ;; Add new markers to clusterer
        (doseq [md (s/difference new old)]
          (.addMarker clusterer (get-marker md map-obj)))))))

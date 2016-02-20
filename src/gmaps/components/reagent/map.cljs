(ns gmaps.components.reagent.map
  (:require [gmaps.core :as gmaps]
            [reagent.core :as reagent]))


(defn map-render [_]
  [:div {:id "map-canvas" :ref "map-canvas" :style {:height "500px" :width "700px"}}])

(defn map-did-mount [this]
  (gmaps/attach-map! (reagent/dom-node this) (reagent/state this)))

(defn map-will-unmount [this]
  (gmaps/detach-map! (reagent/dom-node this)))

(defn map-did-update [this]
  (let [[_ data] (reagent/argv this)]
    (gmaps/update-map! (reagent/dom-node this) data)))

(defn map-view [map-data]
  (reagent/create-class {:display-name "map-view-component"
                         :reagent-render map-render
                         :component-did-mount map-did-mount
                         :component-did-update map-did-update
                         :component-will-unmount map-will-unmount
                         :get-initial-state (fn [this]
                                              (println "Getting initial state")
                                              (reagent/set-state this map-data))}))
(ns gmaps.components.om.map
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [gmaps.maps-wrapper :as mw]
            [om-tools.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [om.core :as om :include-macros true]
            [cljs.core.async :as async :refer [put! >! <! chan]]))

(defcomponent map-view [app owner]
  (did-mount [_]
    (let [map-data (om/get-props owner)]
      (mw/attach-map! (om/get-node owner "map-canvas") map-data)))
  (did-update [_ props state]
    (mw/update-map! (om/get-node owner "map-canvas") props))
  (will-unmount [_]
    ;; Release the google.maps.Map for this component
    (mw/detach-map! (om/get-node owner "map-canvas")))
  (render [_]
    (dom/div {:id "map-view"}
      (dom/div {:id "map-canvas" :ref "map-canvas"}))))


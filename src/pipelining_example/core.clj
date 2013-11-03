(ns pipelining-example.core
  (:require [clojure.core.async :refer [chan go put! <!]]))

(defn a [x]
  (println "[A]:" x)
  x)

(defn b [x]
  (println "[B]:" x)
  x)

(defn c [x]
  (println "[C]:" x)
  x)

;;; ((comp c b a) 42)

(def printer (agent nil))

(defn m [x]
  (send printer (fn [_] (println "[A]:" x)))
  (Thread/sleep 500)
  x)

(defn n [x]
  (send printer (fn [_] (println "[B]:" x)))
  (Thread/sleep 1000)
  x)

(defn o [x]
  (send printer (fn [_] (println "[C]:" x)))
  (Thread/sleep 2000)
  x)

;;; ((comp o n m) 42)

(defn pipeline []
  (let [bound 10000
        m-ch (chan bound)
        n-ch (chan bound)
        o-ch (chan bound)]
    (go (while true (put! n-ch (m (<! m-ch)))))
    (go (while true (put! o-ch (n (<! n-ch)))))
    (go (while true (o (<! o-ch))))
    m-ch))

(defn demo []
  (let [head-ch (pipeline)]
    (doseq [k (range 10)]
      (go (put! head-ch k)))))

;;; (demo)


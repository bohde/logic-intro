(ns logic-intro.core
  (:refer-clojure :exclude [==])
  (:use clojure.core.logic))

;; What are all values of `q` which fail? 
(run* [q]
      fail)

;; What are all values of `q` which succeed? 
(run* [q]
      succeed)

;; What are all the values `q` where `q` unifies to 'apple? 
(run* [q]
      (== q 'apple))

;; order of unification doesn't matter
(run* [q]
      (== 'apple q))

;; Each term is a goal, and each goal needs to succeed
(run* [q]
      succeed
      (== q 'apple))

(run* [q]
      fail
      (== q 'apple))

;; What are all the values of `q` where `q` is 'apple and 'banana ? 
(run* [q]
      (== q 'apple)
      (== q 'banana))


;; fresh can be used to declare new logical variables
(run* [q]
      (fresh [x]
             (== x 'apple)
             (== q x)))

;; conde is like cond for logical variables
(run* [q]
      (conde
       [(== 'apple q)]
       [(== 'banana q)]))

(ns logic-intro.core
  (:refer-clojure :exclude [==])
  (:use clojure.core.logic))

;; What are all values of `q` which fail? 
(run* [q]
      fail)

;; What are all values of `q` which succeed? 
(run* [q]
      succeed)

;; What if we add more terms? 
(run* [q]
      fail
      succeed)

(run* [q]
      succeed
      succeed)


;; What does `==` do? 
(run* [q]
      (== 'apple 'banana))

;; So does `==` works like equality? 
(run* [q]
      (== q 'apple))

;; What happends when we reverse the order? 
(run* [q]
      (== 'apple q))

;; What if we have multiple unifications of the same variable? 
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


;; firsto is like first, but for logical variables
(run* [q]
      (firsto '(a p p l e) q))

(run* [q]
      (firsto (list q 'p 'p 'l 'e) 'a))


;; resto is like rest, but for logical variables
(run* [q]
      (resto '(a p p l e) q))

(run* [q]
      (resto q '(p p l e)))


;; membero is like member
(run* [q]
      (membero 'apple '(apple banana cherry))
      (== true q))


(run* [q]
      (fresh [x y z]
             (== (list x y z) q)
             (membero 'apple q)))


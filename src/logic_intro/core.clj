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


(first '(a p p l e))

;; firsto is like first, but for logical variables
(run* [q]
      (firsto '(a p p l e) q))

(run* [q]
      (firsto (list q 'p 'p 'l 'e) 'a))


(rest '(a p p l e))

;; resto is like rest, but for logical variables
(run* [q]
      (resto (list 'a 'p 'p 'l 'e) q))

(run* [q]
      (resto q (list 'p 'p 'l 'e)))


;; membero is like member
(run* [q]
      (membero 'apple (list 'apple  'banana 'cherry))
      (== true q))


(run* [q]
      (fresh [x y z]
             (== (list x y z) q)
             (membero 'apple q)))

;; How do we write functions like firsto and membero?

;; Let's write f function pair, which checks if x is a pair
(defn pair? [x]
  (or (lcons? x) (and (coll? x) (seq x))))

;; In Clojure linked-lists aren't often idiomatic,
;; so core.logic provides it's own cons operators to
;; mimic Scheme
(pair? (lcons 'a 'b))
(pair? (lcons 'a (lcons 'b 'c)))
(pair? (llist 'a 'b 'c))
(pair? ['a 'b 'c])

;; How would we write this using logical programming?
(defn pairo [p]
  (fresh [a d]
         (conso a d p)))

(run* [q]
      (pairo (lcons 'a 'b))
      (== q true))

(run* [q]
      (pairo (lcons 'a (lcons 'b 'c)))
      (== q true))

(run* [q]
      (pairo (llist 'a 'b 'c))
      (== q true))

(run* [q]
      (pairo ['a 'b 'c])
      (== q true))

;; How do we write programs that have a return value other than true/false? 
(defn append [l s]
  (cond
   (nil? (seq l)) s
   :else (conj (append (rest l) s) (first l))))

(append '(a b c) '(d e))

;; First, we need to add an out parameter
(defn my-appendo [l s out]
  ;; Change cond -> conde
  (conde
   ;; nil? -> emptyo
   [(emptyo l) (== s out)]
   [succeed (fresh [a d res]
                   ;; deconstruct resto
                   (conso a d l)
                   ;; decontruct firsto
                   (conso a res out)
                   ;; recurse with rest of l 
                   (my-appendo d s res))]))

(run* [q]
      (my-appendo '(a b c) '(d e) q))


;; We can also use built-in pattern matching
(defne my-appendo-match [x y z]
  ([() _ y])
  ([[a . d] _ [a . r]] (appendo d y r)))

(run* [q]
      (my-appendo-match '(a b c) '(d e) q))

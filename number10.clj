(ns user)

;; Write a program that outputs all possibilities to put + or - or nothing between 
;; the numbers 1,2,…,9 (in this order) such that the result is 100. 
;; For example 1 + 2 + 3 - 4 + 5 + 6 + 78 + 9 = 100.

;; in clojure:
;; => (+ (+ (+ (+ (- (+ (+ 1 2) 3) 4) 5) 6) ($ 7 8) 9)
;; 100

;; put + - or nothing ($) into the elements
;; [+ + - - $ + - +]
;; [1 2 3 4 5 6 7 8 9]
;; [1 + 2 + 3 - 4 - 56 + 7 + 8 + 9] = -44
;; (+ (+ (- (- (+ (+ 1 2) 3) 4) ($ 5 6) 7) 8) 9)

;; note that $ needs to be resolved first, "has precedence".

(def n100 [1 2 3 4 5 6 7 8 9])

(defn $
 	"merge collection of numbers into one"
	[r]
	(Integer/parseInt (apply str r))
)

(defn vec-remove
	"remove elem in coll"
	[coll pos]
	(vec (concat (subvec coll 0 pos) (subvec coll (inc pos)))))

(defn vec-melt
		"melt two elems in coll on pos and pos + 1"
		[coll pos]
		;(println "coll:" coll "pos: " pos)
		(vec (concat 
			(subvec coll 0 pos) 
			(conj [] ($ (subvec coll pos (+ pos 2)))) 
			(subvec coll (+ pos 2))))
)

;; first merge all numbers in the vec with $, then apply - and + 	
(defn multifun [numbers fns]
	;(println "multifun: " numbers fns)
	;; call all $
    (if (and (= (count numbers) 2) (= (count fns) 1)) 
		(eval (concat (take 1 fns) (take 2 numbers)))
		(multifun 
		    (conj (drop 2 numbers) (eval (concat (take 1 fns) (take 2 numbers))))
			(drop 1 fns)
		)
	)
)

(defn in? 
  "true if coll contains elm"
  [coll elm]  
  (some #(= elm %) coll))

;; (indices #(= '$ %) t)
(defn indices [pred coll]
   "find indexes of predicate in collection"
   (keep-indexed #(when (pred %2) %1) coll))

;; [1 2 3][+ $] 
;; > [1 23]

;; [1 2 3][$ $] 
;; > [123]

(defn doit [numbers functions]
	(loop [numbers numbers functions functions]
		(let [idx (first (indices #(= '$ %) functions))]
	    	;(println "n:" numbers " f:" functions)
			(if (not (in? functions '$))
				numbers ; done
				(recur (vec-melt numbers idx) (vec-remove functions idx))
			)
		)
	)
)

(defn next-level [s length]
  "create all possible combinations of +, - and $ for length number of operations"
  (if (zero? length) 
 	(if (= (multifun (doit n100 s) (filter #(not= '$ %) s)) 100)
		(println "Sequence: " (conj (into [] (interleave n100 s)) '9) " becomes" 100))
  	(do (next-level (conj s '+) (dec length))
  		(next-level (conj s '-) (dec length))
    	(next-level (conj s '$) (dec length))
	)
  )
) 


(println "Solution: " (next-level [] 8))
;;(println "Solution: " (multifun [12 3] (filter #(not= '$ %) ['$ -])))


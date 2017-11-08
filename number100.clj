(ns user)

;; Write a program that outputs all possibilities to put + or - or nothing between 
;; the numbers 1,2,…,9 (in this order) such that the result is 100. 
;; For example 1 + 2 + 3 - 4 + 5 + 6 + 78 + 9 = 100.

;; put + - or nothing ($) into the elements
;; [+ + - - $ + - +]
;; [1 + 2 + 3 - 4 - 56 + 7 + 8 + 9]

(def n100 [1 2 3 4 5 6 7 8 9])

(defn $ [a b]
	(Integer/parseInt (->> [a b] (reduce str)))
)

(defn multifun [numbers fns]
	(println "multifun: " numbers fns)
    (if (and (= (count numbers) 2) (= (count fns) 1)) 
		(eval (concat (take 1 fns) (take 2 numbers)))
		(multifun 
		    (conj (drop 2 numbers) (eval (concat (take 1 fns) (take 2 numbers))))
			(drop 1 fns)
		)
	)
)

(defn next-level [s length]
  (if (zero? length) 
 	(if (= (multifun n100 s) 100)
		(println "Sequence: " s " becomes" 100))
  	(do (next-level (conj s '+) (dec length))
  		(next-level (conj s '-) (dec length))
    	(next-level (conj s '$) (dec length))
	)
  )
) 

(println " This is :" (next-level [] 8))
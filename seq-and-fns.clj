(ns user)

;;(def numbers [1 2 3 4])
;;(def fns ['$ '$ +])

(def numbers [1 2 3 4 5 6 7 8 9])
(def fns [+ + + + + + + +])

(defn $ [a b]
	(Integer/parseInt (->> [a b] (reduce str)))
)

(defn multifun [numbers fns]
	(println numbers fns)
    (if (and (= (count numbers) 2) (= (count fns) 1)) 
		(eval (concat (take 1 fns) (take 2 numbers)))
		(multifun 
		    (conj (drop 2 numbers) (eval (concat (take 1 fns) (take 2 numbers))))
			(drop 1 fns)
		)
	)
)

(println "Outcome:" (multifun numbers fns))
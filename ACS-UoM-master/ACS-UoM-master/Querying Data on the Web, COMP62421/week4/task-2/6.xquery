(:Q6:)
(for $c in  doc('mondial.xml')//country
	let $d := sum( doc('mondial.xml')//river[tokenize(@country)=$c/@car_code]/length)
	order by $d descending
return $c/name/text()) [position() = 1 to 10]

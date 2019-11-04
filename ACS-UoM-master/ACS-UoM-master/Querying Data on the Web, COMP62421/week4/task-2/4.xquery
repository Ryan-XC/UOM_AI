(:Q4:)
for $c in  doc('mondial.xml')//country
	where count( doc('mondial.xml')//island[tokenize(@country)=$c/@car_code]) > 10
return $c/name/text()

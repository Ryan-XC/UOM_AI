(:Q9:)
for $a in doc('mondial.xml')//country, $b in doc('mondial.xml')//organization 
	where $b/members/tokenize(@country) = $a/@car_code and $b/established > '1994-12-01' and $a/religion/text() = 'Buddhist'
return concat($a/name,' ',$b/name/text())

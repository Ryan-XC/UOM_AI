(:Q5:)
for $r in doc('mondial.xml')//river
	where $r/tokenize(@country) = 'GB'
return $r/length/text()

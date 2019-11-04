(:Q8:)
for $e in  doc('mondial.xml')//city/name
	where fn:starts-with($e/text(),'Man')
return $e/text()

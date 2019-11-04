(:Q10:)
for $e in //country
return concat($e/name/text(),' ',count(//island[tokenize(@country)=$e/@car_code]))

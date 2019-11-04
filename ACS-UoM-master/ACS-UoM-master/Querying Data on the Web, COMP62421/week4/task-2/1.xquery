(:Q1:)
distinct-values(
  for $c in doc('mondial.xml')//country, 
  $s in doc('mondial.xml')//sea/tokenize(@country)
  where $c/@car_code = $s
  return $c/name/text()
)
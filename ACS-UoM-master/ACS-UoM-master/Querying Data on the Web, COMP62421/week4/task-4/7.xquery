(:Q7:)
for $c in doc('city_e.xml')//city/city-tuple
where $c/name/data()='Manchester'
return $c

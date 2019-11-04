(:Q7:)
for $c in doc('city_a.xml')//city/city-tuple
where $c/@name/data()='Manchester'
return $c

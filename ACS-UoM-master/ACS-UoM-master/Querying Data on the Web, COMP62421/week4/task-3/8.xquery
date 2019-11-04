(:Q8:)
for $c in doc('city_a.xml')//city/city-tuple
where fn:starts-with($c/@name/data(),'Man')
return $c

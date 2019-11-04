(:Q5:)
distinct-values(
	for $c in doc('geo_river_a.xml')//geo_river/geo_river-tuple, $r in doc('river_a.xml')//river/river-tuple
	where $c/@country = 'GB' and $c/@river = $r/@name
	return $r/@length/data()
)

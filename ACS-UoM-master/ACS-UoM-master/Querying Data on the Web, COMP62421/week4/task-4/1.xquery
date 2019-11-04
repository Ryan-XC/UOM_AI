(:Q1:)
distinct-values(
	for $s in doc('geo_sea_e.xml')//geo_sea, $c in doc('country_e.xml')//country
	where $s/geo_sea-tuple/country = $c/country-tuple/code
	return data($c/country-tuple/name)
)

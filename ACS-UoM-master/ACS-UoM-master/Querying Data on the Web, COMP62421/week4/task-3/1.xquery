(:Q1:)
distinct-values(
	for $s in doc('geo_sea_a.xml')//geo_sea, $c in doc('country_a.xml')//country
	where $s/geo_sea-tuple/@country = $c/country-tuple/@code
	return data($c/country-tuple/@name)
)
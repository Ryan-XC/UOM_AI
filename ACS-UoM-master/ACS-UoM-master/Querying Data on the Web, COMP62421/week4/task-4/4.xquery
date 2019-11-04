(:Q4:)
for $c in doc('country_e.xml')//country/country-tuple
where count(doc('geo_island_e.xml')//geo_island/geo_island-tuple[country = $c/code]) > 10
return $c/name/data()

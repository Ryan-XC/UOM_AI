(:Q10:)
for $b in doc('country_a.xml')//country/country-tuple
return concat($b/@name/data(),' ',count(doc('geo_island_a.xml')//geo_island/geo_island-tuple[@country = $b/@code]))

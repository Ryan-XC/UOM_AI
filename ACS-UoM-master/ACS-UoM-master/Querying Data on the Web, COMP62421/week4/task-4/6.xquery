(:Q6:)
(for $a in doc('country_e.xml')//country-tuple
	let $tl := sum(
	for $r in distinct-values(doc('geo_river_e.xml')//geo_river-tuple[country=$a/code]/river)
	let $rl := doc('river_e.xml')//river-tuple/length[data()!='MISSING']
	return $rl
)
order by $tl descending 
return $a/name/data())[position()=1 to 10]

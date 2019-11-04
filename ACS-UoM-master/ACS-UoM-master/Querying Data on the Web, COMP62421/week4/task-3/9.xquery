(:Q9:)
for $a in doc('country_a.xml')//*/country-tuple, $b in doc('organization_a.xml')//*/organization-tuple, $c in doc('ismember_a.xml')//*/ismember-tuple, $d in doc('religion_a.xml')//*/religion-tuple
where $a/@code = $c/@country and $c/@organization = $b/@abbreviation and $a/@code = $d/@country and $d/@name = 'Buddhist' and $b/@established>'1994-12-01'
return concat(data($a/@name),' ',data($b/@name))

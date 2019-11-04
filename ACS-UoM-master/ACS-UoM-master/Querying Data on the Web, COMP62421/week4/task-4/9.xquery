(:Q9:)
for $a in doc('country_e.xml')//*/country-tuple,$b in doc('organization_e.xml')//*/organization-tuple,$c in doc('ismember_e.xml')//*/ismember-tuple,$d in doc('religion_e.xml')//*/religion-tuple
where $a/code=$c/country and $c/organization=$b/abbreviation and $a/code=$d/country and $d/name='Buddhist' and $b/established>'1994-12-01'
return concat(data($a/name),' ',data($b/name))

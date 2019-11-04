declare default element namespace "http://www.cs.manchester.ac.uk/pgt/COMP60411/el";
declare namespace ssd="http://www.cs.manchester.ac.uk/pgt/COMP60411/";
(: You should define a *function* ssd:axiom, that takes a node and returns true if that node is an axiom,
e.g., an instance-of, subsumes, etc. element. :)
declare function ssd:axiom($arg as node()) as xs:boolean
{
let $n:= fn:name($arg)
return if ($n ='equivalent' or $n ='subsumes' or $n ='instance-of' or $n ='related-to')
then true()
else false()
};
let $doc := doc("el1.xml")
for $i in $doc//*
where ssd:axiom($i)
return $i


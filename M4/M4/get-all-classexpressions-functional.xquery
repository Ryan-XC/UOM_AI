declare default element namespace "http://www.cs.manchester.ac.uk/pgt/COMP60411/el";
declare namespace ssd="http://www.cs.manchester.ac.uk/pgt/COMP60411/";
(:You should define a *function* ssd:classexpression, that takes a node and returns true if that node is an class expression,
e.g., a conjunction element. :)
declare function ssd:classexpression($nd as node()) as xs:boolean
{
let $el := fn:name($nd)
return if($el="conjunction" or $el="atomic"or $el="exists")
then true()
else false()
};
for $e in doc("el1.xml")//*
where ssd:classexpression($e)
return $e
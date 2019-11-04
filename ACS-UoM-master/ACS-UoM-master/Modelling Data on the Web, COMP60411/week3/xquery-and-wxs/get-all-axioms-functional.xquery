declare default element namespace "http://www.cs.manchester.ac.uk/pgt/COMP60411/el";
declare namespace ssd="http://www.cs.manchester.ac.uk/pgt/COMP60411/";
(: You should define a *function* ssd:axiom, that takes a node and returns true if that node is an axiom,
e.g., an instance-of, subsumes, etc. element. :)


declare function ssd:classexpression($e as node()){
    let $root := doc("el1.xml")//(instance-of,subsumes,equivalent,related-to)
    return if ($e = $root)
    then $root
    else "false"
};

ssd:classexpression(doc('el1.xml')/*/instance-of[1])

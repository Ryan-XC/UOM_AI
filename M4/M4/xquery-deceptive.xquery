declare default element namespace "http://www.cs.manchester.ac.uk/pgt/COMP60411/";
declare namespace ssd="http://www.cs.manchester.ac.uk/pgt/COMP60411/";

(: Declare a function to get the prefix in the document :)
declare function ssd:getPrefixesWithURIs($inputDocumentToAnalyze as document-node()) as element()* {
    for $node in $inputDocumentToAnalyze//*
        (: Get the prefixes in the scope without 'xml' prefix and order by prefix :)
        for $prefix in fn:in-scope-prefixes($node)
            where not($prefix = "xml")
            order by $prefix     
        return 
            <ssd:report prefix="{$prefix}" uri="{fn:namespace-uri-for-prefix($prefix, $node)}"/>
};

(: Declare a function that receives and input document to analyze and look for problems :)
declare function ssd:lookForDeceptiveProblems($inputDocumentToAnalyze as document-node()) as element()*{
    let $docWithPrefixesList := ssd:getPrefixesWithURIs($inputDocumentToAnalyze)
    for $node1 in $docWithPrefixesList
        for $node2 in $docWithPrefixesList
            (: Constraint for the same prefix and different URI :)
            where ( ($node1/@prefix=$node2/@prefix) and not($node1/@uri=$node2/@uri) )     
        return               
        <ssd:problems>
            <ssd:prefix value="{$node2/@prefix}"/>
            <ssd:uris>
                <ssd:uri value="{$node1/@uri}"/>
                <ssd:uri value="{$node2/@uri}"/>
            </ssd:uris>
        </ssd:problems>
};

(: Declare function that receives a document to analyze and return a string with the analysis :)
declare function ssd:validateDeceptive($inputDocumentToAnalyze as document-node()) as xs:string {
    (: Count the elements in validateResponse :)
    let $nodesCount := count(ssd:lookForDeceptiveProblems($inputDocumentToAnalyze))
        return
            (: Validation if the response has elements with problems :)
            if ($nodesCount > 0) then
                "YES - it's Deceptive!"
            else 
                ()
};

declare variable $inputDocument as document-node() external;
ssd:validateDeceptive($inputDocument) 
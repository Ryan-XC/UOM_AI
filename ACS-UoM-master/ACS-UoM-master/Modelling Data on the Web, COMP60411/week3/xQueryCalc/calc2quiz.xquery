import schema default element namespace "" at "flashcardhtml.xsd";
import schema namespace q="http://www.cs.manchester.ac.uk/pgt/COMP60411/examples/quiz" at "quiz.xsd";

declare function local:expression($expr) {
    let $expression := 
    if(fn:string(fn:node-name($expr)) = "plus") 
    then "+" 
    else if(fn:string(fn:node-name($expr)) = "minus") 
    then "-" 
    else if (fn:string(fn:node-name($expr))="times") 
    then "*" else "/"
    return $expression
};

declare function local:iterator($expr) {
    let $expression := local:expression($expr)
    let $count := count($expr/*)
    for $y in (1 to $count) 
    return 
    if (count($expr/*[$y]/*) = 0 )
    then if ($y = 1) then concat("(",number($expr/*[$y])) else concat($expression," ",fn:number($expr/*[$y]),")")
    else if ($y = 1) then local:iterator($expr/*[$y]) else if ($y = $count) then 
    insert-before(insert-before(local:iterator($expr/*[$y]),1,$expression),count(insert-before(local:iterator($expr/*[$y]),1,$expression))+1,")") 
    else insert-before(local:iterator($expr/*[$y]),1,$expression)
};

declare function local:expr2humanReadable($expr) {
    let $expression := local:expression($expr/*)
    let $count := count($expr/*/*)
    for $y in (1 to $count) 
    return 
    if (count($expr/*/*[$y]/*) = 0 )
    then if ($y = 1) then number($expr/*/*[$y]) else concat($expression," ",fn:number($expr/*/*[$y]))
    else if ($y = 1) then local:iterator($expr/*/*[$y]) else insert-before(local:iterator($expr/*/*[$y]),0,$expression)
};

declare function local:answerFor($expr) {
    let $count := count($expr/*/*)  
    return if (count($expr/*/*[1]/*) = 0 )
    then local:calculate(number($expr/*/*[1]),local:getResult($expr/*,2),$expr/*)
    else local:calculate(local:getResult($expr/*/*[1],1),local:getResult($expr/*,2),$expr/*)
};

declare function local:getResult($expr, $index) {
    let $count := count($expr/*)  
    return if (count($expr/*[$index]/*) = 0 )
    then if ($count = $index ) then number($expr/*[$index]) 
    else local:calculate(number($expr/*[$index]),local:getResult($expr,$index+1),$expr)
    else if ($count = $index ) then local:getResult($expr/*[$index],1)
    else local:calculate(local:getResult($expr/*[$index],1),local:getResult($expr,$index+1),$expr)
};

declare function local:calculate($value1, $value2, $expr) {
    let $result := 
    if(fn:string(fn:node-name($expr)) = "plus") 
    then $value1 + $value2 
    else if(fn:string(fn:node-name($expr)) = "minus") 
    then $value1 - $value2 
    else if (fn:string(fn:node-name($expr))="times") 
    then $value1 * $value2 else $value1 div $value2
    return $result
};

validate{<html>
    <head>
        <title>{/*/q:title/text()}</title>
        <script type="text/javascript" src="miniformvalidator.js"/>
    </head>
    <body>
        <h1>{/*/q:title/text()}</h1>
        <form>
            <ol>{let $exprs := /*/q:expr, 
                     $count := count($exprs)
                 for $i in (1 to $count)
                 let $expr := $exprs[$i]
                 let $id := concat("q", $i)
                 return <li>{local:expr2humanReadable($expr)}=
                <input type="text" id="{$id}" data-answer="{local:answerFor($expr)}" size="8" /><span><input
                        type="button" onclick="check(document.getElementById('{$id}'))"
                        value="Check answer" /></span>
                </li>}
            </ol>
        </form>

    </body>
</html>}
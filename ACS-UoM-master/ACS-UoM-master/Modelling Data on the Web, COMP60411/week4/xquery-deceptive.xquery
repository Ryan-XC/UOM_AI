declare namespace ns = 'http://ex.org/';

declare function ns:nsBindingsForNode($node)
{
   for $n in $node
   for $pre in in-scope-prefixes($n)
   for $ns in namespace-uri-for-prefix($pre, $n)
   order by $ns ascending
   return <nsb pre="{$pre}" ns="{$ns}"/>
};

declare function ns:multiPrefixedNs($bindings)
{
    for $b1 in $bindings
    for $b2 in $bindings
    where ($b1/@pre = $b2/@pre) and not($b1/@ns = $b2/@ns)
    return <multi>{$b1}{$b2}</multi>
};

declare function ns:isDeceptive($root)
{
    for $m in ns:multiPrefixedNs(ns:nsBindingsForNode($root))
    return "YES - it's deceptive!"
};

distinct-values(ns:isDeceptive(//*))
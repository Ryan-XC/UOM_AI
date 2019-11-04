<?xml version="1.0" encoding="UTF-8"?>
<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2"
    xmlns:sqf="http://www.schematron-quickfix.com/validator/process">
    <sch:pattern >
        <sch:rule context="family">
            <!-- Q1  -->
            <sch:let name="lName" value="@name" />
            <sch:assert test="$lName = //MainContact/LastName">
                The name of family should coincide with its last name
            </sch:assert>
            <!-- Q2  -->
            <sch:let name="family" value="@name"/>
            <sch:assert test="count(//family[@name = $family]) = 1">
                No two families share the same name
            </sch:assert>
        </sch:rule>
    </sch:pattern>
    
    <sch:pattern>
        <sch:rule context="house">
            <!-- Q3  -->
            <sch:assert test="count(//house[@castle = 'true']) = 1">
                There should be exactly one house that is a castle
            </sch:assert>
            <!-- Q4  -->
            <sch:let name="house" value="@name"/>
            <sch:assert test="count(//period[@house = $house]) >= 1">
                Every house should occur in some occupancy
            </sch:assert>
        </sch:rule>
    </sch:pattern>
    
    <sch:pattern >
        <sch:rule context="family">
        <!-- Q4  -->
        <sch:let name="lName" value="@name"/>
            <sch:assert test="count(//period[@family = $lName]) >= 1">
                Every family should occur in some occupancy
            </sch:assert>
        </sch:rule>
    </sch:pattern>
    
    <sch:pattern>
        <sch:rule context="period">
            <!-- Q5  -->
            <sch:let name="family" value="@family"/>
            <sch:assert test="count(//family[@name = $family]) >= 1">
                Every family that occurs in an occupancy also occurs in one of the families
            </sch:assert>
            <!-- Q5  -->
            <sch:let name="house" value="@house"/>
            <sch:assert test="count(//house[@name = $house]) >= 1">
                Every house that occurs in an occupancy also occurs in one of the houses
            </sch:assert>
            <!-- Q6  -->
            <sch:let name="time" value="@time"/>
            <sch:let name="house" value="@house"/>
            <sch:assert test="count(//period[@time = $time and @house = $house])&lt; 2">
                For every period, each house is occupied by at most one family
            </sch:assert>
        </sch:rule>
    </sch:pattern>
    
    <sch:pattern >
        <sch:rule context="family">
        <!-- Q7 -->
        <sch:let name="family" value="@name"/>
            <sch:assert test="count(distinct-values(//period[@family = $family]/@time)) >= count(distinct-values(//period/@time))">
                For each period, each family occupies some house
            </sch:assert>
        </sch:rule>
    </sch:pattern>
</sch:schema>
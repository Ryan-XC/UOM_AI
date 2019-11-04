-- task3.sql

-- set echoing on

.echo on

-- set spooling out: note the suffix

.output task3.log

--

.header on

.mode column

select name,population/2 from Country order by population DESC limit 10;

select name as Name, country as Country, province as Province, population as Population, elevation as Elevation, latitude as Latitude, longitude as Longitude from City c  where c.name = 'Manchester';

select name from City where name like 'Man%';

select distinct c.name,o.name from isMember n,Country c,Religion r,Organization o where n.organization in (select abbreviation from Organization where established > '1994-12-01') and n.country = c.code and r.name = 'Buddhist' and c.code = r.country and o.abbreviation = n.organization;

select c.name, ifnull(t.times, 0) from Country c left outer join (select i.country,count(*) as times from geo_island i group by i.country) t on t.country = c.code;


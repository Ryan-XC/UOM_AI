-- week4.sql

-- set echoing on
.header on
.echo on 
.mode 
-- set spooling out: note the suffix

.output week4.log

--

.timer on
--Q1
select c.name from country c,geo_sea s where c.code = s.country;
--Q2
select name from lake union select name from river union select name from sea;
--Q3
select avg(length) from river;
--Q4
select distinct c.name from country c,geo_island i where c.code = i.country group by c.code having count(distinct(island)) > 10;
--Q5
select r.length from geo_river gr,river r where gr.country='GB' and gr.river = r.name;
--Q6
select c.name,sum(length) from country c,(select distinct gr.river, gr.country,length from geo_river gr, river r where gr.river = r.name) as cl where cl.country = c.code group by code order by sum(length) desc limit 10;
--Q7
select name as Name, country as Country, province as Province, population as Population, elevation as Elevation, latitude as Latitude, longitude as Longitude from City c  where c.name = 'Manchester';
--Q8
select name from City where name like 'Man%';
--Q9
select c.name,o.name from isMember n,Country c,Religion r,Organization o where n.organization in (select abbreviation from Organization where established > '1994-12-01') and n.country = c.code and r.name = 'Buddhist' and c.code = r.country and o.abbreviation = n.organization;
--Q10
select c.name, ifnull(t.times, 0) from Country c left outer join (select i.country,count(*) as times from geo_island i group by i.country) t on t.country = c.code;


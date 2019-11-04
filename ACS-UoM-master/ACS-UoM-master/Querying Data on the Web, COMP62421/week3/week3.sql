.header on
.mode column
.echo on
.timer on
.output week3.log
--Bar, Beer, Drinker, Frequents, Likes, Serves 
--Q1
select name from Drinker where name = "Eve";
--Q2
select name from Drinker;
--Q3
select price from Beer cross join Serves where name = beer;
--Q4
select distinct price from Beer cross join Serves;
--Q5
select price from Beer b, Serves s where b.name = s.beer;
--Q6
select beer from Likes union select name from Beer;
--Q7
select beer from Likes intersect select name from Beer;
--Q8
select count(*) from Likes;

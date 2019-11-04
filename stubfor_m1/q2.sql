/* Q2 is an "export" script which returns all the data in the database as a CSV file which should look like `People.csv`. Indeed, for the very same data it should return a table that is identical to `People.csv`. */
.bail off
.echo OFF
.separator ","
.headers on
.output ./test.csv
select * 
from (People INNER join Phone on People.ID=Phone.ID)
LEFT JOIN Email on Email.ID=Phone.ID;
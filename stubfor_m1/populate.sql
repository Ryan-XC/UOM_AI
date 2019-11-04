/* Once you have your SQL Schema, you need to write **SQL statements which pull data 
from `Import`** and push it into your schema's tables. After running this script we 
should be able to delete `Impor`t with no loss of information.

This will likely be a *multistatement* SQL document with each statement terminated 
with a semicolon.

They will typically be of the form:
	insert into TABLENAME select ... from Import ...
This will put the results of your query into the specified table. */
INSERT INTO People
(ID,first_name,last_name,address,city,county,postal)
SELECT ID,first_name,last_name,address,city,county,postal
FROM Import;

INSERT INTO Phone
(ID,phone_number)
SELECT ID,phone1
FROM Import;

INSERT INTO Phone
(ID,phone_number)
SELECT ID,phone2
FROM Import;

INSERT INTO Email
(ID,email)
SELECT ID,email
FROM Import;
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
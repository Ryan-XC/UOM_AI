-- CREATE DATABASE CALLED DATABASE
-- DATABASE.DB

-- CREATE TABLE CALLED PHONE
CREATE TABLE `PHONE` (
	`PhoneID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`PhoneNumber`	INTEGER
)

-- CREATE TABLE CALLED EMAIL
CREATE TABLE `EMAIL` (
	`EmailID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`emailAddress`	VARCHAR(50)
)
-- CREATE TABLE CALLED PEOPLE
CREATE TABLE `PEOPLE` (
	`Pid`	INTEGER NOT NULL,
	`first_name`	VARCHAR(50),
	`last_name`	VARCHAR(50),
	`company_name`	VARCHAR(50),
	`address`	VARCHAR(50),
	`city`	VARCHAR(50),
	`country`	VARCHAR(50),
	`postal`	VARCHAR(50),
	`emailID`	INTEGER NOT NULL,
	`phoneID`	INTEGER NOT NULL,
	PRIMARY KEY(Pid),
	FOREIGN KEY (emailID) REFERENCES EMAIL(EmailID),
	FOREIGN KEY (phoneID) REFERENCES PHONE(PhoneID)
)

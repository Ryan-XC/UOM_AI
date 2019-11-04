CREATE TABLE People (
    ID SMALLINT UNSIGNED NOT NULL,
    first_name varchar(255),
    last_name varchar(255) NOT NULL,
    address varchar(255) NOT NULL,
    city varchar(255) NOT NULL,
    county varchar(255) NOT NULL,
    postal varchar(255) NOT NULL,
    CONSTRAINT pk_person PRIMARY KEY (ID)
	);

 CREATE TABLE Phone (
     ID varchar(255) NOT NULL,
     phone_number varchar (255) NOT NULL,
     CONSTRAINT pk_phone_number PRIMARY KEY (phone_number),
	 REFERENCES People(ID)
	 );

CREATE TABLE Email (
     ID varchar(255) NOT NULL,
     email varchar (255) NOT NULL,
     CONSTRAINT pk_phone_number PRIMARY KEY (email),
	 CONSTRAINT fk_ID FOREIGN KEY (ID)
	 REFERENCES People(ID)
	 );
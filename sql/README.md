# Introduction

# SQL Quries

###### Table Setup (DDL)

CREATE TABLE IF NOT EXISTS cd.members (
  memid SERIAL NOT NULL,
  surname VARCHAR(200) NOT NULL,
  firstname VARCHAR(200) NOT NULL,
  address VARCHAR(300) NOT NULL,
  zipcode INTEGER NOT NULL,
  telephone VARCHAR(20) NOT NULL,
  recommendedby INTEGER NULL,
  joindate TIMESTAMP NOT NULL,
  CONSTRAINT members_pk PRIMARY KEY (memid),
  CONSTRAINT members_recommendedby_fk FOREIGN KEY (recommendedby) REFERENCES cd.members(memid)
);
CREATE TABLE IF NOT EXISTS cd.facilities (
  facid SERIAL NOT NULL,
  name VARCHAR(100) NOT NULL,
  membercost NUMERIC NOT NULL,
  guestcost NUMERIC NOT NULL,
  initialoutlay NUMERIC NOT NULL,
  monthlymaintenance NUMERIC NOT NULL,
  CONSTRAINT facilities_pk PRIMARY KEY (facid)
);
CREATE TABLE IF NOT EXISTS cd.bookings (
  bookid SERIAL NOT NULL,
  facid SERIAL NOT NULL,
  memid SERIAL NOT NULL,
  starttime TIMESTAMP NOT NULL,
  slots INTEGER NOT NULL,
  CONSTRAINT bookings_id PRIMARY KEY (bookid),
  CONSTRAINT bookings_facid_fk FOREIGN KEY (facid) REFERENCES cd.facilities(facid),
  CONSTRAINT bookings_memid_fk FOREIGN KEY (memid) REFERENCES cd.members (memid)
);

###### Question 1: Show all members



###### Questions 2: Lorem ipsum...

###### Question 1: Show all members

```sql
SELECT *
FROM cd.members
```

###### Questions 2: Lorem ipsum...

```sql
SELECT blah blah
```

-- Modifying Data:

INSERT INTO cd.facilities
(facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
VALUES (9, 'Spa', 20, 30, 100000, 800);

-- INSERT INTO cd.facilities
-- SELECT (SELECT max(facid) FROM cd.facilities) + 1, 'Spa', 20, 30, 100000, 800;

UPDATE cd.facilities
SET initialoutlay = 10000
WHERE facid = 1;

UPDATE cd.facilities
SET membercost = membercost + membercost * .1,
    guestcost = guestcost + guestcost * .1
WHERE facid = 1;

DELETE FROM cd.bookings;

DELETE FROM cd.members
WHERE memid = 37;

-- Basics:

SELECT facid, name, membercost, monthlymaintenance FROM cd.facilities
WHERE facilities.membercost < facilities.monthlymaintenance / 50
AND facilities.membercost > 0;

SELECT * FROM cd.facilities
WHERE name LIKE '%Tennis%';

SELECT * FROM cd.facilities
WHERE facid IN (1, 5);

SELECT memid, surname, firstname, joindate FROM cd.members
WHERE joindate > '2012-09-01' :: DATE;

SELECT starttime FROM cd.bookings b JOIN cd.members m
ON b.memid = m.memid
WHERE firstname = 'David' AND surname = 'Farrell';

SELECT recommendedby, COUNT(recommendedby) AS count FROM cd.members
WHERE recommendedby != 0
GROUP BY recommendedby
ORDER BY recommendedby ASC;

-- Join:

SELECT starttime FROM cd.bookings b JOIN cd.members m
ON b.memid = m.memid
WHERE firstname = 'David' AND surname = 'Farrell';

-- SELECT starttime, name FROM cd.bookings b JOIN cd.facilities f
-- ON b.facid = f.facid
-- WHERE name LIKE 'Tennis Court%'
-- AND starttime >= '2012-09-21' AND starttime < '2012-09-22'
-- ORDER BY starttime ASC;

SELECT m.firstname AS memfname, m.surname AS memsname, f.firstname AS recfname, f.surname AS recsname
FROM cd.members m LEFT JOIN cd.members f
ON m.recommendedby = f.memid
ORDER BY memsname, memfname ASC;

SELECT DISTINCT f.firstname, f.surname
FROM cd.members m LEFT JOIN cd.members f
ON f.memid = m.recommendedby
WHERE f.memid != 0
ORDER BY f.surname, f.firstname ASC;

-- SELECT DISTINCT m.firstname || ' ' || m.surname AS member,
--   (SELECT r.firstname || ' ' || r.surname AS recommender
-- 	  FROM cd.members r
-- 	  WHERE r.memid = m.recommendedby
--   )
-- FROM cd.members m
-- ORDER BY member ASC;

-- Aggregation:

SELECT recommendedby, COUNT(recommendedby) AS count FROM cd.members
WHERE recommendedby != 0
GROUP BY recommendedby
ORDER BY recommendedby ASC;

SELECT facid, SUM(slots) AS TotalSlots FROM cd.bookings
GROUP BY facid
ORDER BY facid;

SELECT facid, SUM(slots) as totalslots FROM cd.bookings
WHERE starttime >= '2012-09-01' AND starttime <= '2012-10-30'
GROUP BY facid
ORDER BY totalslots;

SELECT facid, EXTRACT(MONTH FROM starttime) AS month, SUM(slots) AS totalslots
FROM cd.bookings
WHERE starttime >= '2012-01-01' AND starttime < '2013-01-01'
GROUP BY facid, month
ORDER BY facid, month;

SELECT COUNT(DISTINCT memid) AS count FROM cd.bookings;

SELECT surname, firstname, b.memid, MIN(starttime)
FROM cd.members m RIGHT JOIN cd.bookings b
ON m.memid = b.memid
WHERE starttime >= '2012-09-01'
-- GROUP BY surname, firstname, b.memid
ORDER BY b.memid;

-- SELECT COUNT(memid) OVER(), firstname, surname FROM cd.members
-- ORDER BY joindate;

SELECT ROW_NUMBER () OVER (), firstname, surname FROM cd.members
ORDER BY joindate;

-- select facid, total from (
-- 	select facid, total, rank() over (order by total desc) rank from (
-- 		select facid, sum(slots) total
-- 			from cd.bookings
-- 			group by facid
-- 		) as sumslots
-- 	) as ranked
-- where rank = 1

-- Strings:

SELECT surname || ', ' || firstname AS name FROM cd.members;

SELECT memid, telephone FROM cd.members
WHERE telephone LIKE '(%)%';

SELECT SUBSTRING(surname, 1, 1) AS letter, COUNT(surname) FROM cd.members
GROUP BY letter
ORDER BY letter;

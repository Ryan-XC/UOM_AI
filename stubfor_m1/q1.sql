/* Q1 should return a list of all people who live in a large household, that is, one with more than 3 people living in it. */
SELECT first_name,last_name 
FROM People 
WHERE postal 
IN (SELECT postal FROM People GROUP BY postal HAVING COUNT(*)>3  ) 
ORDER BY first_name
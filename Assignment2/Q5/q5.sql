/* This statement will get the first 10 (to keep lines below 50) page records where the page is viewable by user_id = 3 or has is_public set to true*/
SELECT * FROM page WHERE page_id IN (SELECT page_id FROM viewable_by WHERE user_id=3) UNION SELECT * FROM page WHERE is_public=1 FETCH FIRST 10 ROWS ONLY;
/* This statement will get all the alert records that a user with user_id = 3 and is not yet dismissed has*/
SELECT * FROM alert WHERE message_id IN (SELECT message_id FROM notifies WHERE user_id=3 AND is_dismissed=0) FETCH FIRST 10 ROWS ONLY;
/* This statement will get all the message records that are not alerts*/
SELECT * FROM message WHERE message_id NOT IN (SELECT message_id FROM alert) FETCH FIRST 10 ROWS ONLY;
/* This statement will get all the name columns of all the user records that have either written a comment or own a page*/
SELECT name FROM user u WHERE EXISTS (SELECT * FROM comment WHERE owner_id=u.user_id) OR EXISTS (SELECT * FROM page WHERE owner_id=u.user_id) FETCH FIRST 10 ROWS ONLY;
/* This statement will get all the user name and page title columns of all the users that match a page owner*/
SELECT u.name,p.title FROM user u,page p WHERE u.user_id=p.owner_id FETCH FIRST 10 ROWS ONLY;


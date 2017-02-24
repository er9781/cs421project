connect to cs421;
SELECT * FROM page WHERE page_id IN (SELECT page_id FROM viewable_by WHERE user_id=3) UNION SELECT * FROM page WHERE is_public=1 FETCH FIRST 10 ROWS ONLY;
SELECT * FROM alert WHERE message_id IN (SELECT message_id FROM notifies WHERE user_id=3 AND is_dismissed=0) FETCH FIRST 10 ROWS ONLY;
SELECT * FROM message WHERE message_id NOT IN (SELECT message_id FROM alert) FETCH FIRST 10 ROWS ONLY;
SELECT name FROM user u WHERE EXISTS (SELECT * FROM comment WHERE owner_id=u.user_id) OR EXISTS (SELECT * FROM page WHERE owner_id=u.user_id) FETCH FIRST 10 ROWS ONLY;
SELECT u.name,p.title FROM user u,page p WHERE u.user_id=p.owner_id FETCH FIRST 10 ROWS ONLY;

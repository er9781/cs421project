/* This statement updates the is_dismissed attribute in all the notifies records that are alerts and have type info*/
UPDATE notifies SET is_dismissed=1 WHERE message_id IN (SELECT message_id FROM alert WHERE type='info');
/* This statement will delete all the content records that are on a page that is public and has owner_id = 3*/
DELETE FROM content WHERE page_id IN (SELECT page_id FROM page Where is_public = 1 AND owner_id = 3);
/* This statement updates the config_id attribute in all the page_menu records that have page_id associated with owner_id = 1*/
UPDATE page_menu SET config_id=1 WHERE page_id IN (SELECT page_id FROM page WHERE owner_id=1);
/* This statement removes all text from a comment record that has page_id greater than 3 but smaller than 6*/
UPDATE comment SET text = NULL WHERE page_id > 3 AND page_id < 6;
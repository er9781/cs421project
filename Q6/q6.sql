connect to cs421;
UPDATE notifies SET is_dismissed=1 WHERE message_id IN (SELECT message_id FROM alert WHERE type='info');
DELETE FROM content WHERE page_id=1;
UPDATE page_menu SET config_id=1 WHERE page_id IN (SELECT page_id FROM page WHERE owner_id=1);
DELETE FROM receives WHERE user_id=1;
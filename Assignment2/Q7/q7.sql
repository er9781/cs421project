/* AllCommentsWrittenByGalen returns a view of pages and their contents along with galen's comment on said page*/
CREATE VIEW AllCommentsWrittenByGalen as select USER.USERNAME, COMMENT.PAGE_ID, TYPE, CONTENT, COMMENT.COMMENT_ID, COMMENT.TEXT from COMMENT,USER,CONTENT WHERE COMMENT.OWNER_ID = USER.USER_ID AND USERNAME = 'galen' AND COMMENT.PAGE_ID = CONTENT.PAGE_ID;

SELECT USERNAME, COMMENT_ID from AllCommentsWrittenByGalen;

/* should not work */
UPDATE AllCommentsWrittenByGalen SET USERNAME = 'mario';

/* MessagesRecievedByGalen returns a view of messages that were received by username Galen */
CREATE VIEW MessagesReceivedByGalen
as select USER.USERNAME, USER.NAME, MESSAGE.MESSAGE_ID, MESSAGE.TEXT from USER, RECEIVES, MESSAGE
WHERE RECEIVES.MESSAGE_ID = MESSAGE.MESSAGE_ID AND RECEIVES.USER_ID = USER.USER_ID AND USER.USERNAME = 'galen';

SELECT NAME, MESSAGE_ID  FROM MessagesReceivedByGalen;

/* should not work */
UPDATE MessagesReceivedByGalen SET USERNAME = 'mario';

--This trigger deletes the alert when it is dismissed
CREATE TRIGGER ONDISMISSALERT
AFTER UPDATE OF is_dismissed ON notifies
REFERENCING NEW AS NEW_NOTIF
FOR EACH ROW MODE DB2SQL
BEGIN ATOMIC
IF (NEW_NOTIF.is_dismissed = 1) THEN
	DELETE FROM alert WHERE alert.message_id = NEW_NOTIF.message_id;
END IF;
END
@

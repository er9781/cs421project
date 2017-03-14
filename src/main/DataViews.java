package main;

public class DataViews {

	/**
	 * Get ids and names of all admins
	 * @param prefix
	 * @return
	 */
	public static String getAdmins(String prefix){
		return "SELECT user_id, name FROM " + prefix + "user WHERE role='admin'";
	}
	
	/**
	 * Get all columns for pages viewable by a given user. This means pages they have been set to be
	 * able to view and also all public pages.
	 * @param prefix
	 * @param userId
	 * @return
	 */
	public static String getPagesViewable(String prefix, int userId){
		return "SELECT * FROM " + prefix + "page WHERE page_id IN "
				+ "(SELECT page_id FROM " + prefix + "viewable_by WHERE user_id=" + userId + ") "
				+ "UNION SELECT * FROM " + prefix + "page WHERE is_public=1 FETCH FIRST 10 ROWS ONLY";
	}
	
	/**
	 * Get all columns for alerts for a given user which have not been dismissed yet.
	 * @param prefix
	 * @param userId
	 * @return
	 */
	public static String getNonDismissedAlerts(String prefix, int userId){
		return "SELECT * FROM " + prefix + "alert WHERE message_id IN "
				+ "(SELECT message_id FROM " + prefix + "notifies WHERE user_id=" + userId + " AND is_dismissed=0) FETCH FIRST 10 ROWS ONLY";
	}

	/**
	 * Get a list of all unassigned menu items (not attached to any page).
	 * @param prefix
	 * @return
	 */
	public static String getUnassignedMenuItemIds(String prefix){
		return "SELECT menu_item_id FROM " + prefix + "menu_item WHERE menu_item_id NOT IN "
				+ "(SELECT menu_item_id FROM " + prefix + "page_menu)";
	}
	
	/**
	 * Return all content items for a given pageId
	 * @param prefix
	 * @param pageId
	 * @return
	 */
	public static String getPageContent(String prefix, int pageId){
		return "SELECT * FROM " + prefix + "content WHERE page_id="+pageId;
	}
	
	/**
	 * Get all messages for a given user.
	 * @param prefix
	 * @param userId
	 * @return
	 */
	public static String getMessages(String prefix, int userId){
		//don't need to check that the message is not an alert, since that uses notifies and not receives.
		return "SELECT * FROM " + prefix + "message WHERE message_id IN "
				+ "(SELECT message_id FROM recieves WHERE user_id=" + userId + ")";
	}
	
	/**
	 * Get all messages. This must check that the message is not an alert since that will be listed separately.
	 * @param prefix
	 * @return
	 */
	public static String getMessages(String prefix){
		return "SELECT * FROM " + prefix + "message WHERE message_id NOT IN "
				+ "(SELECT message_id FROM " + prefix + "alert) FETCH FIRST 10 ROWS ONLY";
	}
	
	/**
	 * Get all active users in the platform.
	 * An active user is defined as a user having at least one comment or page belonging to him.
	 * @param prefix
	 * @return
	 */
	public static String getActiveUserNames(String prefix){
		return "SELECT name FROM " + prefix + "user u WHERE "
				+ "EXISTS (SELECT * FROM " + prefix + "comment WHERE owner_id=u.user_id) OR "
				+ "EXISTS (SELECT * FROM " + prefix + "page WHERE owner_id=u.user_id) FETCH FIRST 10 ROWS ONLY";
		//alternative. which is better?
//		return "SELECT name FROM " + prefix + "user WHERE IN "
//				+ "(SELECT owner_id FROM " + prefix + "comment UNION SELECT owner_id FROM " + prefix + "page)";
	}
	
	/**
	 * Get a list of all page titles and the names of their authors (owners)
	 * @param prefix
	 * @return
	 */
	public static String getPageAuthors(String prefix){
		return "SELECT u.name,p.title FROM " + prefix + "user u," + prefix + "page p WHERE u.user_id=p.owner_id FETCH FIRST 10 ROWS ONLY";
	}
	
}

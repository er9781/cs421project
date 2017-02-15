package main;

public class DataViews {

	public static String getAdmins(String prefix){
		return "SELECT * FROM user WHERE role='admin'";
	}
	
	public static String getPagesViewable(String prefix, int userId){
		return "SELECT * FROM " + prefix + "page WHERE page_id IN "
				+ "(SELECT page_id FROM " + prefix + "viewable_by WHERE user_id=" + userId + ")";
	}
	
	public static String getNonDismissedAlerts(String prefix, int userId){
		return "SELECT * FROM " + prefix + "alert WHERE alert_id IN "
				+ "(SELECT alert_id FROM " + prefix + "notifies WHERE user_id=" + userId + " AND is_dismissed=0)";
	}
	
	public static String getUnassignedMenuItemIds(String prefix){
		return "SELECT menu_item_id FROM " + prefix + "menu_item WHERE menu_item_id NOT IN "
				+ "(SELECT menu_item_id FROM " + prefix + "page_menu)";
	}
	
	public static String getPageContent(String prefix, int pageId){
		return "SELECT * FROM " + prefix + "content WHERE page_id="+pageId;
	}
	
	public static String getMessages(String prefix, int userId){
		//don't need to check that the message is not an alert, since that uses notifies and not receives.
		return "SELECT * FROM " + prefix + "message WHERE message_id IN "
				+ "(SELECT message_id FROM recieves WHERE user_id=" + userId + ")";
	}
	
	public static String getMessages(String prefix){
		return "SELCT * FROM " + prefix + "message WHERE message_id NOT IN "
				+ "(SELECT message_id FROM " + prefix + "alert)";
	}
	
	/**
	 * An active user is defined as a user having at least one comment or page belonging to him.
	 * @param prefix
	 * @return
	 */
	public static String getActiveUserNames(String prefix){
		return "SELECT name FROM " + prefix + "user u WHERE "
				+ "EXISTS (SELECT * FROM " + prefix + "comment WHERE owner_id=u.user_id) OR "
				+ "EXISTS (SELECT * FROM " + prefix + "page WHERE owner_id=u.user_id)";
		//alternative. which is better?
//		return "SELECT name FROM " + prefix + "user WHERE IN "
//				+ "(SELECT owner_id FROM " + prefix + "comment UNION SELECT owner_id FROM " + prefix + "page)";
	}
	
	public static String getPageAuthors(String prefix){
		return "SELECT u.name,p.title FROM " + prefix + "user u," + prefix + "page p WHERE u.user_id=p.owner_id";
	}
	
}

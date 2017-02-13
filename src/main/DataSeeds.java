package main;

public class DataSeeds {

	public static int nUsers = 5;// >= admins.length
	public static int[] admins = {1, 2, 3, 4};//must match ids of admin users
	public static int nAlerts = 20;
	public static int nMessages = 100;
	public static int nPages = 10;
	public static int nComments = 40;
	public static int nMenuItems = 10;
	public static int nMenuItemConfigurations = 5;
	public static int nContents = 3 * nPages;//avg 3 per page
	
	public static String[] getUserSeeds(String prefix) {
		String[] vals = {
				"(DEFAULT, 'simon', 'Simon Labute', 'simon', 'admin')",
				"(DEFAULT, 'galen', 'Galen Bryant', 'galen', 'admin')",
				"(DEFAULT, 'mario', 'Mario George', 'mario', 'admin')",
				"(DEFAULT, 'audran', 'Audran Semler', 'audran', 'admin')",
				"(DEFAULT, 'cool', 'Cool Person', 'cool', 'user')",
		};
		
		String[] queries = new String[vals.length + 1];
		for(int i = 0; i < queries.length - 1; i++){
			queries[i] = "INSERT INTO " + prefix + "user VALUES " + vals[i];
		}
		queries[queries.length - 1] = "SELECT * FROM " + prefix + "user";

		return queries;
	}
	
	public static String[] getUserSeeds(){
		return getUserSeeds("");
	}
	
	public static String[] getMessageSeeds(String prefix){
		
		String[] queries = new String[nAlerts + nMessages];
		
		for(int i = 0; i < nAlerts; i++){
			int owner = (int) (Math.random() * admins.length);//choose one of the admins since this is an alert
			queries[i] = "INSERT INTO " + prefix + "message VALUES (DEFAULT, "
					+ "'Alert content " + i + "', " + admins[owner] + ")";
		}
		
		for(int i = nAlerts; i < nAlerts + nMessages; i++){
			int ownerId = (int) (Math.random() * nUsers + 1);//choose any of the users
			queries[i] = "INSERT INTO " + prefix + "message VALUES (DEFAULT, "
					+ "'Message content " + i + "', " + ownerId + ")";
		}
		
		return queries;
	}
	
	public static String[] getMessageSeeds(){
		return getMessageSeeds("");
	}
	
	public static String[] getAlertSeeds(String prefix){
		//messages table has been seeded by messagesSeeder for the alerts.
		String[] types = {"success", "info", "warning", "danger"};
		
		String[] queries = new String[nAlerts];
		
		for(int i = 0; i < nAlerts; i++){
			int type = (int) (Math.random() * types.length);
			queries[i] = "INSERT INTO " + prefix + "alert VALUES (" + (i + 1) + ", '" + types[type] + "')";
		}
		
		return queries;
	}
	
	public static String[] getAlertSeeds(){
		return getAlertSeeds("");
	}
	
	public static String[] getPageSeeds(String prefix){
		String[] queries = new String[nPages];
		
		for(int i = 0; i < nPages; i++){
			int ownerId = (int) (Math.random() * nUsers + 1);
			int isPublic = (int) (Math.random() * 2);//random boolean for db2
			String title = "Page " + (i + 1);
			queries[i] = "INSERT INTO " + prefix + "page VALUES (DEFAULT, '" + title + "', " + isPublic + ", " + ownerId + ")";
		}
		
		return queries;
	}
	
	public static String[] getPageSeeds(){
		return getPageSeeds("");
	}
	
	public static String[] getCommentSeeds(String prefix){
		String[] queries = new String[nComments];
		
		for(int i = 0; i < nComments; i++){
			int ownerId = (int) (Math.random() * nUsers + 1);
			int pageId = (int) (Math.random() * nPages + 1);
			String text = "Comment " + (i + 1);
			queries[i] = "INSERT INTO " + prefix + "comment VALUES (DEFAULT, '" + text + "', " + pageId + ", " + ownerId + ")";
		}
		
		return queries;
	}
	
	public static String[] getCommentSeeds(){
		return getCommentSeeds("");
	}
	
	public static String[] getMenuItemSeeds(String prefix){
		String[] queries = new String[nMenuItems];
		
		for(int i = 0; i < nMenuItems; i++){
			String text = "Link " + (i + 1);
			String url = "link" + (i + 1);
			queries[i] = "INSERT INTO " + prefix + "menu_item VALUES (DEFAULT, '" + text + "', '" + url + "')";
		}
		
		return queries;
	}
	
	public static String[] getMenuItemSeeds(){
		return getMenuItemSeeds("");
	}

	public static String[] getMenuItemConfigurationSeeds(String prefix){
		String[] queries = new String[nMenuItemConfigurations];
		
		for(int i = 0; i < nMenuItemConfigurations; i++){
			int padding = (int) (Math.random() * 100);
			String bg = randomColour();
			String tc = randomColour();
			queries[i] = "INSERT INTO " + prefix + "menu_item_configuration VALUES (DEFAULT, " + padding + ", '" + bg + "', '" + tc + "')";
		}
		
		return queries;
	}
	
	public static String randomColour(){
		int r = (int) (Math.random() * 256);
		int g = (int) (Math.random() * 256);
		int b = (int) (Math.random() * 256);
		return "rbg(" + r + ", " + g + ", " + b + ")";
	}
	
	public static String[] getMenuItemConfigurationSeeds(){
		return getMenuItemConfigurationSeeds("");
	}
	
	public static String[] getContentSeeds(String prefix){
		String[] queries = new String[nContents];
		String[] stylings = {"airy", "compact"};
		String[] types = {"text", "img"};
		
		//index just use i, this will satisfy constraint of pages having unique indexes.
		int imgCounter = 1;
		for(int i = 0; i < nContents; i++){
			int pageId = (int) (Math.random() * nPages + 1);
			int index = (i + 1);
			int style = (int) (Math.random() * stylings.length);
			int type = (int) (Math.random() * stylings.length);
			String content;
			if(type == 0){
				content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
			}else{
				content = "images/img" + imgCounter + ".jpg";
				imgCounter++;
			}
			
			//build query
			queries[i] = "INSERT INTO " + prefix + "content VALUES (" + pageId + ", " + index + ", '" + stylings[style] + "', '" + types[type] + "', '"+content+"')";
		}
		
		return queries;
	}
	
	public static String[] getContentSeeds(){
		return getContentSeeds("");
	}
	
	/*
	 * Pivot Table Seeders
	 */
	
	//TODO
}

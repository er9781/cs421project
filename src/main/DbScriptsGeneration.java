package main;

public class DbScriptsGeneration {

	public static void generateP2Scripts(){
		
		String prefix = "";
		//output sql schema creation file for testing
		SchemaDefinition.outputCreateSqlScript("outputs/q2createSchema.sql", prefix);
		SchemaDefinition.outputDropSqlScript("outputs/dropDB.sql", prefix);
		SchemaDefinition.outputUserSeedScript("outputs/q3userSelects.sql", prefix);
		
		//output seeds script
		SchemaDefinition.outputQuerySet("outputs/q4inserts.sql", DbScriptsGeneration.getSeeds(prefix));
		SchemaDefinition.outputSelectScript("outputs/q4selects.sql", prefix);
		
		//output select statements for Q5
		int userId = 3;
		int pageId = 5;
		String[] selects = {
//				DataViews.getAdmins(prefix),
				DataViews.getPagesViewable(prefix, userId),
				DataViews.getNonDismissedAlerts(prefix, userId),
//				DataViews.getUnassignedMenuItemIds(prefix),
//				DataViews.getPageContent(prefix, pageId),
				DataViews.getMessages(prefix),
				DataViews.getActiveUserNames(prefix),
				DataViews.getPageAuthors(prefix),
		};
		SchemaDefinition.outputQuerySet("outputs/q5selects.sql", selects);
		
	}
	
	public static String[] getSeeds(String prefix){
		String[][] seeds = {
				//Models
				DataSeeds.getUserSeeds(prefix),
				DataSeeds.getMessageSeeds(prefix),
				DataSeeds.getAlertSeeds(prefix),
				DataSeeds.getPageSeeds(prefix),
				DataSeeds.getCommentSeeds(prefix),
				DataSeeds.getMenuItemSeeds(prefix),
				DataSeeds.getMenuItemConfigurationSeeds(prefix),
				DataSeeds.getContentSeeds(prefix),
				
				//Relations
				DataSeeds.getReceivesSeeds(prefix),
				DataSeeds.getNotifiesSeeds(prefix),
				DataSeeds.getViewableBySeeds(prefix),
				DataSeeds.getPageMenuSeeds(prefix),
		};
		
		return SchemaDefinition.flatten(seeds);
	}
	
}

package main;

class Main {
	public static void main(String[] args){
		
		String prefix = "";
		//output sql schema creation file for testing
		SchemaDefinition.outputCreateSqlScript("outputs/q2createSchema.sql", prefix);
		SchemaDefinition.outputDropSqlScript("outputs/dropDB.sql", prefix);
		SchemaDefinition.outputUserSeedScript("outputs/q3userSelects.sql", prefix);
		
		//output seeds script
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

		//output insert and select statements for Q4
		SchemaDefinition.outputQuerySet("outputs/q4inserts.sql", seeds);
		SchemaDefinition.outputSelectScript("outputs/q4selects.sql", prefix);
		
		//output select statements for Q5
		int userId = 3;
		int pageId = 5;
		String[] selects = {
				DataViews.getPagesViewable(prefix, userId),
				DataViews.getNonDismissedAlerts(prefix, userId),
				DataViews.getMessages(prefix),
				DataViews.getActiveUserNames(prefix),
				DataViews.getPageAuthors(prefix),
		};
		SchemaDefinition.outputQuerySet("outputs/q5selects.sql", selects);

	}
}

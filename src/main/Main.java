package main;

import java.sql.*;

class Main {
	public static void main(String[] args){
		
		String prefix = "simon_";
		//output sql schema creation file for testing
		SchemaDefinition.outputCreateSqlScript("outputs/createScript.sql", prefix);
		SchemaDefinition.outputDropSqlScript("outputs/dropScript.sql", prefix);
		SchemaDefinition.outputUserSeedScript("outputs/userSeed.sql", prefix);
		
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
		SchemaDefinition.outputQuerySet("outputs/seeds.sql", seeds);
		SchemaDefinition.outputSelectScript("outputs/selects.sql", prefix);
		
		//output select statements for Q5
		int userId = 3;
		int pageId = 5;
		String[] selects = {
				DataViews.getAdmins(prefix),
				DataViews.getPagesViewable(prefix, userId),
				DataViews.getNonDismissedAlerts(prefix, userId),
				DataViews.getUnassignedMenuItemIds(prefix),
				DataViews.getPageContent(prefix, pageId),
				DataViews.getMessages(prefix, userId),
				DataViews.getPageAuthors(prefix),
		};
		SchemaDefinition.outputQuerySet("outputs/q5selects.sql", selects);
		
//		//open db connection
//		System.out.println("Attempting to establish DB connection.");
//		DbConnection con = new DbConnection();
//		System.out.println("DB connection established.");
//		
//		//create the schema
//		String[] tables = SchemaDefinition.getTables("simon_");
//		String tableName = tables[0];
//		con.executeUpdate(tables);
//		String createSQL = "CREATE TABLE " + tableName + " (id INTEGER, name VARCHAR (25)) ";
//		con.execute(createSQL);
//
//		// Inserting Data into the table
//		String insertSQL = "INSERT INTO " + tableName + " VALUES ( 1 , \'Vicki\' ) ";
//		con.executeUpdate(insertSQL);
//
//		insertSQL = "INSERT INTO " + tableName + " VALUES ( 2 , \'Vera\' ) ";
//		con.executeUpdate(insertSQL);
//		insertSQL = "INSERT INTO " + tableName + " VALUES ( 3 , \'Franca\' ) ";
//		con.executeUpdate(insertSQL);
//
//
//		// Querying a table
//		String querySQL = "SELECT id, name from " + tableName + " WHERE NAME = \'Vicki\'";
//		ResultSet rs = con.executeQuery(querySQL);
//		try {
//			while (rs.next()) {
//				int id = rs.getInt(1);
//				String name = rs.getString(2);
//				System.out.println("id:  " + id);
//				System.out.println("name:  " + name);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		// Updating a table
//		String updateSQL = "UPDATE " + tableName + " SET NAME = \'Mimi\' WHERE id = 3";
//		con.executeUpdate(updateSQL);
//
//		// Dropping a table
//		String dropSQL = "DROP TABLE " + tableName;
//		con.executeUpdate(dropSQL);
//		
//		//close out connection
//		con.close();
	}
}

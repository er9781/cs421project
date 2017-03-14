package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SchemaDefinition {

	public static String[] tableNames = {
			"user",
			"message",
			"receives",
			"alert",
			"notifies",
			"page",
			"viewable_by",
			"comment",
			"menu_item",
			"menu_item_configuration",
			"page_menu",
			"content",
	};
	
	/**
	 * Returns the create statements for the tables of the database in a String array.
	 * @param prefix the prefix to be used on all table names
	 * @return the CREATE TABLE statements for the tables of the schema
	 */
	public static String[] getTables(String prefix){
		String[] tables = {
				"CREATE TABLE " + prefix + "user ("
						+ "user_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), "
						+ "username VARCHAR(50) UNIQUE NOT NULL, "
						+ "name VARCHAR(50) NOT NULL, "
						+ "password VARCHAR(191) NOT NULL, "
						+ "role VARCHAR(10) NOT NULL, "
						+ "CHECK (role='admin' OR role='user') "
					+ ")",
				"CREATE TABLE " + prefix + "message ("
						+ "message_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), "
						+ "text VARCHAR(500) NOT NULL, "
						+ "owner_id INTEGER NOT NULL, "
						+ "FOREIGN KEY (owner_id) REFERENCES " + prefix + "user (user_id) "
					+ ")",
				"CREATE TABLE " + prefix + "receives ("
						+ "message_id INTEGER NOT NULL, "
						+ "user_id INTEGER NOT NULL, "
						+ "PRIMARY KEY (message_id, user_id), "
						+ "FOREIGN KEY (message_id) REFERENCES " + prefix + "message (message_id), "
						+ "FOREIGN KEY (user_id) REFERENCES " + prefix + "user (user_id) "
					+ ")",
				"CREATE TABLE " + prefix + "alert ("
						+ "message_id INTEGER NOT NULL PRIMARY KEY, "
						+ "FOREIGN KEY (message_id) REFERENCES " + prefix + "message (message_id), "
						+ "type VARCHAR(7) NOT NULL, "
						+ "CHECK (type='success' OR type='info' OR type='warning' OR type='danger') "
					+ ")",
				"CREATE TABLE " + prefix + "notifies ("
						+ "message_id INTEGER NOT NULL, "
						+ "user_id INTEGER NOT NULL, "
						+ "is_dismissed SMALLINT NOT NULL, "
						+ "CHECK (is_dismissed=0 OR is_dismissed=1), "
						+ "PRIMARY KEY (message_id, user_id), "
						+ "FOREIGN KEY (message_id) REFERENCES " + prefix + "message (message_id), "
						+ "FOREIGN KEY (user_id) REFERENCES " + prefix + "user (user_id) "
					+ ")",
				"CREATE TABLE " + prefix + "page ("
						+ "page_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), "
						+ "title VARCHAR(200) NOT NULL, "
						+ "is_public SMALLINT NOT NULL, "
						+ "owner_id INTEGER NOT NULL, "
						+ "FOREIGN KEY (owner_id) REFERENCES " + prefix + "user (user_id), "
						+ "CHECK (is_public=0 OR is_public=1) "
					+ ")",
				"CREATE TABLE " + prefix + "viewable_by ("
						+ "page_id INTEGER NOT NULL, "
						+ "user_id INTEGER NOT NULL, "
						+ "PRIMARY KEY (page_id, user_id), "
						+ "FOREIGN KEY (page_id) REFERENCES " + prefix + "page (page_id), "
						+ "FOREIGN KEY (user_id) REFERENCES " + prefix + "user (user_id) "
					+ ")",
				"CREATE TABLE " + prefix + "comment ("
						+ "comment_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), "
						+ "text VARCHAR(200) NOT NULL, "
						+ "page_id INTEGER NOT NULL, "
						+ "FOREIGN KEY (page_id) REFERENCES " + prefix + "page (page_id), "
						+ "owner_id INTEGER NOT NULL, "
						+ "FOREIGN KEY (owner_id) REFERENCES " + prefix + "user (user_id) "
					+ ")",
				"CREATE TABLE " + prefix + "menu_item ("
						+ "menu_item_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), "
						+ "text VARCHAR(200) NOT NULL, "
						+ "url VARCHAR(200) NOT NULL "
					+ ")",
				"CREATE TABLE " + prefix + "menu_item_configuration ("
						+ "config_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), "
						+ "padding INTEGER NOT NULL, "
						+ "background_colour VARCHAR(20) NOT NULL, "
						+ "text_colour VARCHAR(20) NOT NULL "
					+ ")",
				"CREATE TABLE " + prefix + "page_menu ("
						+ "page_id INTEGER NOT NULL, "
						+ "FOREIGN KEY (page_id) REFERENCES " + prefix + "page (page_id), "
						+ "menu_item_id INTEGER NOT NULL, "
						+ "FOREIGN KEY (menu_item_id) REFERENCES " + prefix + "menu_item (menu_item_id), "
						+ "config_id INTEGER NOT NULL, "
						+ "FOREIGN KEY (config_id) REFERENCES " + prefix + "menu_item_configuration (config_id), "
						+ "index INTEGER NOT NULL, "
						+ "PRIMARY KEY (page_id, menu_item_id, config_id) "
					+ ")",
				"CREATE TABLE " + prefix + "content ("
						+ "page_id INTEGER NOT NULL, "
						+ "FOREIGN KEY (page_id) REFERENCES " + prefix + "page (page_id), "
						+ "index INTEGER NOT NULL, "
						+ "styling VARCHAR(7) NOT NULL, "
						+ "type VARCHAR(7) NOT NULL, "
						+ "content CLOB NOT NULL, "
						+ "PRIMARY KEY (page_id, index), "
						+ "CHECK (styling='airy' OR styling='compact') "
					+ ")",
		};
		
		return tables;
	}
	
	/**
	 * Returns the create statements for the tables of the database in a String array.
	 * @return the CREATE TABLE statements for the tables of the schema
	 */
	public static String[] getTables(){
		return getTables("");
	}
	
	public static void executeCreateTables(DbConnection con, String prefix){
		con.executeUpdate(SchemaDefinition.getTables(prefix));
	}
	
	public static void outputCreateSqlScript(String filename, String prefix){
		String[] tables = getTables(prefix);
		
		outputQuerySet(filename, tables);
	}
	
	public static void outputSqlScript(){
		outputCreateSqlScript("outputs/createSchema.sql", "");
	}
	
	public static String[] getDropTableQueries(String prefix){
		String[] queries = new String[tableNames.length];
		
		for(int i = 0; i < queries.length; i++){
			queries[i] = "DROP TABLE " + prefix + tableNames[i];
		}
		
		return queries;
	}
	
	public static void outputDropSqlScript(String filename, String prefix){
		String[] queries = getDropTableQueries(prefix);
		
		outputQuerySet(filename, queries);
	}
	
	public static void outputDropSqlScript(){
		outputDropSqlScript("outputs/dropSchema.sql", "");
	}
	
	public static void outputUserSeedScript(String filename, String prefix){
		outputQuerySet(filename, DataSeeds.getUserSeeds(prefix));
	}
	
	public static void outputUserSeedScript(){
		outputUserSeedScript("outputs/userSeed.sql", "");
	}
	
	public static void outputSelectScript(String filename, String prefix){
		int n = 10;
		
		String[] queries = new String[tableNames.length];
		for(int i = 0; i < queries.length; i++){
			queries[i] = "SELECT * FROM " + prefix + tableNames[i] + " FETCH FIRST " + n + " ROWS ONLY"; 
		}
		
		outputQuerySet(filename, queries);
	}
	
	public static void outputSelectScript(){
		outputSelectScript("outputs/selectScript.sql", "");
	}
	
	public static void outputQuerySet(String filename, String[] queries){

		PrintWriter w = null;
		try {
			w = new PrintWriter(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		w.println("connect to cs421;");//connect to db at the top of the script.
		
		for(String query: queries){
			w.println(query + ";");
		}
		
		w.close();
	}
	
	public static void outputQuerySet(String filename, String[][] queries){
		//flatten query array and pipe to regular outputQuerySet function
		String[] flat = flatten(queries);
		outputQuerySet(filename, flat);
	}
	
	public static String[] flatten(String[][] array){
		//sum lengths
		int len = 0;
		for(int i = 0; i < array.length; i++){
			len += array[i].length;
		}
		
		String[] flat = new String[len];
		int index = 0;
		for(int i = 0; i < array.length; i++){
			System.arraycopy(array[i], 0, flat, index, array[i].length);
			index += array[i].length;
		}
		
		return flat;
	}
	
}

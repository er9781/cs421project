package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SchemaDefinition {

	/**
	 * Returns the create statements for the tables of the database in a String array.
	 * @param prefix the prefix to be used on all table names
	 * @return the CREATE TABLE statements for the tables of the schema
	 */
	public static String[] getTables(String prefix){
		String[] tables = {
//				"CREATE TABLE " + prefix + "example (id INTEGER, name VARCHAR (25))",
				"CREATE TABLE " + prefix + "user ("
						+ "user_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),"
						+ "username VARCHAR(50) UNIQUE,"
						+ "name VARCHAR(50) NOT NULL,"
						+ "password VARCHAR(191) NOT NULL,"
						+ "role VARCHAR(10) NOT NULL,"
						+ "CHECK (role='admin' OR role='user'),"
					+ ")",
				"CREATE TABLE " + prefix + "message ("
						+ "message_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),"
						+ "text VARCHAR(500) NOT NULL,"
						+ "owner_id INTEGER NOT NULL FOREIGN KEY REFERENCES user (user_id),"
					+ ")",
				"CREATE TABLE " + prefix + "receives ("
						+ "message_id INTEGER NOT NULL,"
						+ "user_id INTEGER NOT NULL,"
						+ "PRIMARY KEY (message_id, user_id),"
						+ "FOREIGN KEY (message_id) REFERENCES message (message_id),"
						+ "FOREIGN KEY (user_id) REFERENCES user (user_id),"
					+ ")",
				"CREATE TABLE " + prefix + "alert ("
						+ "message_id INTEGER PRIMARY KEY FOREIGN KEY REFERENCES message (message_id),"
						+ "type VARCHAR(7) NOT NULL,"
						+ "CHECK (type='success' OR type='info' OR type='warning' OR type='danger'),"
					+ ")",
				"CREATE TABLE " + prefix + "notifies ("
						+ "message_id INTEGER NOT NULL,"
						+ "user_id INTEGER NOT NULL,"
						+ "is_dismissed SMALLINT NOT NULL,"
						+ "CHECK (is_dismissed=0 OR is_dismissed=1),"
						+ "PRIMARY KEY (message_id, user_id),"
						+ "FOREIGN KEY (message_id) REFERENCES message (message_id),"
						+ "FOREIGN KEY (user_id) REFERENCES user (user_id),"
					+ ")",
				"CREATE TABLE " + prefix + "page ("
						+ "page_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),"
						+ "title VARCHAR(200) NOT NULL,"
						+ "is_public SMALLINT NOT NULL,"
						+ "owner_id INTEGER NOT NULL FOREIGN KEY REFERENCES user (user_id),"
						+ "CHECK (is_public=0 OR is_public=1),"
					+ ")",
				"CREATE TABLE " + prefix + "viewableBy ("
						+ "page_id INTEGER NOT NULL,"
						+ "user_id INTEGER NOT NULL,"
						+ "PRIMARY KEY (page_id, user_id),"
						+ "FOREIGN KEY page_id REFERENCES page (page_id),"
						+ "FOREIGN KEY user_id REFERENCES user (user_id),"
					+ ")",
				"CREATE TABLE " + prefix + "comment ("
						+ "comment_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),"
						+ "text VARCHAR(200) NOT NULL,"
						+ "page_id NOT NULL FOREIGN KEY REFERENCES page (page_id),"
						+ "user_id NOT NULL FOREIGN KEY REFERENCES user (user_id),"
					+ ")",
				"CREATE TABLE " + prefix + "menu_item ("
						+ "menu_item_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),"
						+ "text VARCHAR(200) NOT NULL,"
						+ "url VARCHAR(200) NOT NULL,"
					+ ")",
				"CREATE TABLE " + prefix + "menu_item_configuration ("
						+ "config_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),"
						+ "padding INTEGER NOT NULL,"
						+ "background_colour VARCHAR(20) NOT NULL,"
						+ "text_colour VARCHAR(20) NOT NULL,"
					+ ")",
				"CREATE TABLE " + prefix + "page_menu ("
						+ "page_id INTEGER FOREIGN KEY REFERENCES page (page_id),"
						+ "menu_item_id INTEGER FOREIGN KEY REFERENCES menu_item (menu_item_id),"
						+ "config_id INTEGER FOREIGN KEY REFERENCES menu_item_configuration (config_id),"
						+ "index INTEGER NOT NULL,"
						+ "PRIMARY KEY (page_id, menu_item_id, config_id),"
					+ ")",
				"CREATE TABLE " + prefix + "content ("
						+ "page_id INTEGER FOREIGN KEY REFERENCES page (page_id),"
						+ "index INTEGER NOT NULL,"
						+ "styling VARCHAR(7) NOT NULL,"
						+ "type VARCHAR(7) NOT NULL,"
						+ "content CLOB NOT NULL,"
						+ "PRIMARY KEY (page_id, index),"
						+ "CHECK (styling='airy' OR styling='compact'),"
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
	
	public static void outputSqlScript(String filename, String prefix){
		String[] tables = getTables(prefix);
		
		PrintWriter w = null;
		try {
			w = new PrintWriter(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(String table: tables){
			w.println(table + ";");
		}
		
		w.close();
	}
	
	public static void outputSqlScript(){
		outputSqlScript("createSchema.sql", "");
	}
	
}

package main;

class Main {
	
	public static void main(String[] args){
		
		//parse input options and pass control appropriately
		if(args.length == 0){
			//Begin Application Loop
			Control control = new Control();
			control.beginApplication();
		}else{
			switch(args[0]){
			case "db:createSchema":
				//execute table creation on the database.
				SchemaDefinition.executeCreateTables(new DbConnection(), "");
				break;
			case "db:seed":
				//seed the database.
				DbConnection con = new DbConnection();
				con.executeUpdate(DbScriptsGeneration.getSeeds(""));
				break;
			case "P2Scripts":
				//generate Scripts for Project 2
				DbScriptsGeneration.generateP2Scripts();
				break;
			}
		}
	}
}

package main;

import java.sql.* ;

class TableCreate
{
	public static void main ( String [ ] args ) throws SQLException
	{
		String tablePrefix = "simon_";
		String tableName = "example";
		
		int sqlCode=0;      // Variable to hold SQLCODE
		String sqlState="00000";  // Variable to hold SQLSTATE

		//register the driver
		try {
			DriverManager.registerDriver ( new com.ibm.db2.jcc.DB2Driver() ) ;
		} catch (Exception cnfe){
			System.out.println("Class not found for JDBC driver.");
		}

		//open connection to database
		String url = "jdbc:db2://comp421.cs.mcgill.ca:50000/cs421";
		Connection con = DriverManager.getConnection(url, "cs421g35", "Group35_2017");
		Statement statement = con.createStatement();

		// Creating a table
		try {
			String createSQL = "CREATE TABLE " + tableName + " (id INTEGER, name VARCHAR (25)) ";
			System.out.println (createSQL ) ;
			statement.execute (createSQL ) ;
			System.out.println ("DONE");
		}catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}

		// Inserting Data into the table
		try {
			String insertSQL = "INSERT INTO " + tableName + " VALUES ( 1 , \'Vicki\' ) " ;
			System.out.println ( insertSQL ) ;
			statement.executeUpdate ( insertSQL ) ;
			System.out.println ( "DONE" ) ;

			insertSQL = "INSERT INTO " + tableName + " VALUES ( 2 , \'Vera\' ) " ;
			System.out.println ( insertSQL ) ;
			statement.executeUpdate ( insertSQL ) ;
			System.out.println ( "DONE" ) ;
			insertSQL = "INSERT INTO " + tableName + " VALUES ( 3 , \'Franca\' ) " ;
			System.out.println ( insertSQL ) ;
			statement.executeUpdate ( insertSQL ) ;
			System.out.println ( "DONE" ) ;

		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}

		// Querying a table
		try {
			String querySQL = "SELECT id, name from " + tableName + " WHERE NAME = \'Vicki\'";
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			while ( rs.next ( ) ) {
				int id = rs.getInt ( 1 ) ;
				String name = rs.getString (2);
				System.out.println ("id:  " + id);
				System.out.println ("name:  " + name);
			}
			System.out.println ("DONE");
		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}

		//Updating a table
		try {
			String updateSQL = "UPDATE " + tableName + " SET NAME = \'Mimi\' WHERE id = 3";
			System.out.println(updateSQL);
			statement.executeUpdate(updateSQL);
			System.out.println("DONE");

			// Dropping a table
			String dropSQL = "DROP TABLE " + tableName;
			System.out.println ( dropSQL ) ;
			statement.executeUpdate ( dropSQL ) ;
			System.out.println ("DONE");
		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}


		// Finally but importantly close the statement and connection
		statement.close ( ) ;
		con.close ( ) ;
	}
}

package main;

import java.sql.*;

public class DbConnection {

	private Connection con;
	private Statement stmt;

	/**
	 * Open a new database connection and remain ready for queries.
	 */
	public DbConnection(){
		
		// register the driver
		try {
			DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
		} catch (Exception cnfe) {
			System.out.println("Class not found for JDBC driver.");
		}
		
		System.out.println("open con");

		// open connection to database
		try {
			String url = "jdbc:db2://comp421.cs.mcgill.ca:50000/cs421";
			DriverManager.setLoginTimeout(30);//set login timeout to 15s.
			this.con = DriverManager.getConnection(url, "cs421g35", "Group35_2017");
			this.stmt = con.createStatement();
		} catch (SQLException e) {
			System.out.println("Error establishing database connection.");
			this.handleSqlError(e);
		}
	}
	
	public void execute(String sql){
		try {
			System.out.println(sql);
			this.stmt.execute(sql);
			System.out.println("DONE");
		} catch (SQLException e) {
			this.handleSqlError(e);
		}
	}
	
	public ResultSet executeQuery(String sql){
		try {
			System.out.println(sql);
			ResultSet result = this.stmt.executeQuery(sql);
			System.out.println("DONE");
			return result;
		} catch (SQLException e) {
			this.handleSqlError(e);
		}
		return null;
	}
	
	public void executeUpdate(String sql){
		try {
			System.out.println(sql);
			this.stmt.executeUpdate(sql);
			System.out.println("DONE");
		} catch (SQLException e) {
			this.handleSqlError(e);
		}
	}
	
	/**
	 * Execute a sequence of sql updates.
	 * @param sqls
	 */
	public void executeUpdate(String[] sqls){
		for(int i = 0; i < sqls.length; i++){
			this.executeUpdate(sqls[i]);
		}
	}
	
	/**
	 * Close the statement and database connections.
	 */
	public void close(){
		try {
			this.stmt.close();
			this.con.close();
		} catch (SQLException e) {
			this.handleSqlError(e);
		}
	}
	
	private void handleSqlError(SQLException e){
		int sqlCode = e.getErrorCode(); // Get SQLCODE
		String sqlState = e.getSQLState(); // Get SQLSTATE
		System.out.println("Failed Query: Code: " + sqlCode + "  sqlState: " + sqlState);
		System.out.println(e.getMessage());
	}
	
}

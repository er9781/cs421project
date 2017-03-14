package interactions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import main.DbConnection;

public class ViewAlert extends UserInteraction {

	private String ownerName;
	private int messageId;
	private String userId;
	
	public ViewAlert(int alertId, String ownerName, String userId){
		this.messageId = alertId;
		this.ownerName = ownerName;
		this.userId = userId;
	}
	
	@Override
	public String getDisplayTitle() {
		return "View alert from " + this.ownerName;
	}

	@Override
	public Context execute(Scanner in, DbConnection con) {
		String sql = "SELECT a.type, m.text FROM alert a, message m "
				+ "WHERE m.message_id=a.message_id AND m.message_id=" + this.messageId;
		ResultSet res = con.executeQuery(sql);
		try {
			res.next();//only 1 record selected
			String type = res.getString(1);
			String text = res.getString(2);
			System.out.println("Alert type " + type + " from " + this.ownerName);
			System.out.println(text);
			
			System.out.println("Would you like to dismiss the alert? (y/n)");
			String input = in.nextLine();
			if(input.equals("y")){
				sql = "UPDATE notifies SET is_dismissed=1 WHERE message_id="+this.messageId;
				con.executeUpdate(sql);
				System.out.println("The alert has been dismissed.\n");
				
				//reload alerts list since there is one less now.
				return new ListNonDismissedAlerts(this.userId).execute(in, con);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println();
		return null;
	}

	@Override
	public String getDescription() {
		return "This will show the specified alert and give the option to dismiss it.";
	}

}

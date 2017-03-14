package interactions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import main.Control;
import main.DbConnection;

public class ListNonDismissedAlerts extends UserInteraction {

	private String userId;
	
	public ListNonDismissedAlerts(String userId){
		this.userId = userId;
	}
	
	@Override
	public String getDisplayTitle() {
		return "View Alerts";
	}

	@Override
	public Context execute(Scanner in, DbConnection con) {

		//get the page titles viewable by the given user_id
		String sql = "SELECT u.name, m.message_id FROM alert a, message m, user u "
				+ "WHERE a.message_id=m.message_id AND u.user_id=m.owner_id AND a.message_id IN ("
					+ "SELECT message_id FROM notifies WHERE user_id="+this.userId+" AND is_dismissed=0"
				+ ")";
		ResultSet res = con.executeQuery(sql);
		ArrayList<String> names = new ArrayList<String>(10);
		ArrayList<Integer> alertIds = new ArrayList<Integer>(10);
		try {
			while(res.next()){
				names.add(res.getString(1));
				alertIds.add(res.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		UserInteraction[] options = new UserInteraction[names.size() + 1];
		for(int i = 0; i < names.size(); i++){
			options[i] = new ViewAlert(alertIds.get(i), names.get(i), this.userId);
		}
		options[names.size()] = Control.getReturnToMainMenuOption();
		Context alertsList = new Context(in, con, options, "Select an alert to view.");
		
		return alertsList;
	}

	@Override
	public String getDescription() {
		return "This will show the non-dissmissed alerts for the user.";
	}

}

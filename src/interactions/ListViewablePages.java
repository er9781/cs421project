package interactions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import main.Control;
import main.DbConnection;

/**
 * UserInteraction which switches the context to a list of the viewable pages for a user.
 * @author Simon
 *
 */
public class ListViewablePages extends UserInteraction {

	private String userId;
	
	public ListViewablePages(String userId){
		this.userId = userId;
	}
	
	@Override
	public String getDisplayTitle() {
		return "List pages";
	}

	@Override
	public Context execute(Scanner in, DbConnection con) {
		
		//get the page titles viewable by the given user_id
		String sql = "SELECT DISTINCT p.page_id, title FROM page p, viewable_by v "
				+ "WHERE (p.page_id=v.page_id AND v.user_id="+this.userId+") OR p.is_public=1";
		ResultSet res = con.executeQuery(sql);
		ArrayList<String> titles = new ArrayList<String>(10);
		ArrayList<Integer> ids = new ArrayList<Integer>(10);
		try {
			while(res.next()){
				ids.add(res.getInt(1));
				titles.add(res.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		UserInteraction[] options = new UserInteraction[titles.size() + 1];
		for(int i = 0; i < titles.size(); i++){
			options[i] = new ViewPage(ids.get(i), titles.get(i));
		}
		options[titles.size()] = Control.getReturnToMainMenuOption();
		Context pagesList = new Context(in, con, options, "Select a page to view.");
		
		return pagesList;
	}

	@Override
	public String getDescription() {
		return "This will list the current viewable pages for the current user.";
	}

}

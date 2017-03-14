package interactions;

import java.util.Scanner;

import main.Control;
import main.DbConnection;

/**
 * UserInteraction which switches the context to a list of the viewable pages for a user.
 * @author Simon
 *
 */
public class ListViewablePages extends UserInteraction {

	private String username;
	
	public ListViewablePages(String username){
		this.username = username;
	}
	
	@Override
	public String getDisplayTitle() {
		return "List pages";
	}

	@Override
	public Context execute(Scanner in, DbConnection con) {
		
		//TODO DB connection to get viewable pages for the user
		String[] pages = {
				"Masters of War",
				"Something interesting",
				"Something boring",
				"Who cares"
		};
		
		UserInteraction[] options = new UserInteraction[pages.length + 1];
		for(int i = 0; i < pages.length; i++){
			options[i] = new ViewPage(pages[i]);
		}
		options[pages.length] = Control.getReturnToMainMenuOption();
		Context pagesList = new Context(in, con, options, "Select a page to view.");
		
		return pagesList;
	}

	@Override
	public String getDescription() {
		return "This will list the current viewable pages for the current user.";
	}

}

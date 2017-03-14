package interactions;

import java.util.Scanner;

import main.DbConnection;

/**
 * UserInteraction to view a general page and its content.
 * @author Simon
 *
 */
public class ViewPage extends UserInteraction {

	private String pageTitle;
	
	public ViewPage(String pageTitle){
		this.pageTitle = pageTitle;
	}
	
	@Override
	public String getDisplayTitle() {
		return "View page: " + this.pageTitle;
	}

	@Override
	public Context execute(Scanner in, DbConnection con) {
		System.out.println("Connect to DB and get page content for \"" + this.pageTitle + "\" and present it.\n");
		
		return null;
	}

	@Override
	public String getDescription() {
		return "This will display the contents of the page " + this.pageTitle;
	}

}

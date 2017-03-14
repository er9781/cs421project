package interactions;

import java.util.Scanner;
import main.DbConnection;

/**
 * UserInteraction to Quit the application.
 * @author Simon
 *
 */
public class Quit extends UserInteraction {

	@Override
	public String getDisplayTitle() {
		return "Quit";
	}

	@Override
	public Context execute(Scanner in, DbConnection con) {
		//return basic quit context.
		return Context.getQuitContext();
	}

	@Override
	public String getDescription() {
		return "This exits the application.";
	}

}

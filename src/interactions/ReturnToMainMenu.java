package interactions;

import java.util.Scanner;
import main.Control;

import main.DbConnection;

/**
 * UserInteraction to return to the main menu of the application
 * @author Simon
 *
 */
public class ReturnToMainMenu extends UserInteraction {

	@Override
	public String getDisplayTitle() {
		return "Return to main menu";
	}

	@Override
	public Context execute(Scanner in, DbConnection con) {
		return Control.getMainMenu();
	}

	@Override
	public String getDescription() {
		return "This returns to the main menu.";
	}

}

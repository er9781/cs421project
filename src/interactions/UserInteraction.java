package interactions;

import java.util.Scanner;
import main.DbConnection;

public abstract class UserInteraction {

	
	/**
	 * Returns the string to display in the menu.
	 * @return Display string to represent this option
	 */
	public abstract String getDisplayTitle();
	
	/**
	 * Runs the interaction. This optionally returns a new context. If null is returned, this option is assumed
	 * to not have a sub-menu and the previous menu will be shown.
	 * 
	 * @param Scanner in. A scanner instance is passed in for use if the interaction requires input.
	 * @param DbConnection con. A connection to the database for use by the interaction.
	 * @return
	 */
	public abstract Context execute(Scanner in, DbConnection con);
	
	/**
	 * Returns a string to describe the function of the option.
	 * @return
	 */
	public abstract String getDescription();
	
}

package interactions;

import java.util.Scanner;

import main.DbConnection;

/**
 * Example of a UserInteraction class which requests user input.
 * @author Simon
 *
 */
public class Option1 extends UserInteraction {

	@Override
	public String getDisplayTitle() {
		return "Option Poop";
	}

	@Override
	public Context execute(Scanner in, DbConnection con) {
		String input = "";
		boolean isValid = false;
		while( ! isValid){
			
			System.out.println("Please input an interger.");
			input = in.nextLine();
			
			isValid = Context.validateInteger(input);
			if( ! isValid){
				System.out.println("Your input was not of the correct format");
			}
			
		}
		
		System.out.println("Option 1 executed. Input: " + input + "\n");
		return null;
	}

	@Override
	public String getDescription() {
		return "This option does some shit. Whatever";
	}

}

package main;

import java.util.Scanner;
import interactions.*;

public class Control {
	
	/*
	 * Static resources
	 */
	public static Context mainMenu;
	public static UserInteraction returnToMainMenu = new ReturnToMainMenu();
	public static UserInteraction quit = new Quit();
	
	/*
	 * Database and Input configuration
	 */
	protected Scanner in;
	protected DbConnection con;
	
	/**
	 * Create a new Control instance. This accepts the input source for the application.
	 * If null is passed, it will use System.in by default.
	 * @param in
	 */
	public Control(Scanner in, DbConnection con){
		if(in == null){
			in = new Scanner(System.in);
		}
		if(con == null){
			con = new DbConnection();
		}
		this.in = in;
		this.con = con;
		
		UserInteraction[] mainMenuOptions = {
				new Option1(),
				new ListViewablePages("1"),
				Control.quit
		};
		Control.mainMenu = new Context(this.in, this.con, mainMenuOptions);
	}
	
	/**
	 * Create a new Control instance with System.in as the input source and the default database connection.
	 */
	public Control(){
		this(null, null);
	}
	
	/**
	 * Launches the loop for input/output of the application
	 */
	public void beginApplication(){
		
		
		Context currentContext = Control.getMainMenu();
		
		while( ! currentContext.isQuitRequested()){
			
			System.out.println(currentContext.getMenu());
			
			//get and validate input
			String input = this.in.nextLine();
			if( ! currentContext.validateInput(input)){
				System.out.println(currentContext.getErrorMessage(input));
			}else{
				//selection is valid. execute the option
				Context newContext = currentContext.executeOption(input);
				
				if(newContext != null){
					currentContext = newContext;
				}
			}
			
		}
		
		System.out.println("The application has terminated successfully.");
		
	}
	
	/*
	 * Static functions
	 */
	
	public static Context getMainMenu(){
		return Control.mainMenu;
	}
	
	public static UserInteraction getReturnToMainMenuOption(){
		return Control.returnToMainMenu;
	}
	
	public static UserInteraction getQuitOption(){
		return Control.quit;
	}
	
}


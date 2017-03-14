package interactions;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.DbConnection;

public class Context {

	protected Scanner in;
	protected UserInteraction[] options;
	protected boolean quitRequested = false;
	protected DbConnection con;
	protected String topMessage;
	
	protected static Pattern integerPattern = Pattern.compile("^\\d+$");
	
	public Context(Scanner in, DbConnection con, UserInteraction[] options, String topMessage){
		//check if any options are null, and remove those options.
		int numNulls = 0;
		for(UserInteraction option: options){
			if(option == null){
				numNulls++;
			}
		}
		if(numNulls == 0){
			this.options = options;
		}else{
			this.options = new UserInteraction[options.length - numNulls];
			int nullsPassed = 0;
			for(int i = 0; i < options.length; i++){
				if(options[i] == null){
					nullsPassed++;
				}else{
					this.options[i - nullsPassed] = options[i];
				}
			}
		}
		
		//set remainder of the context configuration
		this.in = in;
		this.con = con;
		this.topMessage = topMessage;
	}
	
	public Context(Scanner in, DbConnection con, UserInteraction[] options){
		this(in, con, options, "Please select among the following options.");
	}
	
	public Context(boolean quitRequested) {
		this.quitRequested = quitRequested;
		this.in = null;
		this.options = null;
		this.topMessage = "";
	}

	/**
	 * Returns the string that represents the set of options to be shown to the user.
	 * @return
	 */
	public String getMenu(){
		String toRet = "";
		
		if(this.options == null){
			return toRet;
		}
		
		toRet += this.topMessage + "\n";
		
		for(int i = 0; i < this.options.length; i++){
			toRet += (i + 1) + ": " + this.options[i].getDisplayTitle() + "\n";
		}
		
		return toRet;
	}
	
	/**
	 * Returns if this context has the quitRequested flag set to true.
	 * @return
	 */
	public boolean isQuitRequested(){
		return this.quitRequested;
	}
	
	/**
	 * Executes the given input and optionally returns a new Context to be presented to the user.
	 * @param input
	 * @return
	 */
	public Context executeOption(String input){
		//assumes valid input already? Or check here as well.
		int optionNumber = Integer.parseInt(input);
		if( ! this.validateOptionNumber(optionNumber)){
			//throw error?
			return null;
		}
		
		return this.options[optionNumber - 1].execute(this.in, this.con);
	}
	
	/**
	 * Validates if the given input is allowed to be executed by this context.
	 * @param input
	 * @return
	 */
	public boolean validateInput(String input){
		if( ! Context.validate(input, Context.integerPattern) ){
			return false;
		}else{
			return this.validateOptionNumber(Integer.parseInt(input));
		}
	}
	
	/**
	 * Validates that the given option is within the allowed range of integers.
	 * @param optionNumber
	 * @return
	 */
	private boolean validateOptionNumber(int optionNumber){
		return ! (optionNumber < 1 || this.options == null || optionNumber > this.options.length);
	}
	
	/**
	 * Returns the error message to be presented for the given input. The input is assumed to 
	 * have already been validated and is invalid.
	 * @param input
	 * @return
	 */
	public String getErrorMessage(String input){
		return "The selection made was not among the valid options.";
	}
	
	/*
	 * Static utilities
	 */
	
	/**
	 * Returns a basic context with the quitRequested flag set to true.
	 * @return
	 */
	public static Context getQuitContext(){
		return new Context(true);
	}
	
	/**
	 * Validates an input string against the given pattern.
	 * @param input
	 * @param pattern
	 * @return true if a match is found and false otherwise
	 */
	public static boolean validate(String input, Pattern pattern){
		Matcher m = pattern.matcher(input);
		return m.find();
	}
	
	/**
	 * Validates an input string against the given pattern.
	 * @param input
	 * @param pattern
	 * @return true if a match is found and false otherwise
	 */
	public static boolean validate(String input, String pattern){
		return Context.validate(input, Pattern.compile(pattern));
	}
	
	/**
	 * Checks that the given input is an integer.
	 * @param input
	 * @return
	 */
	public static boolean validateInteger(String input){
		return Context.validate(input, Context.integerPattern);
	}
	
}

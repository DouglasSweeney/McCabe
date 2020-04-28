package main.java.debug;

import java.util.List;

import main.java.main.Options;
import main.java.scanner.Token;

/**
 * To use this class.
 * Set the define in the "java -Ddebug.options -jar filename.jar".
 * Calling function is "Debug.println(category, message);".
 *  
 * @author dks
 *
 */
public class Debug {
	private final static Integer MAX_TOKENS_TO_PRINT = 500;
	
	private static Boolean useExceptions;
	private static Boolean oneFilename;
	private static String  methodName;
	private static String  directoryOrFilename;
	
	/**
	 * Set some properties.
	 * The properties coorespond to those in the Categories.java file
	 * 
	 */
	public static void setProperties() {
		final String value = "1";
		
		System.setProperty("debug." + Categories.OPTIONS.toString().toLowerCase(), value);
		System.setProperty("debug." + Categories.TOKENS.toString().toLowerCase(), value);
		System.setProperty("debug." + Categories.MCCABE.toString().toLowerCase(), value);
		System.setProperty("debug." + Categories.BRACES.toString().toLowerCase(), value);
		System.setProperty("debug." + Categories.INPUT_METHODS.toString().toLowerCase(), value);
		System.setProperty("debug." + Categories.INTERNAL_METHODS.toString().toLowerCase(), value);
	}
	
	private static void printList(String methodName, List<Token> list) {
		System.out.println("methodName: " + methodName);
		if (list.size() < MAX_TOKENS_TO_PRINT) {
			for (Token item : list) { // get the overall Mccabe Complexity Factor
				System.out.println("Token: " + item.enumeration);
			}
     	}
    }
	
	private static void printList(List<Token> list) {
		if (list.size() < MAX_TOKENS_TO_PRINT) {
			for (Token item : list) { // get the overall Mccabe Complexity Factor
				System.out.println("Token: " + item.enumeration);
			}
     	}
    }
	
	/** 
	 * No output if everything is OK.
	 * 
	 * @param options the options to check against.
	 */
	private static void compareToFirstCall(Options options) {
		if (directoryOrFilename == null) {
			useExceptions = options.getUseExceptions();
			oneFilename = Options.isComputeOnlyOneMethod();
			methodName = Options.getMethodName();
			directoryOrFilename = options.getMcCabeDirectory();
		}
		else {
			if (useExceptions != options.getUseExceptions() ||
				oneFilename != Options.isComputeOnlyOneMethod() ||
				!methodName.equals(Options.getMethodName()) ||
				!directoryOrFilename.equals(options.getMcCabeDirectory()) 
			   ) {
					System.err.println("Debug found that options are different.");
			}
//			else {
//				System.out.println("Debug found that the options are the same");
//			}
		}
	}
	
	private static boolean isEnabled(String category) {
		return System.getProperty("debug." + category.toLowerCase()) != null;
	}
	
	public static void println(Categories category, String message) {
		if (isEnabled(category.toString())) {
			System.out.println(message);
		}
	}
	
	public static void println(Categories category, Options options, String message) {
		if (isEnabled(category.toString())) {
			if (category.equals(Categories.OPTIONS))
				compareToFirstCall(options);
			else
				System.out.println(message);
		}
	}
	
	public static void println(Categories category, String methodName, List<Token> tokenList, String message) {
		if (isEnabled(category.toString())) {
			if (category.equals(Categories.TOKENS))
				printList(methodName, tokenList);
			else
				System.out.println(message);
		}
	}
	public static void println(Categories category, List<Token> tokenList, String message) {
		if (isEnabled(category.toString())) {
			if (category.equals(Categories.TOKENS))
				printList(tokenList);
			else
				System.out.println(message);
		}
	}
}

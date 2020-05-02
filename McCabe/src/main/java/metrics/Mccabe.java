//
// File:    Mccabe.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney 
//
package main.java.metrics;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import main.java.debug.Categories;
import main.java.debug.Debug;
import main.java.main.Options;
import main.java.main.TokenList;
import main.java.main.Utils;
import main.java.scanner.Scanner;
import main.java.scanner.Token;
import main.java.scanner.TokenEnum;

/**
 * Compute the McCabe complexity factor.
 * 
 * @author dks
 * @since 1.0
 */
public class Mccabe extends TokenList {
	List<String> classList = new ArrayList<>();
	static List<MccabeNode> methods = new ArrayList<>();
    Integer mccabeComplexityFactor;
	Utils utils = new Utils();
    String previousMethodName = "";

    /**
     * The constructor - checks that some class variables were initialized.
     */
	public Mccabe() {
		assert classList != null : Mccabe.class.getCanonicalName() +
                "constructor: classList = null";
		assert methods != null : Mccabe.class.getCanonicalName() +
				                "constructor: method = null";
	}
	
	/**
	 * Count the braces.
	 * 
	 * @param methodName keeps the output smaller when debugging
	 * @param token the current token
	 * @param braces the current count of the braces
	 * @param list debugging - the list of tokens
	 * 
	 * @return the current count of the braces
	 */
	private Integer countBraces(String methodName, Token token, Integer braces, List<Token> list) {
		if (token.enumeration != TokenEnum.LBRACE &&
			token.enumeration != TokenEnum.RBRACE)
			return braces;
		
		if (!methodName.equals("") && !methodName.equals(previousMethodName)) {
			previousMethodName = methodName;
		    Debug.println(Categories.INPUT_METHODS, Mccabe.class.getCanonicalName() + " " + 
				      "countBraces() methodName: " + methodName + "()");
			
		}
		
			if (token.enumeration == TokenEnum.LBRACE) {
				braces++;
			}
			else {
				if (token.enumeration == TokenEnum.RBRACE) {
					braces--;
				}
			}

		if (braces < 0) {
			Debug.println(Categories.BRACES, Mccabe.class.getCanonicalName() + " " + 
						  "methodName: <" + methodName + "> countBraces() " + braces.toString());
	    	Debug.println(Categories.TOKENS, list, Mccabe.class.getCanonicalName() + " ");
	 	}
		
		return braces;
	}
	
	/**
	 * Counts each "return" as 1 except if the "return" is on the last line.
	 * 
	 * @param methodName debugging
	 * @param procedureList a list of tokens just for the method
	 */
    private void returns(String methodName, List<Token> procedureList) {
    	Integer procedureTokenIndex = 0;
    	Token token = procedureList.get(procedureTokenIndex);
    	Integer braces = 1;
    	Debug.println(Categories.INTERNAL_METHODS, Mccabe.class.getCanonicalName() + " " + 
			      "returns()");
    	Debug.println(Categories.TOKENS, procedureList, Mccabe.class.getCanonicalName() + " " );
	    Debug.println(Categories.INPUT_METHODS, Mccabe.class.getCanonicalName() + " " + 
			      "returns() methodName: " + methodName + "()");

    	while (token.enumeration != TokenEnum.RETURN &&
    		   procedureTokenIndex < procedureList.size()-1) {
    		
			braces = countBraces(methodName, token, braces, procedureList);
			
 		    if (procedureTokenIndex < procedureList.size()-1) {
   		       procedureTokenIndex++;   
   		       token = procedureList.get(procedureTokenIndex);
 		    }
    	}
    		
    	if (token.enumeration == TokenEnum.RETURN) {
        	while (token.enumeration != TokenEnum.SEMICOLON && 
        		   token.enumeration != TokenEnum.EOF) {
        		procedureTokenIndex++;
        	    token = procedureList.get(procedureTokenIndex);
        	}
        	
    		procedureTokenIndex++;
    	    token = procedureList.get(procedureTokenIndex);
        	if (token.enumeration != TokenEnum.RBRACE) {
        		braces--;
        		if (braces == 1)
        			mccabeComplexityFactor++;
        	}
    	}
    }
    
	/**
	 * Counts each if/else/case/default statements as 1.
	 * 
	 * @param methodName debugging
	 * @param procedureList a list of tokens just for the method
	 */
    private void selections(String methodName, List<Token> procedureList) {
    	Debug.println(Categories.INTERNAL_METHODS, Mccabe.class.getCanonicalName() + " " + 
			      "selections()");
    	Debug.println(Categories.TOKENS, procedureList, Mccabe.class.getCanonicalName() + " ");
	    Debug.println(Categories.INPUT_METHODS, Mccabe.class.getCanonicalName() + " " + 
			      "selections() methodName: " + methodName + "()");
    	Token token = null;
    	
    	for (int i=0;i < procedureList.size(); i++) {
    		token = procedureList.get(i);
    		if (token.enumeration == TokenEnum.IF ||
    			token.enumeration == TokenEnum.ELSE ||
    			token.enumeration == TokenEnum.CASE ||
    			token.enumeration == TokenEnum.DEFAULT) {
    		   mccabeComplexityFactor++;
    		}
    	}
    }
       
    /** 
     * Increment the McCabe complexity factor.
     * 
     * @param size the number of times to increment it
     */
    private void incrementComplexityFactor(Integer size) {
		for (int i=0; i<size; i++) {
   			mccabeComplexityFactor++;	
		}
    }
    
	/**
	 * Counts each for/break/continue/do-while statements as 1.
	 * 
	 * @param methodName debugging
	 * @param procedureList a list of tokens just for the method
	 */
    private void loops(String methodName, List<Token> procedureList) {
    	Debug.println(Categories.INTERNAL_METHODS, Mccabe.class.getCanonicalName() + " " + 
			      "loops()");
    	Debug.println(Categories.TOKENS, procedureList, Mccabe.class.getCanonicalName() + " ");
	    Debug.println(Categories.INPUT_METHODS, Mccabe.class.getCanonicalName() + " " + 
			      "loops() methodName: " + methodName + "()");
    	
    	Token token = null;
    	Integer braces = 0;
    	Integer doStatements = 0;
    	Integer whileStatements = 0;
    	Integer doWhileStatements = 0;
    	List<Integer> whileLineNumbers = new ArrayList<Integer>();
    	
    	for (int i=0;i < procedureList.size(); i++) {
    		token = procedureList.get(i);
    		
    		braces = countBraces(methodName, token, braces, procedureList);
    		
    		if (token.enumeration == TokenEnum.FOR ||
    			token.enumeration == TokenEnum.BREAK ||
    			token.enumeration == TokenEnum.CONTINUE) {
    		    mccabeComplexityFactor++;
    		}
    		
   			if (token.enumeration == TokenEnum.WHILE) {
   				whileLineNumbers.add(token.lineNumber);
   			}
   		
   			if (token.enumeration == TokenEnum.DO) {
   				doStatements++;
   			}
    	}
    
   		if (doStatements >= 1) {
   			doWhileStatements = doStatements;
  			incrementComplexityFactor(doWhileStatements);
  			whileStatements = whileLineNumbers.size()-doStatements;
  			incrementComplexityFactor(whileStatements);
   		}   		
   		else {
   			incrementComplexityFactor(whileLineNumbers.size());
   		}
   	}
    
	/**
	 * Counts each &&/||/? and possibly count the colon statement as 1.
	 * 
	 * @param methodName debugging
	 * @param procedureList a list of tokens just for the method
	 */
    private void operators(String methodName, List<Token> procedureList) {
    	Debug.println(Categories.INTERNAL_METHODS, Mccabe.class.getCanonicalName() + " " + 
			      "operators()");
	    Debug.println(Categories.INPUT_METHODS, Mccabe.class.getCanonicalName() + " " + 
			      "operators() methodName: " + methodName + "()");
    	Debug.println(Categories.TOKENS, procedureList, Mccabe.class.getCanonicalName() + " ");
    	Token token = null;
    	Token token2 = null;
    	
    	if (methodName.equals("main")) {
    	    Debug.println(Categories.INPUT_METHODS, "**********************" + Mccabe.class.getCanonicalName() + " " + 
  			      "operators() methodName: " + "main FOUND" + "()");
    	    Debug.println(Categories.BRACES, Mccabe.class.getCanonicalName() + " " + 
				  "methodName: <" + methodName + ">");
    	}
    		
       	for (int i=0;i < procedureList.size(); i++) {
    		token = procedureList.get(i);
       		if (token.enumeration == TokenEnum.AMPERSAND_AMPERSAND ||
        		token.enumeration == TokenEnum.OROR) {
        		   mccabeComplexityFactor++;
       		}
         	
   			if (token.enumeration == TokenEnum.QUESTION_MARK) {
     		   mccabeComplexityFactor++;
   			}
   			
       		if (token.enumeration == TokenEnum.COLON ) {
        		token = procedureList.get(i-1);
        		token2 = procedureList.get(i-2);
          		if (token.enumeration != TokenEnum.DEFAULT &&
                    token2.enumeration != TokenEnum.CASE ) {
    				mccabeComplexityFactor++;
           		}
       		}
       	}
    }
    
	/**
	 * Counts each catch/finally/throw statements as 1.
	 * 
	 * @param methodName debugging
	 * @param procedureList a list of tokens just for the method
	 */
    private void exceptions(String methodName, List<Token> procedureList) {
    	Debug.println(Categories.INTERNAL_METHODS, Mccabe.class.getCanonicalName() + " " + 
			      "exceptions()");
    	Debug.println(Categories.TOKENS, procedureList, Mccabe.class.getCanonicalName() + " ");
	    Debug.println(Categories.INPUT_METHODS, Mccabe.class.getCanonicalName() + " " + 
			      "operators() methodName: " + methodName + "()");
    	Token token = null;
    	
    	for (int i=0;i < procedureList.size(); i++) {
    		token = procedureList.get(i);
    		if (token.enumeration == TokenEnum.CATCH ||
    			token.enumeration == TokenEnum.FINALLY ||
    			token.enumeration == TokenEnum.THROW) {
    		   mccabeComplexityFactor++;
    		}
    	}
    }
    
	/**
	 * Counts some statements for the McCabe complexity Factor. The exceptions is a 
	 * special case - one train of thought is that its good programming style to have
	 * exception tests in your code.
	 * 
	 * @param methodName debugging
	 * @param procedureList a list of tokens just for the method
	 * @param useExceptions count the exception keywords towards the complexity factor
	 */
    private void computeMcCabeComplexityFactor(String methodName, List<Token> procedureList, 
    		                                   boolean useExceptions) {
	    Debug.println(Categories.INPUT_METHODS, Mccabe.class.getCanonicalName() + " " + 
			      "computeMcCabeComplexityFactor() methodName: " + methodName + "()");
        returns(methodName, procedureList);
        selections(methodName, procedureList);
        loops(methodName, procedureList);
        operators(methodName, procedureList);
        if (useExceptions)
        	exceptions(methodName, procedureList);
        
    }

    /**
     * Compute the new index into the file token list. Believe this isn't used.
     * 
     * @param currentTokenIndex the current value of the index
     * @param procedureList the list for a single method
     * 
     * @return the new value of the index
     */
//    private Integer computeCurrentTokenIndex(Integer currentTokenIndex,
//    		                                 List<Token> procedureList) {
//        currentTokenIndex += procedureList.size();
//        
//        return currentTokenIndex;
//    }
    
    /**
     * Determine the methodName and the complexity factor of each method.
     * 
     * @param filename debugging
     * @param list the token list of the file
     * @param currentTokenIndex the index into the list
     * @param className the class of the file
     * @param classNumber the number of classes in the file
     * @param useExceptions count the exceptions towards the McCabe complexity factor
     */
	private void findMethodsAndComplexityFactor(String filename, List<Token> list, 
			                                    Integer currentTokenIndex,
			                                    String className, Integer classNumber,
			                                    boolean useExceptions) {
		List<Token> procedureList = new ArrayList<>();
		Token token = list.get(currentTokenIndex);
		String methodName = "";
		Token token2 = null;
		Integer braces = 0;
		
		while (token.enumeration != TokenEnum.EOF) {
			
			braces = countBraces(methodName, token, braces, list);
			

			// look for '(' inside a class
			if (token.enumeration == TokenEnum.LPAREN && 
					classNumber >= 1 && 
					braces == 1) {
				
				// remember the procedure name
     			token2 = list.get(currentTokenIndex - 1);
				if (token2.enumeration == TokenEnum.IDENTIFIER) {
					methodName = token2.string;
					// look for type
	     			token = list.get(currentTokenIndex - 2);
					if (token.enumeration == TokenEnum.GT || // return type of a procedure: i.e.List<Token>
						token.enumeration == TokenEnum.IDENTIFIER ||
					    token.enumeration == TokenEnum.BYTE ||
						token.enumeration == TokenEnum.CHAR ||
						token.enumeration == TokenEnum.DOUBLE ||
						token.enumeration == TokenEnum.BOOLEAN ||
						token.enumeration == TokenEnum.FLOAT ||
						token.enumeration == TokenEnum.INT ||
						token.enumeration == TokenEnum.LONG ||
						token.enumeration == TokenEnum.SHORT ||
						token.enumeration == TokenEnum.VOID ||
						token.enumeration == TokenEnum.PUBLIC) {
						
						// Ensure the token is not qualified
//						if (token.enumeration == TokenEnum.IDENTIFIER) {
//			     			token3 = list.get(currentTokenIndex - 3);
//			     			if (token3.enumeration != TokenEnum.PERIOD) {
//								methodName = token2.string;
								
			     				// Set the mcCabe Complexity Factor to 1 for a new method
					        	mccabeComplexityFactor = 1;
					        	procedureList = utils.getProcedureTokens(list, currentTokenIndex);
					        	Debug.println(Categories.INPUT_METHODS, Mccabe.class.getCanonicalName() + " " + 
					        			"findMethodsAndComplexityFactor() "
					        			+ "methodName: " + methodName + "()");
					        	Debug.println(Categories.TOKENS, methodName, procedureList, Mccabe.class.getCanonicalName() + " " + 
					        			"findMethodsAndComplexityFactor()");
					        	if (Utils.throwsToken)
					        		if (useExceptions)
					        			mccabeComplexityFactor++;
	
					        	// compute the McCabe Complexity Factor
					        	computeMcCabeComplexityFactor(methodName, procedureList, useExceptions);

					        	// Optimization - readjust the token pointer for each input procedure. Rather 
					        	//                than allow the while() loop to process extra tokens.
//					        	currentTokenIndex = computeCurrentTokenIndex(currentTokenIndex, procedureList);

					        	MccabeNode mccabeNode = new MccabeNode(filename, 
					        								  className,
 						                                      methodName, 
 							                                  token2.lineNumber, 
 							                                  mccabeComplexityFactor);
					        	methods.add(mccabeNode);
//					        }
//					    }
					}
				}
			}
			
			currentTokenIndex = currentTokenIndex + 1;
			token = list.get(currentTokenIndex);
		}
	}
	
	private double getTotalComplexity(String filename) {
		double total = 0;
		
    	for (MccabeNode item : methods) { // get the file Mccabe Complexity Factor
    		if (item.getFilename().equals(filename)) {
    			total += item.getMccabeComplexityFactor();
    		}
   	    }
    	
		return total;
	}
	
	/**
	 * Determine the complexity factor for a class.
	 * 
	 * @param filename debugging
	 * @param list the file list of tokens
	 * @param options arguments specified on the command line
	 */
    public void compute(String filename, List<Token> list, Options options) {
       	Debug.println(Categories.OPTIONS, options, Mccabe.class.getCanonicalName() + " " + 
			      "compute()");
    	Debug.println(Categories.INTERNAL_METHODS, Mccabe.class.getCanonicalName() + " " + 
			      "compute()");
    	Integer currentTokenIndex = 0;
    	Integer classNumber = 0;
    	String  className = "";
    	Token   token;
    	boolean useExceptions = options.getUseExceptions();
    	
    	token = list.get(currentTokenIndex);
    	while (token.enumeration != TokenEnum.EOF) {
    		currentTokenIndex++;
    	    token = list.get(currentTokenIndex);
    	
 	    if (token.enumeration == TokenEnum.CLASS ||
    	        token.enumeration == TokenEnum.ENUM) {
    	    	classNumber = 1;
    	    	
    	    	currentTokenIndex++;
    	    	token = list.get(currentTokenIndex);
    	    	if (token.enumeration == TokenEnum.IDENTIFIER) {
                    className = token.string; // get the class name; IDENTIFIER
    	    	    classList.add(className);
    	    	}
    	    	
    	    	currentTokenIndex++;
    	    	findMethodsAndComplexityFactor(filename, list, currentTokenIndex, className, 
    	    								   classNumber, useExceptions);
    	    }
    	}

//    	if (Options.isComputeOnlyOneMethod()) {    		
//    		String targetName = Options.getMethodName();
//          	System.out.print("method: <" + targetName + ">  ");
//          	System.out.println("Complexity Factor: " + getMethodComplexityFactor(targetName));
//    	}
    	
    	Debug.println(Categories.MCCABE, Mccabe.class.getCanonicalName() + " " + 
			      "compute() " + "McCabe total: " + getTotalComplexity(filename));

    }
    
    /** 
     * Print out information about the file. 
     */
    public void debugging_print() {
    	double total = 0;
    	double mccabeComplexityFactor = 0;
    	
     	System.out.println("Number of classes: " + classList.size());
     	
     	for (String item : classList) { // get the overall Mccabe Complexity Factor
     		System.out.println("classname: " + item + " ");
     	}
     	
     	System.out.println("Number of methods: " + methods.size());
      	for (MccabeNode item2 : methods) { // get the method Mccabe Complexity Factor
     		System.out.print("classname: " + item2.getClassName() + " ");
     		System.out.print("methodName: " + item2.getMethodName() + "(" + item2.getLineNumber() + ") ");
    		System.out.println(" mcCabeComplexityFactor: " + item2.getMccabeComplexityFactor());
    	}    	
   
   	    for (MccabeNode item2 : methods) { // get the overall Mccabe Complexity Factor
   			total += item2.getMccabeComplexityFactor();
   	    }
   	    
   	    if (methods.size() > 0) {
   	    	mccabeComplexityFactor = total/methods.size();
   	    	System.out.println("McCabe Complexity Factor: " + mccabeComplexityFactor);
   	    }
    }
    
    /**
     * Print out non-verbose information about the file
     */
    public void print() {
    	double complexityFactor;
    	double total = 0;
    	
    	for (MccabeNode item : methods) { // get the overall Mccabe Complexity Factor
   			total += item.getMccabeComplexityFactor();
   	    }
    	
    	if (methods.size() > 0) {
    		complexityFactor = (double)(total/methods.size());
    		System.out.println("McCabe Complexity Factor: " + complexityFactor);
    	}
    	
	    System.out.println("Number of classes: " + classList.size());
	    System.out.println("Number of methods: " + methods.size());
	}
    
    /**
     * Determine the file McCabe complexity factor.
     * 
     * @return the McCabe complexity factor of the file
     */
    public double getOverallComplexityFactor() {
    	double complexityFactor;
    	double total = 0;
    	
    	for (MccabeNode item : methods) { // get the overall Mccabe Complexity Factor
   			total += item.getMccabeComplexityFactor();
   	    }
  	 	complexityFactor = (double)(total/methods.size());
  	 	
  	 	return complexityFactor;
	}
    
    /** 
     * Get the McCabe complexity factor for a method.
     * 
     * @param method the method 
     * 
     * @return the complexity factor
     */
    public Integer getMethodComplexityFactor(String method) {
    	Integer complexityFactor;
    	
		complexityFactor = 0;
    	for (MccabeNode item : methods) { // get the overall Mccabe Complexity Factor
    		if (item.getMethodName().equals(method))
    			complexityFactor = item.getMccabeComplexityFactor();
   	    }
  	 	
  	 	return complexityFactor;
	}
    
    /**
     * Get the number of methods
     * 
     * @return the number of methods
     */
    public Integer getNumberOfClasses() {
   	 	return classList.size();
    }
    
    /**
     * Get the number of methods
     * 
     * @return the number of methods
     */
    public Integer getNumberOfMethods() {
   	 	return methods.size();
    }
    
    /**
     * Get the McCabe complexity factor of a method.
     * 
     * @param methodIndex the index into the methods list
     * 
     * @return the complexity factor
     */
    public Integer getMethodComplexityFactor(int methodIndex) {
    	MccabeNode item;
    	
    	if (methodIndex < getNumberOfMethods()) {
    		item = methods.get(methodIndex);
    		return item.getMccabeComplexityFactor();
    	}
    	else
    		return 0;
	}
    
    /**
     * Determine the method name.
     * 
     * @param methodIndex the index into the methods list
     * 
     * @return the method name
     */
    public String getMethodName(int methodIndex) {
    	MccabeNode item;
    	
    	if (methodIndex < getNumberOfMethods()) {
    		item = methods.get(methodIndex);
    		return item.getMethodName();
    	}
    	else
    		return "";
	}
    
    /**
     * Set some options to test out this class
     * 
     * @return the arguments to be tested
     */
    private static Options setOptions() {
		String[] args = new String[4];
		args[0] = "-exceptions";
		args[1] = "-m";
		args[2] = "temp.dat";
		args[3] = "/home/dks/git/McCabe_v1/McCabe/src/test/TestInput.java";
    	Options options = new Options(args);

    	return options;
    }
    
    /**
     * Create a scanner.
     * 
     * @param options for the directory of java files
     * 
     * @return the scanner
     */
    private static Scanner createScanner(Options options) {
    	Scanner scanner = null;
    	
		try {
			scanner = new Scanner(options.getMcCabeDirectory());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
    	return scanner;
    }
    
    /**
     * Read a file of tokens.
     * 
     * @param scanner the scanner to use to get the tokens
     * 
     * @return a list of tokens
     */
    private static TokenList readTokens(Scanner scanner) {
	   	Token token = null;
    	TokenList tokenList = new TokenList();
	   	
		token = scanner.getNextToken();
		while  (token.enumeration != TokenEnum.EOF) {
   	   		System.out.println(token.string + ": " + token.enumeration + "(" + token.lineNumber + ")");
   	   		tokenList.add(token);
   	   		token = scanner.getNextToken();
		}
		tokenList.add(token);
		
		return tokenList;
    }
    
    /**
     * Prints out the methods to the console
     * 
     * @param mcCabe the methods that were computed
     */
    private static void printMethods(Mccabe mcCabe) {
		Integer size = mcCabe.getNumberOfMethods();
		for (int i=0; i<size; i++) {
			System.out.println("Class Name: " + (methods.get(i)).getClassName() +
					           " Method Name: " + mcCabe.getMethodName(i) + 
					           " Mccabe Complexity Factor: " + 
					                         mcCabe.getMethodComplexityFactor(i));
		} 	
    }
    
    /**
     * The main() routine to test out this file.
     * 
     * @param args22222 are not used
     */
	public static void main(String[] args22222) {
		Debug.setProperties();
 		
		Mccabe mcCabe = new Mccabe();
    	TokenList tokenList = new TokenList();
    	Scanner scanner = null;
	
		Options options = setOptions();
		
		scanner = createScanner(options);
		if (scanner != null) {
  		 	
			tokenList = readTokens(scanner);
			
			mcCabe.compute("TestInput.java", tokenList.getList(), options);
			mcCabe.debugging_print();
			mcCabe.print();
		
            printMethods(mcCabe);
            
			System.out.println("Method Name: " + "main" + 
		                   " Mccabe Complexity Factor (SB 10): " + 
				            mcCabe.getMethodComplexityFactor("main"));
		}	
	}
}

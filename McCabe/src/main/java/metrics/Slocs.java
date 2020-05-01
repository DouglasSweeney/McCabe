//
// File:    Slocs.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney 
//
package main.java.metrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.java.debug.Categories;
import main.java.debug.Debug;
//import main.java.main.Options;
import main.java.main.TokenList;
import main.java.scanner.Scanner;
import main.java.scanner.Token;
import main.java.scanner.TokenEnum;

/**
 * Keep track of the source lines of code (SLOC) for the file(s).
 * 
 * @author dks
 * @since 1.0
 */
public class Slocs extends TokenList {
	static TokenList tokenList = new TokenList();
	
 	static List<SlocNode> slocList = new ArrayList<>();;
	
 	/**
 	 * The constructor.
 	 */
	public Slocs() {
		assert tokenList != null : Slocs.class.getCanonicalName() + 
                "constructor: tokenList = null";
		assert slocList != null : Slocs.class.getCanonicalName() + 
                "constructor: slocList = null";
	}

	/**
	 * Clear the source lines of code counter
	 */
	public void clearList() {
		slocList.clear();
	}
	/**
	 * Keep track of the SLOCs - watch for duplicates
	 * 
	 * @param filename the filename of the processed file
	 * @param list the token list of the file
	 */
	public void compute(String filename, List<Token> list) {
    	Debug.println(Categories.INTERNAL_METHODS, Slocs.class.getCanonicalName() + " " + 
			      "compute()");
		Integer lineNumbers = 0;
		Integer currentTokenIndex = 0;
		
		if (list != null) {
			Token token = list.get(currentTokenIndex);		
			while (token.enumeration != TokenEnum.EOF) {
				lineNumbers = 0;
				for (int i=0; i<slocList.size(); i++) {
					SlocNode item = slocList.get(i);
                
					if (item.filename.equals(filename)) {
						lineNumbers++;
				    }
				}
				
				if (token.lineNumber > lineNumbers) {
					SlocNode slocNode = new SlocNode(filename, token.lineNumber);
				
					slocList.add(slocNode);
				}
				
				currentTokenIndex = currentTokenIndex + 1;
				token = list.get(currentTokenIndex);
			}
		}
		else {
			System.err.println(Slocs.class.getCanonicalName() + 
					           "The list is null.");
		}
	}
	
	public int getNumberOfSlocs(String filename) {
		int slocs = 0;
		
		if (filename != null) { 
			if (filename.endsWith(".java")) {
				for (int i=0; i < slocList.size(); i++) {
					SlocNode item = slocList.get(i);
					if (item.filename.equals(filename)) {
						slocs++;
					} 
				}
			} 
			else {
				slocs = slocList.size();
			}	
		}
		
		return slocs;
	}

	/**
	 * Print verbosely information about the SLOCs
	 */
	public void debugging_print() {
		Integer counter = 0;
		
		for (SlocNode sloc : slocList) {
			File file = new File(sloc.filename);
			System.out.println("slocList(" + counter + ")" + file.getName() + " " + sloc.lineNumber);
			counter++;
		}
		System.out.println("slocList.size(): " + slocList.size());
	}
	
	/**
	 * Print just number of SLOCs.
	 */
	public void print() {
	    System.out.println("Source Lines of Code: " + slocList.size());   	
	}
	
	/** 
	 * Loop thru the file getting all the tokens
	 * 
	 * @param directoryAndFilename the path and filename to be processed
	 */
    private void getTokensFromFile(String directoryAndFilename) {
		
	    Scanner scanner = null;
        Token token = null;
	    
 		try {
			scanner = new Scanner(directoryAndFilename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
  			
 		token = new Token(TokenEnum.NONE, 0);
 		while  (token.enumeration != TokenEnum.EOF) {
 			token = scanner.getNextToken();
 			tokenList.add(token);
 		}
// 		tokenList.print(tokenList.getList());
    }
   
    /**
     * Process each token for metrics.
     * 
     * @param filename that is being processed
     * @param processExceptions compute exception keywords
     */
    private void processTokens(String filename, boolean processExceptions) {
		compute(filename, tokenList.getList());
    }
    
    /** 
     * Test out the class.
     * 
     * @param args The supplied arguments to this routine.
     */
	public static void main(String[] args) {
    	Slocs slocs = new Slocs();
    	
		final boolean PRINT_JUST_FILENAME = false;
//		Options options = new Options(args);
	 		
		// Pass in just one file
    	if (args[0].endsWith(".java")) {
			System.out.println(args[0]);
    		slocs.getTokensFromFile(args[0]);
    		slocs.processTokens(args[0], false);
    	}
    	else { // else pass in a directory
 		    try {
 		    	Stream<Path> walk = Files.walk(Paths.get(args[0]));

 		    	List<String> result = walk.filter(Files::isRegularFile)
 		    				.map(x -> x.toString()).collect(Collectors.toList());

 		    	for (String filename : result) {
 		    		if (PRINT_JUST_FILENAME) {
  		    			System.out.println(filename);
 		    		}
 		    		else {
 		    			if (filename.endsWith(".java")) { // make sure its a .java file;
// 		    				System.out.println(filename); // &
 		    				slocs.getTokensFromFile(filename);  // not .c/.txt etc
 		    				slocs.processTokens(filename, false);
 		    				tokenList.clear();
 		    			}
		    		}
 		    	}
 		    	walk.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	
    	if (!PRINT_JUST_FILENAME) {
    		slocs.debugging_print();
    	}
	}
}

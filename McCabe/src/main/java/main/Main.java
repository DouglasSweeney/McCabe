//
// File:    Main.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney 
//
package main.java.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.java.debug.Categories;
import main.java.debug.Debug;
import main.java.scanner.Scanner;
import main.java.scanner.Token;
import main.java.scanner.TokenEnum;

/**
 * Defines the main entry point and starts to process the command line options.
 *  
 * @author dks
 * @since  1.0
 *
 */
public class Main {
	TokenList tokenList = new TokenList();
	Metrics metrics = new Metrics(); 

	/**
	 * Create the scanner.
	 * 
	 * @param directoryAndFilename the input file for the scanner
	 * 
	 * @return the scanner that was created
	 */
	private Scanner createScanner(String directoryAndFilename) {
	    Scanner scanner = null;
	    
	   	try {
    		scanner = new Scanner(directoryAndFilename);
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
	    }

		return scanner;
	}
	
	/**
	 * Build a list of all the tokens.
	 * 
	 * @param scanner get the next token from the scanner
	 */
	private void buildTokenList(Scanner scanner) {
		Token token;
		
	    token = new Token(TokenEnum.NONE, 0);
	    while  (token.enumeration != TokenEnum.EOF) {
	    	token = scanner.getNextToken();
	    	tokenList.add(token);;
	    }
	}
	
	/**
	 * Builds the scanner and gets a list of the tokens.
	 * 
	 * @param directoryAndFilename
	 */
    private void getTokensFromFile(String directoryAndFilename) {
		
	    Scanner scanner = null;
	    
   		scanner = createScanner(directoryAndFilename);
   		
   		buildTokenList(scanner);
   		
	    //tokenList.print(tokenList.getList());
	}

    /**
     * Process the list of tokens.
     * 
     * @param filename for debugging
     * @param options  the user options of the program
     */
    private void processTokens(String filename, Options options) {    	
    	Debug.println(Categories.OPTIONS, options, Main.class.getCanonicalName() + " " + 
			      "processTokens()");
    	Debug.println(Categories.INTERNAL_METHODS, Main.class.getCanonicalName() + " " + 
			      "processTokens()" + " filename: " + filename);
		metrics.compute(filename, tokenList.clone(), options);
    }
    
    /**
     * A single .java file was passed in.
     * 
     * @param options the user options of the program
     */
    private void runOneFile(Options options) {
    	Debug.println(Categories.OPTIONS, options, Main.class.getCanonicalName() + " " + 
    			"runOneFile()");
    	Debug.println(Categories.INTERNAL_METHODS, Main.class.getCanonicalName() + " " + 
    			"runOneFile()");
		File file = new File(options.getMcCabeDirectory());
		
		if (file.exists()) {
		//			System.out.println(options.getMcCabeDirectory());
			getTokensFromFile(options.getMcCabeDirectory());
			processTokens(options.getMcCabeDirectory(), options);
		}
		else {
			System.err.println(Main.class.getCanonicalName() + 
    			" main(): File doesn't exist: " + file);
		}
	
    }
    
    
    /**
     * A directory of .java files was passed in.
     * 
     * @param PRINT_JUST_FILENAME debugging; prints the filenames versus processing the file.
     * @param options the user options of the program
     */
    private void runDirectory(boolean PRINT_JUST_FILENAME, Options options) {
       	Debug.println(Categories.OPTIONS, options, Main.class.getCanonicalName() + " " + 
			      "runDirectory()");
       	Debug.println(Categories.INTERNAL_METHODS, Main.class.getCanonicalName() + " " + 
			      "runDirectory()");
		File dir = new File(options.getMcCabeDirectory());
		
		if (dir.exists()) {
			try {
				Stream<Path> walk = Files.walk(Paths.get(options.getMcCabeDirectory()));

				List<String> result = walk.filter(Files::isRegularFile)
						.map(x -> x.toString()).collect(Collectors.toList());

				for (String filename : result) {
					if (PRINT_JUST_FILENAME) {
						System.out.println(filename);
					}
					else {
						if (filename.endsWith(".java")) { // not a .c/.txt file;
//	    					System.out.println(filename); 
							getTokensFromFile(filename);  
							processTokens(filename, options);
							tokenList.clear();
						}
					}
				}
				walk.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (!PRINT_JUST_FILENAME) {
				System.out.println();
				metrics.print();
			}
		}
		else {
			System.err.println(Main.class.getCanonicalName() + 
				           " main() directory doesn't exist (" +
		                   dir.getName() + ")");
		}    	
    }
    
    /** 
     * Process a single .java file or a directory of .java files.
     * 
     * @param PRINT_JUST_FILENAME debugging; prints the filenames versus processing the file
     * @param options the user specified options on the command line
     */
    public void run(boolean PRINT_JUST_FILENAME, Options options) {
 		
    	if (options.getMcCabeDirectory().endsWith(".java")) {
    		runOneFile(options);
    	}
    	else {
    		runDirectory(PRINT_JUST_FILENAME, options);
		}    	
    }
    
    /**
     * The main entry point of the McCabe application program.
     * 
     * @param args what the user has specified on the command line
     */
 	public static void main(String[] args) {
// 		System.out.println(System.getProperty("sun.java.command"));
 		
 		Debug.setProperties();
 		
 		Main main = new Main();
 		
		final boolean PRINT_JUST_FILENAME = false;
		
		assert args.length > 0 : Main.class.getCanonicalName() + " main(): No input directory";
		
		Options options = new Options(args);		
	        
		main.run(PRINT_JUST_FILENAME, options);
	}
}

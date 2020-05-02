//
// File:    Tests.java
// Created: 5/1/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     5/1/2020        Douglas Sweeney 
//
package main.java.metrics;

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
import main.java.scanner.Scanner;
import main.java.scanner.Token;
import main.java.scanner.TokenEnum;

/**
 * Keep track of the @Test that were encountered.
 *  
 * @author dks
 * @since  1.0
 *
 */
public class Tests {
    static List<String> testList = null;
    
    /**
     * The constructor.
     */
    public Tests() {
        testList = new ArrayList<>();
        assert testList != null : Tests.class.getCanonicalName() + 
                "constructor: testList = null";
    }

    /**
     * Keep track of the number of tests.
     * 
     * @param filename the filename of the processed file
     * @param list the token list of the file
     */
    public void compute(String filename, List<Token> list) {
        Debug.println(Categories.INTERNAL_METHODS, Tests.class.getCanonicalName() + " " + 
                  "compute()");
        Integer lineNumbers = 0;
        Integer currentTokenIndex = 0;
        
        if (list != null) {
            Token token = list.get(currentTokenIndex);      
            while (token.enumeration != TokenEnum.EOF) {
                if (token.enumeration == TokenEnum.TEST_ANNOTATION) {
                    testList.add(token.string);
                }
                
                currentTokenIndex = currentTokenIndex + 1;
                token = list.get(currentTokenIndex);
            }
        }
        else {
            System.err.println(Tests.class.getCanonicalName() + 
                               "The list is null.");
        }
    }
    
    /**
     * Print just number of @Tests.
     */
    public int getNumberOfTests() {
        return testList.size();
    }

    /**
     * Print just number of @Tests.
     */
    public void debugging_print() {
        System.out.println("Number of tests: " + getNumberOfTests());     
    }
    
     /**
     * Print just number of @Tests.
     */
    public void print() {
        if (getNumberOfTests() > 0)
            System.out.println("Number of tests: " + getNumberOfTests());     
    }
    
    /** 
     * Loop thru the file getting all the tokens
     * 
     * @param directoryAndFilename the path and filename to be processed
     * 
     * @return List<Token> the list of tokens from the file
     */
    private List<Token> getTokensFromFile(String directoryAndFilename) {
        
        Scanner scanner = null;
        Token token = null;
        List<Token> tokenList = new ArrayList<>();
        
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
//      tokenList.print(tokenList);
        
        return tokenList;
    }
   
    /**
     * Process each token for metrics.
     * 
     * @param filename that is being processed
     * @param processExceptions compute exception keywords
     */
    private void processTokens(String filename, List<Token> tokenList, boolean processExceptions) {
        compute(filename, tokenList);
    }
    
    /** 
     * Test out the class.
     * 
     * @param args The supplied arguments to this routine.
     */
    public static void main(String[] args) {
        Tests tests = new Tests();
        List<Token> tokenList = new ArrayList<>();
        
        final boolean PRINT_JUST_FILENAME = false;
            
        // Pass in just one file
        if (args[0].endsWith(".java")) {
            System.out.println(args[0]);
            tokenList = tests.getTokensFromFile(args[0]);
            tests.processTokens(args[0], tokenList, false);
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
//                          System.out.println(filename); // &
                            tokenList = tests.getTokensFromFile(filename);  // not .c/.txt etc
                            tests.processTokens(filename, tokenList, false);
                        }
                    }
                }
                walk.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (!PRINT_JUST_FILENAME) {
            System.out.println("Number of tests: " + tests.getNumberOfTests());
        }
    }
}

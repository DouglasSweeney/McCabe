//
// File:    CountFiles.java
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
 * Keep track of the number of files that were processed.
 *  
 * @author dks
 * @since  1.0
 *
 */
public class CountFiles {
    static List<String> fileList = null;
    
    /**
     * The constructor.
     */
    public CountFiles() {
        fileList = new ArrayList<>();
        assert fileList != null : Tests.class.getCanonicalName() + 
                "constructor: fileList = null";
    }

    /**
     * Keep track of the number of files.
     * 
     * @param filename the filename of the processed file
     */
    public void compute(String filename) {
        Debug.println(Categories.INTERNAL_METHODS, CountFiles.class.getCanonicalName() + " " + 
                  "compute()");
        
        fileList.add(filename);
    }
    
    /**
     * Print just number of files.
     */
    public int getNumberOfFiles() {
        return fileList.size();
    }

    /**
     * Print just number of files.
     */
    public void debugging_print() {
        System.out.println("Number of files: " + getNumberOfFiles());     
    }
    
     /**
     * Print just number of files.
     */
    public void print() {
        System.out.println("Number of files: " + getNumberOfFiles());     
    }
    
   
    /**
     * Process each token for metrics.
     * 
     * @param filename that is being processed
     */
    private void processTokens(String filename) {
        compute(filename);
    }
    
    /** 
     * Test out the class.
     * 
     * @param args The supplied arguments to this routine.
     */
    public static void main(String[] args) {
        CountFiles files = new CountFiles();
        
        final boolean PRINT_JUST_FILENAME = false;
            
        // Pass in just one file
        if (args[0].endsWith(".java")) {
            System.out.println(args[0]);
            files.processTokens(args[0]);
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
                            files.processTokens(filename);
                        }
                    }
                }
                walk.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (!PRINT_JUST_FILENAME) {
            System.out.println("Number of files: " + files.getNumberOfFiles());
        }
    }

}

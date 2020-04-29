//
// File:    Metrics.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney 
//
package main.java.main;

import java.util.List;

import main.java.debug.Categories;
import main.java.debug.Debug;
import main.java.metrics.Mccabe;
import main.java.metrics.Packages;
import main.java.metrics.Slocs;
import main.java.scanner.Token;

/**
 * Coordinates the calculation of the McCabe complexity measurement.
 *  
 * @author dks
 * @since  1.0
 *
 */
public class Metrics {
    Packages packageIdentifier = null;
    Slocs    slocCounter = null;
    Mccabe   mccabeComplexity = null;
	
    /**
     * The constructor - creates some classes. Then the post condition assert are called.
     */
    public Metrics() {
    	packageIdentifier = new Packages();
    	slocCounter = new Slocs();
    	mccabeComplexity = new Mccabe();
    	
    	assert packageIdentifier != null : Metrics.class.getCanonicalName() + 
    			" constructor: packageIdentifier = null";
    	assert slocCounter != null : Metrics.class.getCanonicalName() + 
    			" constructor: slocCounter = null";
    	assert mccabeComplexity != null : Metrics.class.getCanonicalName() + 
    			" constructor: mcCabeComplexity = null";
    }
    
    /** 
     * Call the individual classes to get some statistics of the file(s) that were
     * passed in.
     * 
     * @param filename debugging
     * @param list the list of tokens 
     * @param options the user specified arguments on the command line 
     */
    public void compute(String filename, List<Token> list, Options options) {
    	Debug.println(Categories.INTERNAL_METHODS, Metrics.class.getCanonicalName() + " " + 
			      "compute()");
    	packageIdentifier.compute(list);
    	slocCounter.compute(filename, list);
    	mccabeComplexity.compute(filename, list, options);

    	//debugging_print();
    }
    
    /**
     * Print out in a verbose form the internal metrics. 
     * Not normally called as part of the program.
     * Debugging only.
     */
    public void debugging_print() {
    	packageIdentifier.debugging_print();
    	slocCounter.debugging_print();
    	mccabeComplexity.debugging_print();
    }

    /** 
     * Print a non-verbose output of the metrics.
     */
    public void print() {
    	packageIdentifier.print();
    	slocCounter.print();
    	mccabeComplexity.print();
    }
}

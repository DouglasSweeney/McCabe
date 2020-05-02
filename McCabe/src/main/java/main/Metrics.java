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
import main.java.metrics.CountFiles;
import main.java.metrics.Mccabe;
import main.java.metrics.Packages;
import main.java.metrics.Slocs;
import main.java.metrics.Tests;
import main.java.scanner.Token;

/**
 * Coordinates the calculation of the McCabe complexity measurement.
 *  
 * @author dks
 * @since  1.0
 *
 */
public class Metrics {
    Packages   packageIdentifier = null;
    Slocs      slocCounter = null;
    Mccabe     mccabeComplexity = null;
    Tests      tests = null;
    CountFiles files = null;
	
    /**
     * The constructor - creates some classes. Then the post condition assert are called.
     */
    public Metrics() {
    	packageIdentifier = new Packages();
    	slocCounter = new Slocs();
    	mccabeComplexity = new Mccabe();
    	tests = new Tests();
    	files = new CountFiles();
    	
    	assert packageIdentifier != null : Metrics.class.getCanonicalName() + 
    			" constructor: packageIdentifier = null";
    	assert slocCounter != null : Metrics.class.getCanonicalName() + 
    			" constructor: slocCounter = null";
        assert mccabeComplexity != null : Metrics.class.getCanonicalName() + 
                " constructor: mcCabeComplexity = null";
        assert tests != null : Metrics.class.getCanonicalName() + 
                " constructor: tests = null";
        assert files != null : Metrics.class.getCanonicalName() + 
                " constructor: files = null";
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
      	if (options != null) {
    		mccabeComplexity.compute(filename, list, options);
    	}
      	
      	tests.compute(filename, list);
      	files.compute(filename);
    	//debugging_print();
    }
    
    /**
     * debugging only
     */
    public void clearSlocList() {
    	slocCounter.clearList();
    }
    
    /**
     * Debugging only.
     * 
     * @return number of packages
     */
    public int getNumberOfTests() {
        return tests.getNumberOfTests();
    }

    /**
     * Debugging only.
     * 
     * @return number of packages
     */
    public int getNumberOfPackages() {
    	return packageIdentifier.size();
    }

    /**
     * Debugging only.
     * 
     * @param filename get the number of slocs for filename
     * 
     * @return the number of slocs
     */
    public int getNumberOfSlocs(String filename) {
    	return slocCounter.getNumberOfSlocs(filename);
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
    	tests.debugging_print();
        files.debugging_print();
   }

    /** 
     * Print a non-verbose output of the metrics.
     */
    public void print() {
    	packageIdentifier.print();
    	slocCounter.print();
    	mccabeComplexity.print();
    	tests.print();
    	files.print();
    }
}

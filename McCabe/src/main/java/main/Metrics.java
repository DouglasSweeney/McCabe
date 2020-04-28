package main.java.main;

import java.util.List;

import main.java.debug.Categories;
import main.java.debug.Debug;
import main.java.metrics.Mccabe;
import main.java.metrics.Packages;
import main.java.metrics.Slocs;
import main.java.scanner.Token;

public class Metrics {
    Packages packageIdentifier = null;
    Slocs    slocCounter = null;
    Mccabe   mccabeComplexity = null;
	
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
    
    public void compute(String filename, List<Token> list, Options options) {
    	Debug.println(Categories.INTERNAL_METHODS, Metrics.class.getCanonicalName() + " " + 
			      "compute()");
    	packageIdentifier.compute(list);
    	slocCounter.compute(filename, list);
    	mccabeComplexity.compute(filename, list, options);

    	//debugging_print();
    }
    
    public void debugging_print() {
    	packageIdentifier.debugging_print();
    	slocCounter.debugging_print();
    	mccabeComplexity.debugging_print();
    }

    public void print() {
    	packageIdentifier.print();
    	slocCounter.print();
    	mccabeComplexity.print();
    }
}

package source.main;

import java.util.List;

import source.metrics.Mccabe;
import source.metrics.Packages;
import source.metrics.Slocs;
import source.scanner.Token;

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
    			" constructor: mccabeComplexity = null";
    }
    
    public void compute(String filename, List<Token> list, Options options) {
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

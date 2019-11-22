package jplusplus.main;

import java.util.List;

import jplusplus.metrics.Mccabe;
import jplusplus.metrics.Packages;
import jplusplus.metrics.Slocs;
import jplusplus.scanner.Token;

public class Metrics {
    Packages packageIdentifier = null;
    Slocs    slocCounter = null;
    Mccabe   mccabeComplexity = null;
	
    public Metrics() {
    	packageIdentifier = new Packages();
    	slocCounter = new Slocs();
    	mccabeComplexity = new Mccabe();
    }
    
    public void compute(String filename, List<Token> list, boolean processExceptions) {
    	
    	packageIdentifier.compute(list);
    	slocCounter.compute(filename, list);
    	mccabeComplexity.compute(filename, list, processExceptions);
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

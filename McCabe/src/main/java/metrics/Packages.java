//
// File:    Packages.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney 
//
package main.java.metrics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.java.debug.Categories;
import main.java.debug.Debug;
import main.java.main.TokenList;
import main.java.scanner.Scanner;
import main.java.scanner.Token;
import main.java.scanner.TokenEnum;

/**
 * Keep track of the packages for the file(s)..
 * 
 * @author dks
 * @since 1.0
 */
public class Packages extends TokenList {

	static List<String> packageList = new ArrayList<>();
	
	public Packages() {
		assert packageList != null : Packages.class.getCanonicalName() + 
			                      "constructor: packageList = null";
	}
	
	/**
	 * Track the "package" keyword.
	 * 
	 * @param list Scan thru the list looking for the package keyword
	 */
	public void compute(List<Token> list) {
    	Debug.println(Categories.INTERNAL_METHODS, Packages.class.getCanonicalName() + " " + 
			      "compute()");
		Integer index = 0;
		boolean found;
    	StringBuffer buffer = new StringBuffer();
		
		Token token = list.get(index);
		while (token.enumeration != TokenEnum.EOF) {
			if (token.enumeration == TokenEnum.PACKAGE) {
				index = index + 1;
				token = list.get(index);
				while (token.enumeration == TokenEnum.PERIOD || 
		    	   token.enumeration == TokenEnum.IDENTIFIER) {
					if (token.enumeration == TokenEnum.PERIOD)
						buffer.append(".");
					else
						buffer.append(token.string);
					index = index + 1;
					token = list.get(index);
				}
				
				found = false;
				String string = buffer.toString();
				for (int i=0; i<packageList.size(); i++) {
					String item = packageList.get(i);
					if (item.equals(string))
						found = true;
				}
				
				if (!found) {
					packageList.add(string);
			    }
		    }
			else {
				index = index + 1;
				token = list.get(index);
			}
		}
	}

	/**
	 * Print the the individual package(s)
	 */
	public void debugging_print() {
		
		packageList.sort(Comparator.naturalOrder());
		for (String packageName : packageList) {
			System.out.println("package: " + packageName);
		}
	}
	/**
	 * Get the number of packages.
	 * 
	 * @return number of packages
	 */
	public int size() {
	    return packageList.size();
	}
		
	/**
	 * Print out just the number of packages
	 */
	public void print() {
	    System.out.println("Packages: " + packageList.size());
	}
	
	/**
	 * Debugging
	 */
	public void clear() {
	    packageList.clear();
	}
	
	/**
	 * Test out this class
	 * 
	 * @param args the arguments to main()
	 */
	public static void main(String[] args) {
		Packages packages = new Packages();
	
		Token token = null;
		Scanner scanner = null;
		TokenList tokenList = new TokenList();
	
		Stream<Path> walk = null;
		try {
			walk = Files.walk(Paths.get("/home/dks/git/McCabe_v1/McCabe/src/main"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		List<String> result = walk.filter(Files::isRegularFile)
    				.map(x -> x.toString()).collect(Collectors.toList());

		for (String filename : result) {
			System.out.println(filename);
			try {
				scanner = new Scanner(filename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			token = scanner.getNextToken();
			while  (token.enumeration != TokenEnum.EOF) {
   	   			System.out.println(token.string + ": " + token.enumeration + "(" + token.lineNumber + ")");
   	   			tokenList.add(token);
   	   			token = scanner.getNextToken();
			}
			tokenList.add(token);
		 	
			packages.compute(tokenList.getList());
			packages.debugging_print();
			packages.print();
		}
    }
}

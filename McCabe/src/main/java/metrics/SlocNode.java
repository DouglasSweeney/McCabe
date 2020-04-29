//
// File:    SlocNode.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney 
//
package main.java.metrics;

/**
 * Keep track of the line number of the file(s).
 * 
 * @author dks
 * @since 1.0
 */
public class SlocNode {
	public String filename = "";
	public Integer lineNumber = 0;
	
	/** 
	 * The constructor
	 * 
	 * @param filename    the filename associated with the line number
	 * @param lineNumber  the line number of the statement
	 */
	public SlocNode(String filename, Integer lineNumber) {
		this.filename = filename;
		this.lineNumber = lineNumber;
	}
}

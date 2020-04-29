//
// File:    MccabeNode.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney 
//
package main.java.metrics;

/**
 * Keep track of the McCabe complexity factor.
 * 
 * @author dks
 * @since  1.0
 */
public class MccabeNode {
	private String    filename;
	private String    className;
	private String    methodName;
	private Integer   lineNumber;
	private Integer   mccabeComplexityFactor = 0;
	
	public MccabeNode(String filename, String className, String methodName,
			          Integer lineNumber, Integer mccabe) {
		this.filename = filename;
		this.className = className;
		this.methodName = methodName;
		this.lineNumber = lineNumber;
		this.mccabeComplexityFactor = mccabe;
	}
	
	/**
	 * A getter.
	 * 
	 * @return the filename
	 */
	public String getFilename()  {
		return filename;
	}
	
	/**
	 * Another getter.
	 * 
	 * @return the class of the method (maybe two classes in a single file)
	 */
	public String getClassName()  {
		return className;
	}
	
	/** 
	 * Another getter.
	 * 
	 * @return the method
	 */
	public String getMethodName()  {
		return methodName;
	}
	
	/**
	 * Another getter
	 * 
	 * @return the line number
	 */
	public Integer getLineNumber()  {
		return lineNumber;
	}
	
	/**
	 * Another getter
	 * 
	 * @return the complexity factor
	 */
	public Integer getMccabeComplexityFactor()  {
		return mccabeComplexityFactor;
	}
}
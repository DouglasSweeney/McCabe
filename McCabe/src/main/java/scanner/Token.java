//
// File:    Scanner.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney
//
package main.java.scanner;

/**
 * A token is represented by this class.
 * 
 * @author dks
 * @since 1.0
 */
public class Token {

	public TokenEnum enumeration;
	public String    string;
	public Integer   lineNumber;
	
	/**
	 * A constructor.
	 * 
	 * @param enumeration the representation of the token file identifier/keywords
	 * @param lineNumber  the line number of the file that is being processed
	 */
	public Token(TokenEnum enumeration, int lineNumber) {
		this.enumeration = enumeration;
		this.lineNumber = lineNumber;
	}
	
	/**
	 * Another constructor
	 * 
	 * @param enumeration the representation of the token file identifier/keywords
	 * @param string      the identifier or the number
	 * @param lineNumber  the line number of the file being processed
	 */
	public Token(TokenEnum enumeration, String string, int lineNumber) {
		this.enumeration = enumeration;
		this.string = string;
		this.lineNumber = lineNumber;
	}
}

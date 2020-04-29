//
// File:    Utils.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney 
//
package main.java.main;

import java.util.ArrayList;
import java.util.List;

import main.java.scanner.Token;
import main.java.scanner.TokenEnum;

/**
 * A class that has utility methods.
 * 
 * @author dks
 * @since 1.0
 */
public class Utils {

	public static boolean throwsToken;
	
	/**
	 * Break down a file of tokens to just those tokens for a method.
	 * A method doesn't have to be specified - the braces are kept track of. When the
	 * the count of brace pairs is 0 then processing is stopped.
	 * 
	 * @param fileList the list of tokens for a file
	 * @param currentTokenIndex the index into the list of tokens for a file
	 * 
	 * @return the list for a method 
	 *         
	 */
	public List<Token> getProcedureTokens(List<Token> fileList, Integer currentTokenIndex) {
		List<Token> procedureList = new ArrayList<Token>();
		Token token = null;;
		Integer braces = 0;
		
		token = fileList.get(currentTokenIndex);
		while (token.enumeration != TokenEnum.LPAREN && // Get to beginning of parameters
			   token.enumeration != TokenEnum.EOF) {
			currentTokenIndex++;
     		token = fileList.get(currentTokenIndex);
		}
		while (token.enumeration != TokenEnum.RPAREN && // Get to end of parameters
			   token.enumeration != TokenEnum.EOF) {
			currentTokenIndex++;
	     	token = fileList.get(currentTokenIndex);
		}
		throwsToken = false;
		while (token.enumeration != TokenEnum.LBRACE && // Goto beginning of procedure
			token.enumeration != TokenEnum.EOF) {
			if (token.enumeration == TokenEnum.THROWS )
				throwsToken = true;
			
			currentTokenIndex++;
		    token = fileList.get(currentTokenIndex);
		}

		do {
			if (token.enumeration == TokenEnum.LBRACE)
				braces++;
			if (token.enumeration == TokenEnum.RBRACE)
				braces--;
			
			procedureList.add(token);
			
			currentTokenIndex = currentTokenIndex + 1;
			if (currentTokenIndex < fileList.size())
			    token = fileList.get(currentTokenIndex);
		} while (braces > 0 && token.enumeration != TokenEnum.EOF);

		return procedureList;
	}
}

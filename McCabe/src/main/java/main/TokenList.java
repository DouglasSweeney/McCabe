//
// File:    TokenList.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney 
//
package main.java.main;

import java.util.LinkedList;
import java.util.List;

import main.java.scanner.Token;
import main.java.scanner.TokenEnum;

/**
 * Build a list of tokens.
 * 
 * @author dks
 * @since 1.0
 */
public class TokenList {
   static private List<Token> list = null;;
   static private Integer     currentIndex;
   
   /**
    * The constructor.
    */
   public TokenList() {
	   list = new LinkedList<Token>();
	   currentIndex = 0;
	   
	   assert list != null : TokenList.class.getCanonicalName() + 
			                 "constructor: list = null";
   }
   
   /** 
    * Add a token to the list.
    * 
    * @param token the token to be added
    */
   public void add(Token token) {
	   list.add(token);
   }
   
   /**
    * Clear/Reset the list. 
    */
   public void clear() {
	   list.clear();
	   currentIndex = 0;
   }
   
   /**
    * Gets a token.
    * 
    * @param index position in the list
    * @return a token from the list
    */
   public Token get(int index) {
	   Token token = null;
	   
	   if (index >= 0) {
		  token = list.get(index);
	   }
	   
	   return token;
   }

   /**
    * A getter.
    * 
    * @return the list of tokens
    */
   public List<Token> getList() {
	   return list;
   }
   
   /**
    * Another getter.
    * 
    * @return the index into the list
    */
   public Integer getCurrentIndex() {
	   return currentIndex;
   }
   
   /** 
    * Print the list of tokens.
    * 
    * @param list the list to be printed
    */
   public void print(List<Token> list) {
	   
	   for (Token token : list) {
		   if (token.string != null)
			   System.out.println(token.string + ": " + token.enumeration + "(" + token.lineNumber + ")");
		   else
		       System.out.println(token.enumeration + "(" + token.lineNumber + ")");
	   }
   }
   
   /**
    * Do a deep copy of the list.
    */
   @Override
   public List<Token> clone() {
	   List<Token> newList = new LinkedList<Token>();
	   Token       newToken = null;
	   
	   for (Token token : list) {
		   newToken = token;
		   newList.add(newToken);
	   }
	   
	   return newList;
   }
   
   /**
    * A setter.
    * 
    * @param index set the currentIndex into the list
    */
   public void setCurrentIndex(Integer index) {
	   TokenList.currentIndex = index;;
   }
   
   /**
    * The main entry point to test out this file.
    * 
    * @param args user specified on the commend line
    */
   public static void main(String[] args) {
	  TokenList list = new TokenList();
	  List<Token> newList = new LinkedList<Token>();
	   
	  list.add(new Token(TokenEnum.GT, 0));
	  list.add(new Token(TokenEnum.PLUS, 0));
	  list.print(list.getList());
	  newList = list.clone();
	  for (Token token : newList) {
	     if (token.string != null)
		    System.out.println(token.string + ": " + token.enumeration + "(" + token.lineNumber + ")");
		 else {
			if (token.string != null) {
			   System.out.println(token.string + ": " + token.enumeration + "(" + token.lineNumber + ")");
			}
			else {
 			   System.out.println(token.enumeration + "(" + token.lineNumber + ")");
			   }
		  }
       }
    }
}

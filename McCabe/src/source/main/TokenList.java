package source.main;

import java.util.LinkedList;
import java.util.List;

import source.scanner.Token;
import source.scanner.TokenEnum;

public class TokenList {
   static private List<Token> list = null;;
   static private Integer     currentIndex;
   
   public TokenList() {
	   list = new LinkedList<Token>();
	   currentIndex = 0;
   }
   public void add(Token token) {
	   list.add(token);
   }
   
   public void clear() {
	   list.clear();
	   currentIndex = 0;
   }
   
   public Token get(int index) {
	   Token token = null;
	   
	   if (index >= 0) {
		  token = list.get(index);
	   }
	   
	   return token;
   }

   public List<Token> getList() {
	   return list;
   }
   
   public Integer getCurrentIndex() {
	   return currentIndex;
   }
   
   public void print(List<Token> list) {
	   
	   for (Token token : list) {
		   if (token.string != null)
			   System.out.println(token.string + ": " + token.enumeration + "(" + token.lineNumber + ")");
		   else
		       System.out.println(token.enumeration + "(" + token.lineNumber + ")");
	   }
   }
   
   /* Do a deep copy */
   @Override
   public List<Token> clone() {
	   List<Token> newList = new LinkedList();
	   Token       newToken = null;
	   
	   for (Token token : list) {
		   newToken = token;
		   newList.add(newToken);
	   }
	   
	   return newList;
   }
   
    public void setCurrentIndex(Integer index) {
	   currentIndex = index;;
   }
   
   public static void main(String[] args) {
	  TokenList list = new TokenList();
	  List<Token> newList = new LinkedList();
	   
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

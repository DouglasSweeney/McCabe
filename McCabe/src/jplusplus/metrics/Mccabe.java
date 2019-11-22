package jplusplus.metrics;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import jplusplus.main.TokenList;
import jplusplus.main.Utils;
import jplusplus.scanner.Scanner;
import jplusplus.scanner.Token;
import jplusplus.scanner.TokenEnum;

public class Mccabe extends TokenList {
	List<String> classList = new ArrayList();
	static List<MccabeNode> mccabeList = new ArrayList();
    Integer          mccabeComplexityFactor;
	Utils            utils = new Utils();
	
	public Mccabe() {
	}
			
    private void returns(List<Token> procedureList) {
    	Integer procedureTokenIndex = 0;
    	Token token = procedureList.get(procedureTokenIndex);
    	Integer braces = 1;
    	
//if (methodName.equals("main"))
//	methodName = "main";
    	while (token.enumeration != TokenEnum.RETURN &&
    		   procedureTokenIndex < procedureList.size()-1) {
    		
    		if (token.enumeration == TokenEnum.LBRACE) {
				braces++;
			}
			else {
				if (token.enumeration == TokenEnum.RBRACE) {
					braces--;
				}
			}
			
 		    if (procedureTokenIndex < procedureList.size()-1) {
   		       procedureTokenIndex++;   
   		       token = procedureList.get(procedureTokenIndex);
 		    }
    	}
    		
    	if (token.enumeration == TokenEnum.RETURN) {
        	while (token.enumeration != TokenEnum.SEMICOLON && 
        		   token.enumeration != TokenEnum.EOF) {
        		procedureTokenIndex++;
        	    token = procedureList.get(procedureTokenIndex);
        	}
        	
    		procedureTokenIndex++;
    	    token = procedureList.get(procedureTokenIndex);
        	if (token.enumeration != TokenEnum.RBRACE) {
        		braces--;
        		if (braces == 1)
        			mccabeComplexityFactor++;
        		
        	}
    	}
    }
    
    private void selections(List<Token> procedureList) {
    	Token token = null;
    	
    	for (int i=0;i < procedureList.size(); i++) {
    		token = procedureList.get(i);
    		if (token.enumeration == TokenEnum.IF ||
    			token.enumeration == TokenEnum.ELSE ||
    			token.enumeration == TokenEnum.CASE ||
    			token.enumeration == TokenEnum.DEFAULT) {
    		   mccabeComplexityFactor++;
    		}
    	}
    }
    
    private boolean lookForCoorespondingWhile(List<Token> procedureList, 
    		                                  Integer procedureTokenIndex,
    		                                  Integer braces) {
        boolean found = false;
        Integer doWhileBraces = 0;
    	Token token = null;
    	
       	for (int i=procedureTokenIndex;i < procedureList.size(); i++) {
    		token = procedureList.get(i);
			if (token.enumeration == TokenEnum.LBRACE) {
				doWhileBraces++;
			}
			else {
				if (token.enumeration == TokenEnum.RBRACE) {
					doWhileBraces--;
				}
			}
    		if (token.enumeration == TokenEnum.WHILE) {
    			if (doWhileBraces == braces) { // found corresponding while stmt
    	   		   found = true;
    			}
     		}
       	}
    		    			
        return found;
    }
    
    private void loops(List<Token> procedureList) {
    	Token token = null;
    	boolean found;
    	Integer braces = 0;
    	
    	for (int i=0;i < procedureList.size(); i++) {
    		token = procedureList.get(i);
			if (token.enumeration == TokenEnum.LBRACE) {
				braces++;
			}
			else {
				if (token.enumeration == TokenEnum.RBRACE) {
					braces--;
				}
			}
    		if (token.enumeration == TokenEnum.FOR ||
    			token.enumeration == TokenEnum.BREAK ||
    			token.enumeration == TokenEnum.CONTINUE) {
    		   mccabeComplexityFactor++;
    		}
    		
    		if (token.enumeration == TokenEnum.DO) {
    			found = lookForCoorespondingWhile(procedureList, i+1, braces);
    			if (found)
    				mccabeComplexityFactor++;
    		}
    		else
       		if (token.enumeration == TokenEnum.WHILE ) {
    			mccabeComplexityFactor++;
    		}
    	}
    }
    
    private void operators(List<Token> procedureList) {
    	Token token = null;
    	
       	for (int i=0;i < procedureList.size(); i++) {
    		token = procedureList.get(i);
       		if (token.enumeration == TokenEnum.AMPERSAND_AMPERSAND ||
        		token.enumeration == TokenEnum.OROR) {
        		   mccabeComplexityFactor++;
       		}
         	
   			if (token.enumeration == TokenEnum.QUESTION_MARK) {
     		   mccabeComplexityFactor++;
   			}
   			
       		if (token.enumeration == TokenEnum.COLON ) {
        		token = procedureList.get(i-2);
           		if (token.enumeration != TokenEnum.CASE ) {
    				mccabeComplexityFactor++;
           		}
       		}
       	}
    }
    
    private void exceptions(List<Token> procedureList) {
    	Token token = null;
    	
    	for (int i=0;i < procedureList.size(); i++) {
    		token = procedureList.get(i);
    		if (token.enumeration == TokenEnum.CATCH ||
    			token.enumeration == TokenEnum.FINALLY ||
    			token.enumeration == TokenEnum.THROW) {
    		   mccabeComplexityFactor++;
    		}
    	}
    }
    
    private void computeMcCabeComplexityFactor(List<Token> procedureList, boolean useExceptions) {
        returns(procedureList);
        selections(procedureList);
        loops(procedureList);
        operators(procedureList);
        if (useExceptions)
        	exceptions(procedureList);
        
    }
    
	private void findMethodsAndComplexityFactor(String filename, List<Token> list, 
			                                    Integer currentTokenIndex,
			                                    String className, Integer classNumber,
			                                    boolean useExceptions) {
		List<Token> procedureList = new ArrayList();
		Token token = list.get(currentTokenIndex);;
		String methodName = "";
		Token token2 = null;
		Integer braces = 0;
		
		while (token.enumeration != TokenEnum.EOF) {
			if (token.enumeration == TokenEnum.LBRACE) {
				braces++;
			}
			else {
				if (token.enumeration == TokenEnum.RBRACE) {
					braces--;
				}
			}
			
			// look for class inside a class
			if (token.enumeration == TokenEnum.CLASS) {
     		    token = list.get(currentTokenIndex + 1);
				if (token.enumeration == TokenEnum.IDENTIFIER && 
				   token.enumeration != TokenEnum.EOF &&
				   braces >= 0) {
					className = token.string;
//					classList.add(className);
					currentTokenIndex++;
					findMethodsAndComplexityFactor(filename, list, currentTokenIndex, 
							                       token.string, classNumber++, useExceptions);
				}
			}
			
			// look for '(' inside a class
			if (token.enumeration == TokenEnum.LPAREN && 
					classNumber >= 1 && 
					braces == 1) {
				// look for procedure name
     			token2 = list.get(currentTokenIndex - 1);
				if (token2.enumeration == TokenEnum.IDENTIFIER) {
					methodName = token2.string;
					
					// look for type
	     			token = list.get(currentTokenIndex - 2);
					if (token.enumeration == TokenEnum.GT || // return type: i.e.List<Token>
						token.enumeration == TokenEnum.IDENTIFIER ||
					    token.enumeration == TokenEnum.BYTE ||
						token.enumeration == TokenEnum.CHAR ||
						token.enumeration == TokenEnum.DOUBLE ||
						token.enumeration == TokenEnum.BOOLEAN ||
						token.enumeration == TokenEnum.FLOAT ||
						token.enumeration == TokenEnum.INT ||
						token.enumeration == TokenEnum.LONG ||
						token.enumeration == TokenEnum.SHORT ||
						token.enumeration == TokenEnum.VOID ||
						token.enumeration == TokenEnum.PUBLIC) {
						
					   // Set the mcCabe Complexity Factor to 1 for a new method
					   mccabeComplexityFactor = 1;
					   procedureList = utils.getProcedureTokens(list, currentTokenIndex);
					   if (utils.throwsToken)
						   if (useExceptions)
							   mccabeComplexityFactor++;
//System.out.println("procedureList");
//print(procedureList);
	
					   // compute the McCabe Complexity Factor
					   computeMcCabeComplexityFactor(procedureList, useExceptions);
					   
					   MccabeNode mccabeNode = new MccabeNode(filename, 
							   								  className,
 						                                      methodName, 
 							                                  token2.lineNumber, 
 							                                  mccabeComplexityFactor);
					   mccabeList.add(mccabeNode);
					}
				}
			}
			
			currentTokenIndex = currentTokenIndex + 1;
				token = list.get(currentTokenIndex);
		}
	}
	
    public void compute(String filename, List<Token> list, boolean useExceptions) {
    	Integer currentTokenIndex = 0;
    	Integer classNumber = 0;
    	String  className = "";
    	
    	Token token = list.get(currentTokenIndex);
    	while (token.enumeration != TokenEnum.EOF) {
    		currentTokenIndex++;
    	    token = list.get(currentTokenIndex);
    	
    	    if (token.enumeration == TokenEnum.CLASS) {
    	    	classNumber = 1;
    	    	currentTokenIndex++;
    	    	className = list.get(currentTokenIndex).string; // get the class name
    	    	classList.add(className);
    	    	currentTokenIndex++;
    	    	findMethodsAndComplexityFactor(filename, list, currentTokenIndex, className, 
    	    								   classNumber, useExceptions);
    	    }
    	}
    	
    }
    
    public void debugging_print() {
    	double total = 0;
    	double mccabeComplexityFactor = 0;
    	
     	System.out.println("Number of classes: " + classList.size());
     	for (String item : classList) { // get the overall Mccabe Complexity Factor
     		System.out.println("classname: " + item + " ");
     	}
     	
     	System.out.println("Number of methods: " + mccabeList.size());
      	for (MccabeNode item2 : mccabeList) { // get the method Mccabe Complexity Factor
    		System.out.print("filename: " + item2.getFilename() + " ");
    		System.out.print("classname: " + item2.getClassName() + " ");
     		System.out.print("methodName: " + item2.getMethodName() + "(" + item2.getLineNumber() + ") ");
    		System.out.println(" mccabeComplexityFactor: " + item2.getMccabeComplexityFactor());
    	}    	
   
   	    for (MccabeNode item2 : mccabeList) { // get the overall Mccabe Complexity Factor
   			total += item2.getMccabeComplexityFactor();
   	    }
  	 	mccabeComplexityFactor = total/mccabeList.size();
	    System.out.println("Mccabe Complexity Factor: " + mccabeComplexityFactor);   	
    }
    
    public void print() {
    	double complexityFactor;
    	double total = 0;
    	
    	for (MccabeNode item : mccabeList) { // get the overall Mccabe Complexity Factor
   			total += item.getMccabeComplexityFactor();
   	    }
  	 	complexityFactor = (double)(total/mccabeList.size());
	    System.out.println("Mccabe Complexity Factor: " + complexityFactor);
	    System.out.println("Number of classes: " + classList.size());
	    System.out.println("Number of methods: " + mccabeList.size());
	        }
    
	public static void main(String[] args) {
		Mccabe mcCabe = new Mccabe();
		
	   	Token token = null;
    	Scanner scanner = null;
    	TokenList tokenList = new TokenList();
    	
		try {
			scanner = new Scanner("src/jplusplus/scanner/TestInput.java");
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
  		 	
  		mcCabe.compute("TestInput.java", tokenList.getList(), true);
		mcCabe.debugging_print();
		mcCabe.print();
	}
}

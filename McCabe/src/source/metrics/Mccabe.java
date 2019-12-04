package source.metrics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import source.main.TokenList;
import source.main.Utils;
import source.scanner.Scanner;
import source.scanner.Token;
import source.scanner.TokenEnum;

public class Mccabe extends TokenList {
	List<String> classList = new ArrayList();
	static List<MccabeNode> methods = new ArrayList();
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
       
    private void incrementComplexityFactor(Integer size) {
		for (int i=0; i<size; i++) {
   			mccabeComplexityFactor++;	
		}
    }
    
    private void loops(List<Token> procedureList) {
    	Token token = null;
    	boolean found;
    	Integer braces = 0;
    	Integer doStatements = 0;
    	Integer whileStatements = 0;
    	Integer doWhileStatements = 0;
    	List<Integer> whileLineNumbers = new ArrayList<Integer>();
    	
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
    		
   			if (token.enumeration == TokenEnum.WHILE) {
   				whileLineNumbers.add(token.lineNumber);
   			}
   		
   			if (token.enumeration == TokenEnum.DO) {
   				doStatements++;
   			}
    	}
    
   		if (doStatements >= 1) {
   			doWhileStatements = doStatements;
  			incrementComplexityFactor(doWhileStatements);
  			whileStatements = whileLineNumbers.size()-doStatements;
  			incrementComplexityFactor(whileStatements);
   		}   		
   		else {
   			incrementComplexityFactor(whileLineNumbers.size());
   		}
   	}
    
    private void operators(List<Token> procedureList) {
    	Token token = null;
    	Token token2 = null;
    	
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
        		token = procedureList.get(i-1);
        		token2 = procedureList.get(i-2);
          		if (token.enumeration != TokenEnum.DEFAULT &&
                    token2.enumeration != TokenEnum.CASE ) {
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
		Token token = list.get(currentTokenIndex);
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
				   braces % 2 == 0) {
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
					
					if (methodName.equals("other4")) {
						methodName = methodName;
					}
					
					
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
					   methods.add(mccabeNode);
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
     	
     	System.out.println("Number of methods: " + methods.size());
      	for (MccabeNode item2 : methods) { // get the method Mccabe Complexity Factor
    		System.out.print("filename: " + item2.getFilename() + " ");
    		System.out.print("classname: " + item2.getClassName() + " ");
     		System.out.print("methodName: " + item2.getMethodName() + "(" + item2.getLineNumber() + ") ");
    		System.out.println(" mccabeComplexityFactor: " + item2.getMccabeComplexityFactor());
    	}    	
   
   	    for (MccabeNode item2 : methods) { // get the overall Mccabe Complexity Factor
   			total += item2.getMccabeComplexityFactor();
   	    }
  	 	mccabeComplexityFactor = total/methods.size();
	    System.out.println("Mccabe Complexity Factor: " + mccabeComplexityFactor);   	
    }
    
    public void print() {
    	double complexityFactor;
    	double total = 0;
    	
    	for (MccabeNode item : methods) { // get the overall Mccabe Complexity Factor
   			total += item.getMccabeComplexityFactor();
   	    }
  	 	complexityFactor = (double)(total/methods.size());
	    System.out.println("Mccabe Complexity Factor: " + complexityFactor);
	    System.out.println("Number of classes: " + classList.size());
	    System.out.println("Number of methods: " + methods.size());
	}
    
    public double getOverallComplexityFactor() {
    	double complexityFactor;
    	double total = 0;
    	
    	for (MccabeNode item : methods) { // get the overall Mccabe Complexity Factor
   			total += item.getMccabeComplexityFactor();
   	    }
  	 	complexityFactor = (double)(total/methods.size());
  	 	
  	 	return complexityFactor;
	}
    
    public Integer getMethodComplexityFactor(String method) {
    	Integer complexityFactor;
    	
		debugging_print();
		complexityFactor = 0;
    	for (MccabeNode item : methods) { // get the overall Mccabe Complexity Factor
    		if (item.getMethodName().equals(method))
    			complexityFactor = item.getMccabeComplexityFactor();
   	    }
  	 	
  	 	return complexityFactor;
	}
    
    public Integer getNumberOfMethods() {
   	 	return methods.size();
    }
    
    public Integer getMethodComplexityFactor(int methodIndex) {
    	MccabeNode item;
    	
    	if (methodIndex < getNumberOfMethods()) {
    		item = methods.get(methodIndex);
    		return item.getMccabeComplexityFactor();
    	}
    	else
    		return 0;
	}
    
    public String getMethodName(int methodIndex) {
    	MccabeNode item;
    	
    	if (methodIndex < getNumberOfMethods()) {
    		item = methods.get(methodIndex);
    		return item.getMethodName();
    	}
    	else
    		return "";
	}
        
	public static void main(String[] args) {
		Mccabe mcCabe = new Mccabe();
		
	   	Token token = null;
    	Scanner scanner = null;
    	TokenList tokenList = new TokenList();
    	
		try {
			scanner = new Scanner("src/test/source/metrics/TestInput.java");
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
		
		Integer size = mcCabe.getNumberOfMethods();
		for (int i=0; i<size; i++) {
			System.out.println("Class Name: " + (methods.get(i)).getClassName() + " Method Name: " + mcCabe.getMethodName(i) + " Mccabe Complexity Factor: " + mcCabe.getMethodComplexityFactor(i));
		}
		
		System.out.println("Method Name: " + "main" + 
		                   " Mccabe Complexity Factor: " + 
				            mcCabe.getMethodComplexityFactor("main"));
			
	}
}

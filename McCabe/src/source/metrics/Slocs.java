package source.metrics;

import java.io.File;
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

import source.main.Options;
import source.main.TokenList;
import source.scanner.Scanner;
import source.scanner.Token;
import source.scanner.TokenEnum;

public class Slocs extends TokenList {
	static TokenList tokenList = new TokenList();
	 
	private class SlocNode {
		public String filename = "";
		public Integer lineNumber = 0;
		
		public SlocNode(String filename, Integer lineNumber) {
			this.filename = filename;
			this.lineNumber = lineNumber;
		}
	}
	
 	static List<SlocNode> slocList = new ArrayList();;
	
	public Slocs() {
		assert tokenList != null : Slocs.class.getCanonicalName() + 
                "constructor: tokenList = null";
		assert slocList != null : Slocs.class.getCanonicalName() + 
                "constructor: slocList = null";
	}
	
	public void compute(String filename, List<Token> list) {
		boolean found = false;;
		Integer currentTokenIndex = 0;
		
		if (list != null) {
			Token token = list.get(currentTokenIndex);		
			while (token.enumeration != TokenEnum.EOF) {
				found = false;
				for (int i=0; i<slocList.size(); i++) {
					SlocNode item = slocList.get(i);
                
//      			if (token.lineNumber == 383)
//       				token.lineNumber = 383;
					if ((item.filename.equals(filename)) && 
						(item.lineNumber.intValue() == token.lineNumber.intValue())) {
						found = true;
						break;
					}
				} 
			
				if (!found) {
					SlocNode slocNode = new SlocNode(filename, token.lineNumber);
				
					slocList.add(slocNode);
				}
				currentTokenIndex = currentTokenIndex + 1;
				token = list.get(currentTokenIndex);
			}
		}
		else {
			System.err.println(Slocs.class.getCanonicalName() + 
					           "The list is null.");
		}
	}
	
	public void debugging_print() {
		Integer counter = 0;
		
		for (SlocNode sloc : slocList) {
			File file = new File(sloc.filename);
			System.out.println("slocList(" + counter + ")" + file.getName() + " " + sloc.lineNumber);
			counter++;
		}
		System.out.println("slocList.size: " + slocList.size());
	}
	
	public void print() {
		Integer total = 0;
		
	    System.out.println("Source Lines of Code: " + slocList.size());   	
	}
	
    private void getTokensFromFile(String directoryAndFilename) {
		
	    Scanner scanner = null;
        Token token = null;
	    
 		try {
			scanner = new Scanner(directoryAndFilename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
  			
 		token = new Token(TokenEnum.NONE, 0);
 		while  (token.enumeration != TokenEnum.EOF) {
 			token = scanner.getNextToken();
 			tokenList.add(token);
 		}
// 		tokenList.print(tokenList.getList());
    }
   
    private void processTokens(String filename, boolean processExceptions) {
		compute(filename, tokenList.getList());
    }
    
	public static void main(String[] args) {
    	Slocs slocs = new Slocs();
    	
		final boolean PRINT_JUST_FILENAME = false;
		Options options = new Options(args);
//	       System.out.println("useExceptions: " + options.getUseExceptions());
//	        System.out.println("Last parameter: "+ args[args.length-1]);
//	        options.setMcCabeDirectory(args[args.length-1]);
	 		
		// Pass in just one file
    	if (args[0].endsWith(".java")) {
			System.out.println(args[0]);
    		slocs.getTokensFromFile(args[0]);
    		slocs.processTokens(args[0], false);
    	}
    	else { // else pass in a directory
 		    try {
 		    	Stream<Path> walk = Files.walk(Paths.get(args[0]));

 		    	List<String> result = walk.filter(Files::isRegularFile)
 		    				.map(x -> x.toString()).collect(Collectors.toList());

 		    	for (String filename : result) {
 		    		if (PRINT_JUST_FILENAME) {
  		    			System.out.println(filename);
 		    		}
 		    		else {
 		    			if (filename.endsWith(".java")) { // make sure its a .java file;
// 		    				System.out.println(filename); // &
 		    				slocs.getTokensFromFile(filename);  // not .c/.txt etc
 		    				slocs.processTokens(filename, false);
 		    				tokenList.clear();
 		    			}
		    		}
 		    	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	
    	if (!PRINT_JUST_FILENAME) {
    		slocs.debugging_print();
    	}
	}
}

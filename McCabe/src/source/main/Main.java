package source.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import source.scanner.Scanner;
import source.scanner.Token;
import source.scanner.TokenEnum;

public class Main {
	static TokenList tokenList = new TokenList();
 	static Metrics metrics = new Metrics(); 
 	
    private static void getTokensFromFile(String directoryAndFilename) {
		
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
 			tokenList.add(token);;
 		}
		//tokenList.print(tokenList.getList());
    }
   
    private static void processTokens(String filename, Options options) {
 		metrics.compute(filename, tokenList.clone(), options);
    }
    
 	public static void main(String[] args) {
		final boolean PRINT_JUST_FILENAME = false;
		Options options = new Options(args);
//	       System.out.println("useExceptions: " + options.getUseExceptions());
//	        System.out.println("Last parameter: "+ args[args.length-1]);
	        options.setMcCabeDirectory(args[args.length-1]);
	 		
		// Pass in just one file
    	if (options.getMcCabeDirectory().endsWith(".java")) {
//			System.out.println(options.getMcCabeDirectory());
    		getTokensFromFile(options.getMcCabeDirectory());
    		processTokens(options.getMcCabeDirectory(), options);
    	}
    	else { // else pass in a directory
 		    try {
 		    	Stream<Path> walk = Files.walk(Paths.get(options.getMcCabeDirectory()));

 		    	List<String> result = walk.filter(Files::isRegularFile)
 		    				.map(x -> x.toString()).collect(Collectors.toList());

 		    	for (String filename : result) {
 		    		if (PRINT_JUST_FILENAME) {
  		    			System.out.println(filename);
 		    		}
 		    		else {
 		    			if (filename.endsWith(".java")) { // make sure its a .java file;
// 		    				System.out.println(filename); // &
 		    				getTokensFromFile(filename);  // not .c/.txt etc
 		    				processTokens(filename, options);
 		    				tokenList.clear();
 		    			}
		    		}
 		    	}
 		    	walk.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	
//    	if (!PRINT_JUST_FILENAME) {
//    		metrics.print();
//    	}
	}
}

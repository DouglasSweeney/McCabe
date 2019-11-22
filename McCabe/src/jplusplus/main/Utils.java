package jplusplus.main;

import java.util.ArrayList;
import java.util.List;

import jplusplus.scanner.Token;
import jplusplus.scanner.TokenEnum;

public class Utils {

	public static boolean throwsToken;
	
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

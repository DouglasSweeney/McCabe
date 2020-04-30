package test.java.main;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.main.Main;
import main.java.main.Metrics;
import main.java.main.Options;
import main.java.main.Utils;
import main.java.scanner.Token;
import main.java.scanner.TokenEnum;

public class UtilsTest {
	Utils utils;
	
	@Before
	public void setUp() {
		utils = new Utils();
	}
	
	@After
	public void teardown() {
		utils = null;
	}
	
	@Test
	public void Utils__getProcedureList() {
		List<Token> fileList = new ArrayList<>();
		Integer currentTokenIndex;
		List<Token> procedureList = new ArrayList<>();
		
		// Build a token list for a procedure.
		Token token = new Token(TokenEnum.PRIVATE, 1);
		fileList.add(token);
		token = new Token(TokenEnum.VOID, 1);
		fileList.add(token);
		token = new Token(TokenEnum.IDENTIFIER, "methodName", 1);
		fileList.add(token);
		token = new Token(TokenEnum.LPAREN, 1);
		fileList.add(token);
		token = new Token(TokenEnum.IDENTIFIER, "String", 1);
		fileList.add(token);
		token = new Token(TokenEnum.LBRACKET , 1);
		fileList.add(token);
		token = new Token(TokenEnum.RBRACKET, 1);
		fileList.add(token);
		token = new Token(TokenEnum.IDENTIFIER, "args", 1);
		fileList.add(token);
		token = new Token(TokenEnum.RPAREN, 1);
		fileList.add(token);
		token = new Token(TokenEnum.LBRACE, 2);
		fileList.add(token);
		token = new Token(TokenEnum.INT, 3);
		fileList.add(token);
		token = new Token(TokenEnum.IDENTIFIER, "i", 3);
		fileList.add(token);
		token = new Token(TokenEnum.SEMICOLON, 3);
		fileList.add(token);
		token = new Token(TokenEnum.RBRACE, 4);
		fileList.add(token);
		token = new Token(TokenEnum.EOF, 5);
		fileList.add(token);
		
		currentTokenIndex = 0;
		procedureList = utils.getProcedureTokens(fileList, currentTokenIndex);

		assertEquals(5, procedureList.size()); 
	}
}

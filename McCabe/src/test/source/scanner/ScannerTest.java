package test.source.scanner;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import source.scanner.Scanner;
import source.scanner.Token;
import source.scanner.TokenEnum;

class ScannerTest {

	private static final String DIRECTORY = "src/test/source/scanner/inputs/";
	
   	private Token token = null;
   	private Scanner scanner = null;
   	
	public void getScanner(String input_Filename) {
		try {
			scanner = new Scanner(input_Filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void printToken() {
    	System.out.println(token.enumeration);
		
	}
	
// Java example of the tokens - writes them to standard out
	@Test
	void testInput() {
		getScanner("src/test/source/metrics/TestInput.java");
    	token = scanner.getNextToken();
    	do {
        	System.out.println(token.string+ ": " + token.enumeration );
           	token = scanner.getNextToken();
        } while  (token.enumeration != TokenEnum.EOF);
	}

	@Test
	void tilde() {
		getScanner(DIRECTORY + "tilde.txt");

	    token = scanner.getNextToken();
	    assertEquals(TokenEnum.TILDE, token.enumeration);
	}

	@Test
	void lParen() {
		getScanner(DIRECTORY + "lparen.txt");

	    token = scanner.getNextToken();
	    assertEquals(TokenEnum.LPAREN, token.enumeration);
	}

	@Test
	void rParen() {
		getScanner(DIRECTORY + "rparen.txt");

	    token = scanner.getNextToken();
	    assertEquals(TokenEnum.RPAREN, token.enumeration);
	}

	@Test
	void lBrace() {
		getScanner(DIRECTORY + "lbrace.txt");

	    token = scanner.getNextToken();
	    assertEquals(TokenEnum.LBRACE, token.enumeration);
	}
	
	@Test
	void rBrace() {
		getScanner(DIRECTORY + "rbrace.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.RBRACE, token.enumeration);
	}

	@Test
	void lBracket() {
		getScanner(DIRECTORY + "lbracket.txt");

	    token = scanner.getNextToken();
	    assertEquals(TokenEnum.LBRACKET, token.enumeration);
	}
	
	@Test
	void rBracket() {
		getScanner(DIRECTORY + "rbracket.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.RBRACKET, token.enumeration);
	}
	
	@Test
	void semiColon() {
		getScanner(DIRECTORY + "semicolon.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.SEMICOLON, token.enumeration);
	}
	
	@Test
	void comma() {
		getScanner(DIRECTORY + "comma.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.COMMA, token.enumeration);
	}

	@Test
	void period() {
		getScanner(DIRECTORY + "period.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.PERIOD, token.enumeration);
	}

	@Test
	void questionmark() {
		getScanner(DIRECTORY + "questionmark.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.QUESTION_MARK, token.enumeration);
	}

	@Test
	void colon() {
		getScanner(DIRECTORY + "colon.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.COLON, token.enumeration);
	}
	
	@Test
	void equalequal() {
		getScanner(DIRECTORY + "equalequal.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.EQUAL_EQUAL, token.enumeration);
	}

	@Test
	void equals() {
		getScanner(DIRECTORY + "equals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.EQUALS, token.enumeration);
	}

	@Test
	void notequals() {
		getScanner(DIRECTORY + "notequals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.NOT_EQUALS, token.enumeration);
	}

	@Test
	void not() {
		getScanner(DIRECTORY + "not.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.NOT, token.enumeration);
	}

	@Test
	void orequals() {
		getScanner(DIRECTORY + "orequals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.OR_EQUALS, token.enumeration);
	}

	@Test
	void or() {
		getScanner(DIRECTORY + "or.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.OR, token.enumeration);
	}

	@Test
	void oror() {
		getScanner(DIRECTORY + "oror.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.OROR, token.enumeration);
	}

	@Test
	void caretequals() {
		getScanner(DIRECTORY + "caretequals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.CARET_EQUALS, token.enumeration);
	}

	@Test
	void caret() {
		getScanner(DIRECTORY + "caret.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.CARET, token.enumeration);
	}

	@Test
	void modequals() {
		getScanner(DIRECTORY + "modequals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.MOD_EQUALS, token.enumeration);
	}

	@Test
	void mod() {
		getScanner(DIRECTORY + "mod.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.MOD, token.enumeration);
	}
	
	@Test
	void multiplyequals() {
		getScanner(DIRECTORY + "multiplyequals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.MULTIPLY_EQUALS, token.enumeration);
	}

	@Test
	void asterisk() {
		getScanner(DIRECTORY + "asterisk.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.ASTERISK, token.enumeration);
	}
	
	@Test
	void divideequals() {
		getScanner(DIRECTORY + "divideequals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.DIVIDE_EQUALS, token.enumeration);
	}

	@Test
	void divide() {
		getScanner(DIRECTORY + "divide.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.DIVIDE, token.enumeration);
	}

	@Test
	void plusequals() {
		getScanner(DIRECTORY + "plusequals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.PLUS_EQUALS, token.enumeration);
	}

	@Test
	void increment() {
		getScanner(DIRECTORY + "increment.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.INCREMENT, token.enumeration);
	}
	
	@Test
	void plus() {
		getScanner(DIRECTORY + "plus.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.PLUS, token.enumeration);
	}

	@Test
	void decrement() {
		getScanner(DIRECTORY + "decrement.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.DECREMENT, token.enumeration);
	}
	
	@Test
	void minus() {
		getScanner(DIRECTORY + "minus.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.MINUS, token.enumeration);
	}
	
	@Test
	void ampersandampersand() {
		getScanner(DIRECTORY + "ampersandampersand.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.AMPERSAND_AMPERSAND, token.enumeration);
	}

	@Test
	void ampersand() {
		getScanner(DIRECTORY + "ampersand.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.AMPERSAND, token.enumeration);
	}

	@Test
	void gtequals() {
		getScanner(DIRECTORY + "gtequals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.GT_EQUALS, token.enumeration);
	}

	@Test
	void unsignedshiftright() {
		getScanner(DIRECTORY + "unsignedshiftright.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.UNSIGNED_SHIFT_RIGHT, token.enumeration);
	}

	@Test
	void unsignedshiftrightequals() {
		getScanner(DIRECTORY + "unsignedshiftrightequals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.UNSIGNED_SHIFT_RIGHT_EQUALS, token.enumeration);
	}

	@Test
	void shiftright() {
		getScanner(DIRECTORY + "shiftright.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.SHIFT_RIGHT, token.enumeration);
	}
	
	@Test
	void shiftrightequals() {
		getScanner(DIRECTORY + "shiftrightequals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.SHIFT_RIGHT_EQUALS, token.enumeration);
	}
	
	@Test
	void gt() {
		getScanner(DIRECTORY + "gt.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.GT, token.enumeration);
	}
	
	@Test
	void lessequals() {
		getScanner(DIRECTORY + "lessequals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.LESS_EQUALS, token.enumeration);
	}
	
	@Test
	void shiftleft() {
		getScanner(DIRECTORY + "shiftleft.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.SHIFT_LEFT, token.enumeration);
	}
	
	@Test
	void shiftleftequals() {
		getScanner(DIRECTORY + "shiftleftequals.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.SHIFT_LEFT_EQUALS, token.enumeration);
	}
	
	@Test
	void less() {
		getScanner(DIRECTORY + "less.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.LESS, token.enumeration);
	}
	
	@Test
	void stringliteral() {
		getScanner(DIRECTORY + "stringliteral.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.STRING_LITERAL, token.enumeration);
	}
	
	@Test
	void eof() {
		getScanner(DIRECTORY + "eof.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.EOF, token.enumeration);
	}
	
	@Test
	void base16integer() {
		getScanner(DIRECTORY + "base16integer.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.BASE16_INTEGER, token.enumeration);
	}
	
	@Test
	void base2integer() {
		getScanner(DIRECTORY + "base2integer.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.BASE2_INTEGER, token.enumeration);
	}
	
	@Test
	void base8integer() {
		getScanner(DIRECTORY + "base8integer.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.BASE8_INTEGER, token.enumeration);
	}
	
	@Test
	void longLiteral() {
		getScanner(DIRECTORY + "longliteral.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.LONG_LITERAL, token.enumeration);
	}
	
	@Test
	void identifier() {
		getScanner(DIRECTORY + "identifier.txt");

		token = scanner.getNextToken();
		assertEquals(TokenEnum.IDENTIFIER, token.enumeration);
	}
}



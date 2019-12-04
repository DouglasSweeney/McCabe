package test.source.metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import source.main.TokenList;
import source.metrics.Mccabe;
import source.scanner.Scanner;
import source.scanner.Token;
import source.scanner.TokenEnum;

class McCabeTest {

	private static final String DIRECTORY = "src/test/source/metrics";
	
   	private static Token token = null;
	private static Scanner scanner = null;
	private static TokenList tokenList = new TokenList();

	private static Mccabe mcCabe = new Mccabe();
   	
	@BeforeAll
	public static void beforeClass() {
    	String filename = "TestInput.java";
    	
		try {
			scanner = new Scanner(DIRECTORY + "/" + filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	token = scanner.getNextToken();
    	while  (token.enumeration != TokenEnum.EOF) {
//       	   	System.out.println(token.string + ": " + token.enumeration + "(" + token.lineNumber + ")");
       		tokenList.add(token);
       	    token = scanner.getNextToken();
       	}
  		tokenList.add(token);
  		 	
  		mcCabe.compute(filename, tokenList.getList(), true);
		
	}

	@Test
	void checkMcCabeTestInputMethod() {
		assertEquals((Integer)3, mcCabe.getMethodComplexityFactor("TestInput"));
	}

	@Test
	void checkMcCabeForStatementMethod() {
		assertEquals((Integer)6, mcCabe.getMethodComplexityFactor("forStatement"));
	}

	@Test
	void checkMcCabeBooleanStatementMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("booleanStatement"));
	}

	@Test
	void checkMcCabeCharMethodMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("charMethod"));
	}

	@Test
	void checkMcCabeIfStatementMethod() {
		assertEquals((Integer)10, mcCabe.getMethodComplexityFactor("ifStatement"));
	}
	
	@Test
	void checkMcCabeWhileStatementMethod() {
		assertEquals((Integer)4, mcCabe.getMethodComplexityFactor("whileStatement"));
	}
	
	@Test
	void checkMcCabeOperatorsMethod() {
		assertEquals((Integer)6, mcCabe.getMethodComplexityFactor("operators"));
	}
	
	@Test
	void checkMcCabeCharLiteralMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("charLiteral"));
	}
	
	@Test
	void checkMcCabeStringLiteralMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("stringLiteral"));
	}
	
	@Test
	void checkMcCabeBase2IntegerMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("base2_Integer"));
	}
	
	@Test
	void checkMcCabeBase8IntegerMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("base8_Integer"));
	}
	
	@Test
	void checkMcCabeBase10IntegerMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("base10_Integer"));
	}
	
	@Test
	void checkMcCabeBase16IntegerMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("base16_Integer"));
	}
	
	@Test
	void checkMcCabeLongsMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("longs"));
	}
	
	@Test
	void checkMcCabeFloatLiteralMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("floatLiteral"));
	}
	
	@Test
	void checkMcCabeDoubleLiteralMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("doubleLiteral"));
	}
	
	@Test
	void checkMcCabeCommaMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("comma"));
	}
	
	@Test
	void checkMcCabeArraysMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("arrays"));
	}
	
	@Test
	void checkMcCabeAnalyzeMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("analyze"));
	}
	
	@Test
	void checkMcCabeCommentsMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("comments"));
	}
	
	@Test
	void checkMcCabeTildeMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("tilde"));
	}
	
	@Test
	void checkMcCabeShiftsMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("shifts"));
	}
	
	@Test
	void checkMcCabeOtherMethod() {
		assertEquals((Integer)3, mcCabe.getMethodComplexityFactor("other"));
	}
	
	@Test
	void checkMcCabeOther2Method() {
		assertEquals((Integer)7, mcCabe.getMethodComplexityFactor("other2"));
	}
	
	@Test
	void checkMcCabeOther3Method() {
		assertEquals((Integer)6, mcCabe.getMethodComplexityFactor("other3"));
	}
	
	@Test
	void checkMcCabeOther4Method() {
		assertEquals((Integer)6, mcCabe.getMethodComplexityFactor("other4"));
	}
	
	@Test
	void checkMcCabeHelloWorldMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("helloWorld"));
	}
	
	@Test
	void checkMcCabeFocusGainedMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("focusGained"));
	}
	
	@Test
	void checkMcCabeFocusLostMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("focusLost"));
	}
	
	@Test
	void checkMcCabeAbstractClassMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("AbstractClass"));
	}
	
	@Test
	void checkMcCabeSubProcedureMethod() {
		assertEquals((Integer)5, mcCabe.getMethodComplexityFactor("subProcedure"));
	}
	
	@Test
	void checkMcCabeMainMethod() {
		assertEquals((Integer)10, mcCabe.getMethodComplexityFactor("main"));
	}
	
	@Test
	void checkMcCabeMain2Method() {
		assertEquals((Integer)4, mcCabe.getMethodComplexityFactor("main2"));
	}
	
	@Test
	void checkMcCabeMain3Method() {
		assertEquals((Integer)5, mcCabe.getMethodComplexityFactor("main3"));
	}
}



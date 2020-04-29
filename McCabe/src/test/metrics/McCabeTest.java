package test.metrics;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import main.java.main.Options;
import main.java.main.TokenList;
import main.java.metrics.Mccabe;
import main.java.scanner.Scanner;
import main.java.scanner.Token;
import main.java.scanner.TokenEnum;

public class McCabeTest {

	private static final String DIRECTORY = "/home/dks/git/McCabe_v1/McCabe/src/test";
	
   	private static Token token = null;
	private Scanner scanner = null;
	private static TokenList tokenList = new TokenList();

	private static Mccabe mcCabe = new Mccabe();
   	
	@Before
	public void beforeClass() {
    	String filename = "TestInput.java";
    	String[] args = new String[4];
    	args[0] = "-exceptions";
    	args[1] = "--method";
    	args[2] = "main";
    	args[3] = "test/metrics/TestInput.java";
    	Options options = new Options(args);
    	
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
  		 	
  		mcCabe.compute(filename, tokenList.getList(), options);
		
	}

	@Test
	public void checkMcCabeTestInputMethod() {
		assertEquals((Integer)3, mcCabe.getMethodComplexityFactor("TestInput"));
	}

	@Test
	public void checkMcCabeForStatementMethod() {
		assertEquals((Integer)6, mcCabe.getMethodComplexityFactor("forStatement"));
	}

	@Test
	public void checkMcCabeBooleanStatementMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("booleanStatement"));
	}

	@Test
	public void checkMcCabeCharMethodMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("charMethod"));
	}

	@Test
	public void checkMcCabeIfStatementMethod() {
		assertEquals((Integer)10, mcCabe.getMethodComplexityFactor("ifStatement"));
	}
	
	@Test
	public void checkMcCabeWhileStatementMethod() {
		assertEquals((Integer)4, mcCabe.getMethodComplexityFactor("whileStatement"));
	}
	
	@Test
	public void checkMcCabeOperatorsMethod() {
		assertEquals((Integer)6, mcCabe.getMethodComplexityFactor("operators"));
	}
	
	@Test
	public void checkMcCabeCharLiteralMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("charLiteral"));
	}
	
	@Test
	public void checkMcCabeStringLiteralMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("stringLiteral"));
	}
	
	@Test
	public void checkMcCabeBase2IntegerMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("base2_Integer"));
	}
	
	@Test
	public void checkMcCabeBase8IntegerMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("base8_Integer"));
	}
	
	@Test
	public void checkMcCabeBase10IntegerMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("base10_Integer"));
	}
	
	@Test
	public void checkMcCabeBase16IntegerMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("base16_Integer"));
	}
	
	@Test
	public void checkMcCabeLongsMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("longs"));
	}
	
	@Test
	public void checkMcCabeFloatLiteralMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("floatLiteral"));
	}
	
	@Test
	public void checkMcCabeDoubleLiteralMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("doubleLiteral"));
	}
	
	@Test
	public void checkMcCabeCommaMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("comma"));
	}
	
	@Test
	public void checkMcCabeArraysMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("arrays"));
	}
	
	@Test
	public void checkMcCabeAnalyzeMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("analyze"));
	}
	
	@Test
	public void checkMcCabeCommentsMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("comments"));
	}
	
	@Test
	public void checkMcCabeTildeMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("tilde"));
	}
	
	@Test
	public void checkMcCabeShiftsMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("shifts"));
	}
	
	@Test
	public void checkMcCabeOtherMethod() {
		assertEquals((Integer)3, mcCabe.getMethodComplexityFactor("other"));
	}
	
	@Test
	public void checkMcCabeOther2Method() {
		assertEquals((Integer)7, mcCabe.getMethodComplexityFactor("other2"));
	}
	
	@Test
	public void checkMcCabeOther3Method() {
		assertEquals((Integer)6, mcCabe.getMethodComplexityFactor("other3"));
	}
	
	@Test
	public void checkMcCabeOther4Method() {
		assertEquals((Integer)6, mcCabe.getMethodComplexityFactor("other4"));
	}
	
	@Test
	public void checkMcCabeHelloWorldMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("helloWorld"));
	}
	
	@Test
	public void checkMcCabeFocusGainedMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("focusGained"));
	}
	
	@Test
	public void checkMcCabeFocusLostMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("focusLost"));
	}
	
	@Test
	public void checkMcCabeAbstractClassMethod() {
		assertEquals((Integer)1, mcCabe.getMethodComplexityFactor("AbstractClass"));
	}
	
	@Test
	public void checkMcCabeSubProcedureMethod() {
		assertEquals((Integer)5, mcCabe.getMethodComplexityFactor("subProcedure"));
	}
	
	@Test
	public void checkMcCabeMainMethod() {
		assertEquals((Integer)10, mcCabe.getMethodComplexityFactor("main"));
	}
	
	@Test
	public void checkMcCabeMain2Method() {
		assertEquals((Integer)4, mcCabe.getMethodComplexityFactor("main2"));
	}
	
	@Test
	public void checkMcCabeMain3Method() {
		assertEquals((Integer)5, mcCabe.getMethodComplexityFactor("main3"));
	}
}
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
import main.java.scanner.Token;
import main.java.scanner.TokenEnum;

public class MetricsTest {
	Metrics metrics;
	
	@Before
	public void setUp() {
		metrics = new Metrics();
	}
	
	@After
	public void teardown() {
		metrics = null;
	}
	
	@Test
	public void checkSlocsForFile() {
		Main main = new Main();
		boolean PRINT_JUST_FILENAME = false;
		
		String[] args = new String[1];
		args[0] = "/home/dks/git/McCabe_v1/McCabe/src/test/TestInput.java";
		Options options = new Options(args);
		
		main.run(PRINT_JUST_FILENAME, options);

		assertEquals(443, metrics.getNumberOfSlocs(options.getMcCabeDirectory())); 
	}
	
	@Test
	public void debuggingPrint() {
		String[] args = new String[1];
		args[0] = "/home/dks/git/McCabe_v1/McCabe/src/test/TestInput.java";
		Options options = new Options(args);
		
		String filename = options.getMcCabeDirectory();
		
		List<Token> list = new ArrayList<>();
		int lineNumber = 1;
		Token token = new Token(TokenEnum.PACKAGE, "main.java", lineNumber);
		list.add(token);
		token = new Token(TokenEnum.EOF, lineNumber);
		list.add(token);
		
		metrics.compute(filename, list, options);
		
		metrics.debugging_print();
		
		assertEquals(443, metrics.getNumberOfSlocs(filename));
	}
	
	@Test
	public void print() {
		String[] args = new String[1];
		args[0] = "/home/dks/git/McCabe_v1/McCabe/src/test/TestInput.java";
		Options options = new Options(args);
		
		String filename = options.getMcCabeDirectory();
		
		List<Token> list = new ArrayList<>();
		int lineNumber = 1;
		Token token = new Token(TokenEnum.PACKAGE, "main.java", lineNumber);
		list.add(token);
		token = new Token(TokenEnum.EOF, lineNumber);
		list.add(token);
		
		metrics.compute(filename, list, options);
		
//		metrics.print();
		
		assertEquals(443, metrics.getNumberOfSlocs(filename));
	}
	
	@Test
	public void checkSlocsForDirectory() {
		Main main = new Main();
		boolean PRINT_JUST_FILENAME = false;
		
		String[] args = new String[1];
		args[0] = "/home/dks/git/McCabe_v1/McCabe/src/test/java/debug";
		Options options = new Options(args);
		
		metrics.clearSlocList();
		main.run(PRINT_JUST_FILENAME, options);

		assertEquals(122, metrics.getNumberOfSlocs(options.getMcCabeDirectory())); 
	}
}

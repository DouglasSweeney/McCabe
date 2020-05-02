package test.java.main;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.main.Main;
import main.java.main.Options;
import main.java.metrics.Mccabe;

public class MainTest {
	private final static Double EPSILON = 0.01;
	
	Main main;
	
	@Before
	public void setUp() {
		main = new Main();
	}
	
	@After
	public void teardown() {
		main = null;
	}
	
	@Test
	public void checkSingleFile() {
		String[] args = new String[3];
		args[0] = "-m";
		args[1] = "main";
		args[2] = "/home/dks/git/McCabe_v1/McCabe/src/test/TestInput.java";
		Options options = new Options(args);
		
		boolean PRINT_JUST_FILENAME = false;
		Mccabe mcCabe = new Mccabe();
    
		main.run(PRINT_JUST_FILENAME, options);
		
		assertEquals(10, (int)mcCabe.getMethodComplexityFactor("main"));
	}
	
	@Test
	public void checkDirectory() {
		String[] args = new String[3];
		args[0] = "-m";
		args[1] = "main";
		args[2] = "/home/dks/git/McCabe_v1/McCabe/src/main/java";
		Options options = new Options(args);
		
		boolean PRINT_JUST_FILENAME = false;
		Mccabe mcCabe = new Mccabe();
    
		main.run(PRINT_JUST_FILENAME, options);
		
		assertEquals(4.91, mcCabe.getOverallComplexityFactor(), EPSILON);
	}
}

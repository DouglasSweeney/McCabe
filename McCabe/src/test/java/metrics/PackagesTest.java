package test.java.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.main.Main;
import main.java.main.Options;
import main.java.metrics.Packages;
import main.java.scanner.Scanner;
import main.java.scanner.TokenEnum;

public class PackagesTest {
	Packages packages;
	
	@Before
	public void setUp() {
		packages = new Packages();
	}
	
	@After
	public void teardown() {
		packages = null;
	}
	
	@Test
	public void packages__getCurrentIndex() {
		packages.setCurrentIndex(5);
		
		assertEquals((Integer)5, packages.getCurrentIndex()); 
	}
	
	@Test
	public void packages__getList() {
		
		assertNotNull(packages.getList()); 
	}
	
	@Test
	public void packages__print() {
		packages.print(); 
	}
	
	@Test
	public void packages__debugging_print() {
		packages.debugging_print(); 
	}
	
	@Test
	public void packages__HappyCase() {
		Main main = new Main();
		boolean PRINT_JUST_FILENAME = false;
		
    	String[] args = new String[1];
    	args[0] = "/home/dks/git/McCabe_v1/McCabe/src/test/TestInput.java";
    	Options options = new Options(args);

    	packages.clear();
  		main.run(PRINT_JUST_FILENAME, options);
		
		assertEquals(1, packages.size());
	}
}

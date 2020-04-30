package test.java.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.main.Main;
import main.java.main.Options;
import main.java.metrics.SlocNode;
import main.java.metrics.Slocs;

public class SlocsTest {
    Slocs slocs;
	
	@Before
	public void setUp() {
		slocs = new Slocs();
	}
	
	@After
	public void teardown() {
		slocs = null;
	}
	
	@Test
	public void slocs__GetNumberOfSlocsNull() {
		assertEquals(0, slocs.getNumberOfSlocs(null)); 
	}
	
	@Test
	public void slocs__HappyCase() {
		Main main = new Main(); 
		boolean PRINT_JUST_FILENAME = false;
		
    	String[] args = new String[1];
    	args[0] = "/home/dks/git/McCabe_v1/McCabe/src/test/TestInput.java";
    	Options options = new Options(args);

  		main.run(PRINT_JUST_FILENAME, options);
		
		assertEquals(443, slocs.getNumberOfSlocs(args[0]));
	}
	
	@Test
	public void slocs__print() {
		slocs.print(); 
	}
	
	@Test
	public void slocs__debugging_print() {
		slocs.debugging_print(); 
	}
	
	@Test
	public void slocs__getCurrentIndex() {
		slocs.setCurrentIndex(66);
		
		assertEquals((Integer)66, slocs.getCurrentIndex()); 
	}
	
}

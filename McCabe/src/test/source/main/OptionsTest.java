package test.source.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import source.main.Options;

public class OptionsTest {
	@Test
	public void checkOptionExceptions() {
		String[] args = new String[1];
		args[0] = "-exceptions";
		Options options = new Options(args);
    
		assertEquals(true, options.getUseExceptions());
	}
	
	@Test
	public void checkOptionE() {
		String[] args = new String[1];
		args[0] = "-e";
		Options options = new Options(args);
    
		assertEquals(true, options.getUseExceptions());
	}
	
	@Test
	public void checkNoOption() {
		String[] args = new String[1];
		args[0] = "";
		Options options = new Options(args);
    
		assertEquals(false, options.getUseExceptions());
	}
	
	@Test
	public void checkSetAndGMcCabeDirectory() {
		String[] args = new String[1];
		args[0] = "";
		Options options = new Options(args);
		options.setMcCabeDirectory("directory");
   
		assertEquals("directory", options.getMcCabeDirectory());
	}
}

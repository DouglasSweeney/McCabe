package test.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.main.Options;

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
	public void checkOptionM() {
		String[] args = new String[2];
		args[0] = "-m";
		args[1] = "temp.txt";
		Options options = new Options(args);
    
		assertEquals(true, Options.isComputeOnlyOneMethod());
	}
	
	@Test
	public void checkOptionMFilename() {
		String[] args = new String[2];
		args[0] = "-m";
		args[1] = "temp.txt";
		Options options = new Options(args);
    
		assertEquals("temp.txt", Options.getMethodName());
	}
	
	@Test
	public void checkEmptyStringOption() {
		String[] args = new String[1];
		args[0] = "";
		Options options = new Options(args);
    
		assertEquals(false, options.getUseExceptions());
	}
	
	@Test
	public void checkSetAndMcCabeDirectory() {
		String[] args = new String[1];
		args[0] = "";
		Options options = new Options(args);
		
		options.setMcCabeDirectory("directory");
   
		assertEquals("directory", options.getMcCabeDirectory());
	}
	
	@Test
	public void checkMcCabeDirectoryCommandLine() {
		String[] args = new String[1];
		args[0] = "directory";
		Options options = new Options(args);
		
		assertEquals("directory", options.getMcCabeDirectory());
	}
	
	/**
	 * TODO: How to test usage() - a private method
	public void checkNoArgumentsCommandLine() {
		String[] args = new String[0];
		Options options = new Options(args);
		
	}
	*/
}

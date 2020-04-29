package test.java.debug;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.debug.Debug;

public class DebugTest {
	Debug debug;
	
	@Before
	public void setUp() {
		debug = new Debug();
	}
	
	@After
	public void teardown() {
		debug = null;
	}
	
	@Test
	public void checkSetPropertiesOptions() {
		Debug.setProperties();
		
		assertEquals(true, System.getProperty("debug.options") != null);
	}

	@Test
	public void checkSetPropertiesTokens() {
		Debug.setProperties();
		
		assertEquals(true, System.getProperty("debug.tokens") != null);
	}

	@Test
	public void checkSetPropertiesMcCabe() {
		Debug.setProperties();
		
		assertEquals(true, System.getProperty("debug.mccabe") != null);
	}

	@Test
	public void checkSetPropertiesBraces() {
		Debug.setProperties();
		
		assertEquals(true, System.getProperty("debug.braces") != null);
	}

	@Test
	public void checkSetPropertiesInputMethods() {
		Debug.setProperties();
		
		assertEquals(true, System.getProperty("debug.input_methods") != null);
	}

	@Test
	public void checkSetPropertiesInternalMethods() {
		Debug.setProperties();
		
		assertEquals(true, System.getProperty("debug.internal_methods") != null);
	}
	@Test
	public void checkSetPropertiesDoesNotExist() {
		Debug.setProperties();
		
		assertEquals(false, System.getProperty("debug.internal_method") != null);
	}
}

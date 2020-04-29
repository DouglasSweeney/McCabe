package test.debug;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.debug.Categories;

public class CategoriesTest {
	Categories categories;
	
	@Before
	public void setUp() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void checkBracesToString() {
		assertEquals("BRACES", Categories.BRACES.toString());
	}
	
	@Test
	public void checkInputMethodsToString() {
		assertEquals("INPUT_METHODS", Categories.INPUT_METHODS.toString());
	}
	
	@Test
	public void checkInternalMethodsToString() {
		assertEquals("INTERNAL_METHODS", Categories.INTERNAL_METHODS.toString());
	}
	
	@Test
	public void checkMcCabeToString() {
		assertEquals("MCCABE", Categories.MCCABE.toString());
	}
	
	@Test
	public void checkOptionsToString() {
		assertEquals("OPTIONS", Categories.OPTIONS.toString());
	}
	
	@Test
	public void checkTokensToString() {
		assertEquals("TOKENS", Categories.TOKENS.toString());
	}	
}

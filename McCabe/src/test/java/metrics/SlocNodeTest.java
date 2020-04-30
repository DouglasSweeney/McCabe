package test.java.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.metrics.SlocNode;

public class SlocNodeTest {
    SlocNode slocNode;
	
	@Before
	public void setUp() {
		String  filename = "filename";
		Integer lineNumber = 55;
		
		slocNode = new SlocNode(filename, lineNumber);
	}
	
	@After
	public void teardown() {
		slocNode = null;
	}
	
	@Test
	public void slocNode__filename() {
		assertEquals("filename", slocNode.filename); 
	}
	
	@Test
	public void slocNode__lineNumber() {
		assertEquals((Integer)55, slocNode.lineNumber); 
	}	
}

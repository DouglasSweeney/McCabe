package test.java.metrics;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.main.Utils;
import main.java.metrics.MccabeNode;
import main.java.scanner.Token;
import main.java.scanner.TokenEnum;

public class MccabeNodeTest {
	MccabeNode mccabeNode;
	
	@Before
	public void setUp() {
		String  filename = "filename";
		String  className = "className";
		String  methodName = "methodName";
		Integer lineNumber = 0;
		Integer mcCabeComplexityFactor = 1;
	
		mccabeNode = new MccabeNode(filename, className, methodName, lineNumber, mcCabeComplexityFactor);
	}
	
	@After
	public void teardown() {
		mccabeNode = null;
	}
	
	@Test
	public void MccabeNode__getFilename() {
		assertEquals("filename", mccabeNode.getFilename()); 
	}
	
	@Test
	public void MccabeNode__getClassName() {
		assertEquals("className", mccabeNode.getClassName()); 
	}
	
	@Test
	public void MccabeNode__getMethodName() {
		assertEquals("methodName", mccabeNode.getMethodName()); 
	}
	
	@Test
	public void MccabeNode__getLineNumber() {
		assertEquals((Integer)0, mccabeNode.getLineNumber()); 
	}
	
	@Test
	public void MccabeNode__getMcCabeComplexityFactor() {
		assertEquals((Integer)1, mccabeNode.getMccabeComplexityFactor()); 
	}
}

package main.java.metrics;

public class SlocNode {
	public String filename = "";
	public Integer lineNumber = 0;
		
	public SlocNode(String filename, Integer lineNumber) {
		this.filename = filename;
		this.lineNumber = lineNumber;
	}
}

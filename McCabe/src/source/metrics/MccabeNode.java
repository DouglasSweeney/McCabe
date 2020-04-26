package source.metrics;

public class MccabeNode {
	private String    filename;
	private String    className;
	private String    methodName;
	private Integer   lineNumber;
	private Integer   mccabeComplexityFactor = 0;
	
	public MccabeNode(String filename, String className, String methodName, Integer lineNumber, Integer mccabe) {
		this.filename = filename;
		this.className = className;
		this.methodName = methodName;
		this.lineNumber = lineNumber;
		this.mccabeComplexityFactor = mccabe;
	}
	
	public String getFilename()  {
		return filename;
	}
	
	public String getClassName()  {
		return className;
	}
	
	public String getMethodName()  {
		return methodName;
	}
	public Integer getLineNumber()  {
		return lineNumber;
	}
	
	public Integer getMccabeComplexityFactor()  {
		return mccabeComplexityFactor;
	}
}

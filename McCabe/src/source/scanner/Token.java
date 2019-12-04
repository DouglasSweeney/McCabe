package source.scanner;

public class Token {

	public TokenEnum enumeration;
	public String    string;
	public Integer   lineNumber;
	
	public Token(TokenEnum enumeration, int lineNumber) {
		this.enumeration = enumeration;
		this.lineNumber = lineNumber;
	}
	
	public Token(TokenEnum enumeration, String string, int lineNumber) {
		this.enumeration = enumeration;
		this.string = string;
		this.lineNumber = lineNumber;
	}
}

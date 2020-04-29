//
// File:    Scanner.java
// Created: 4/28/2020
// Author:  Douglas Sweeney
//
// History: 
//           v1.0     4/28/2020        Douglas Sweeney modifications to a file found online.
//
package main.java.scanner;

import java.io.FileNotFoundException;
import java.util.Hashtable;

import main.java.main.TokenList;

/**
 * Convert a file to tokens.
 * 
 * @author dks
 * @since 1.0
 */
public class Scanner {

    /** End of file character. */
    public final static char EOFCH = CharReader.EOFCH;

    /** Keywords in j--. */
    private Hashtable<String, TokenEnum> reserved;

    /** Source characters. */
    private CharReader input;

    /** Next unscanned character. */
    private char ch;

    /** Whether a scanner error has been found. */
    private boolean isInError;

    /** Source file name. */
    private String fileName;

    /** lineNumber number of current token. */
    private int lineNumber;

    /**
     * Construct a Scanner object.
     * 
     * @param fileName
     *            the name of the file containing the source.
     * @exception FileNotFoundException
     *                when the named file cannot be found.
     */
    public Scanner(String fileName) throws FileNotFoundException {
        this.input = new CharReader(fileName);
        this.fileName = fileName;
        isInError = false;

        // Keywords
        reserved = new Hashtable<String, TokenEnum>();
        assert reserved != null : Scanner.class.getCanonicalName() +
        		                  "constructor: reserved == null";
        reserved.put("abstract", TokenEnum.ABSTRACT);
        reserved.put("assert", TokenEnum.ASSERT);
        reserved.put("boolean", TokenEnum.BOOLEAN);
        reserved.put("break", TokenEnum.BREAK);
        reserved.put("byte", TokenEnum.BYTE);
        reserved.put("case", TokenEnum.CASE);
        reserved.put("catch", TokenEnum.CATCH);
        reserved.put("char", TokenEnum.CHAR);
        reserved.put("class", TokenEnum.CLASS);
        reserved.put("const", TokenEnum.CONST);
        reserved.put("continue", TokenEnum.CONTINUE);
        reserved.put("default", TokenEnum.DEFAULT);
        reserved.put("do", TokenEnum.DO);
        reserved.put("double", TokenEnum.DOUBLE);
        reserved.put("else", TokenEnum.ELSE);
        reserved.put("enum", TokenEnum.ENUM);
        reserved.put("extends", TokenEnum.EXTENDS);
        reserved.put("false", TokenEnum.FALSE);
        reserved.put("final", TokenEnum.FINAL);
        reserved.put("finally", TokenEnum.FINALLY);
        reserved.put("float", TokenEnum.FLOAT);
        reserved.put("for",  TokenEnum.FOR);
        reserved.put("if", TokenEnum.IF);
        reserved.put("goto", TokenEnum.GOTO);
        reserved.put("implements", TokenEnum.IMPLEMENTS);
        reserved.put("import", TokenEnum.IMPORT);
        reserved.put("instanceof", TokenEnum.INSTANCEOF);
        reserved.put("int", TokenEnum.INT);
        reserved.put("interface", TokenEnum.INTERFACE);
        reserved.put("long", TokenEnum.LONG);
        reserved.put("native", TokenEnum.NATIVE);
        reserved.put("new", TokenEnum.NEW);
        reserved.put("null", TokenEnum.NULL);
        reserved.put("package", TokenEnum.PACKAGE);
        reserved.put("private", TokenEnum.PRIVATE);
        reserved.put("protected", TokenEnum.PROTECTED);
        reserved.put("public", TokenEnum.PUBLIC);
        reserved.put("return", TokenEnum.RETURN);
        reserved.put("short", TokenEnum.SHORT);
        reserved.put("static", TokenEnum.STATIC);
        reserved.put("strictfp", TokenEnum.STRICTFP);
        reserved.put("super", TokenEnum.SUPER);
        reserved.put("switch", TokenEnum.SWITCH);
        reserved.put("synchronized", TokenEnum.SYNCHRONIZED);
        reserved.put("this", TokenEnum.THIS);
        reserved.put("throw", TokenEnum.THROW);
        reserved.put("throws", TokenEnum.THROWS);
        reserved.put("transient", TokenEnum.TRANSIENT);
        reserved.put("true", TokenEnum.TRUE);
        reserved.put("try", TokenEnum.TRY);
        reserved.put("void", TokenEnum.VOID);
        reserved.put("volatile", TokenEnum.VOLATILE);
        reserved.put("while", TokenEnum.WHILE);
        
        reserved.put("@Override", TokenEnum.OVERRIDE_ANNOTATION);
        reserved.put("@Deprecated", TokenEnum.DEPRECATED_ANNOTATION);
        reserved.put("@SuppressWarnings", TokenEnum.SUPPRESSWARNINGS_ANNOTATION);

        // Prime the pump.
        nextCh();
    }

    /**
     * Scan the next valid token from input.
     * 
     * @return the the next scanned valid token.
     */
    public Token getNextToken() {
    	Token token = new Token(TokenEnum.NONE, "", lineNumber);

    	while (token.enumeration == TokenEnum.NONE) {
    		token = getNextValidToken();
    	}
    	
    	return token;
    }
    
    /**
     * Scan the next token from input.
     * 
     * @return the the next scanned token.
     */
    public Token getNextValidToken() {
    	boolean endOfCommentFound = false;
    	char sign = ' ';     	
        StringBuffer buffer;

        while (isWhitespace(ch)) {
        	nextCh();
        }
        
        sign = ' ';
        lineNumber = input.lineNumber();
        switch (ch) {
        case '~':
            nextCh();
            return new Token(TokenEnum.TILDE, lineNumber);
        case '(':
            nextCh();
            return new Token(TokenEnum.LPAREN, lineNumber);
        case ')':
            nextCh();
            return new Token(TokenEnum.RPAREN, lineNumber);
        case '{':
            nextCh();
            return new Token(TokenEnum.LBRACE, lineNumber);
        case '}':
            nextCh();
            return new Token(TokenEnum.RBRACE, lineNumber);
        case '[':
            nextCh();
            return new Token(TokenEnum.LBRACKET, lineNumber);
        case ']':
            nextCh();
            return new Token(TokenEnum.RBRACKET, lineNumber);
        case ';':
            nextCh();
            return new Token(TokenEnum.SEMICOLON, lineNumber);
        case ',':
            nextCh();
            return new Token(TokenEnum.COMMA, lineNumber);
        case '.':
        	nextCh();
   			return new Token(TokenEnum.PERIOD, lineNumber);
        case '?':
            nextCh();
            return new Token(TokenEnum.QUESTION_MARK, lineNumber);
        case ':':
            nextCh();
            return new Token(TokenEnum.COLON, lineNumber);
        case '=':
            nextCh();
            if (ch == '=') {
                nextCh();
                return new Token(TokenEnum.EQUAL_EQUAL, lineNumber);
            } else {
                return new Token(TokenEnum.EQUALS, lineNumber);
            }
        case '!':
        	nextCh();
            if (ch == '=') {
                nextCh();
                return new Token(TokenEnum.NOT_EQUALS, lineNumber);
            } else {
                return new Token(TokenEnum.NOT, lineNumber);
            }
        case '|':
        	nextCh();
            if (ch == '=') {
                nextCh();
                return new Token(TokenEnum.OR_EQUALS, lineNumber);
            } else if (ch == '|') {
            	nextCh();
                return new Token(TokenEnum.OROR, lineNumber);
            } else {
                return new Token(TokenEnum.OR, lineNumber);
           	
            }
       case '^':
    	   nextCh();
            if (ch == '=') {
                nextCh();
                return new Token(TokenEnum.CARET_EQUALS, lineNumber);
            } else {
            	nextCh();
                return new Token(TokenEnum.CARET, lineNumber);
            }
        case '%':
        	nextCh();
            if (ch == '=') {
                nextCh();
                return new Token(TokenEnum.MOD_EQUALS, lineNumber);
            } else {
            	nextCh();
                return new Token(TokenEnum.MOD, lineNumber);
            }
        case '*':
        	nextCh();
            if (ch == '=') {
                nextCh();
                return new Token(TokenEnum.MULTIPLY_EQUALS, lineNumber);
            } else {
                return new Token(TokenEnum.ASTERISK, lineNumber);
            }
        case '/':
        	nextCh();
            if (ch == '=') {
                nextCh();
                return new Token(TokenEnum.DIVIDE_EQUALS, lineNumber);
            } else {
                if (ch == '/') {
                   // CharReader maps all new lineNumbers to '\n'
                   while (ch != '\n' && ch != EOFCH) { //single line command
                       nextCh();
                   }
                   return new Token(TokenEnum.NONE, lineNumber);
               }  
                else {
                   if (ch == '*') { // multiline comment
              	       nextCh();
              	       endOfCommentFound = false;
                       while (ch != EOFCH && !endOfCommentFound) { // scan though comment
                    	    if (ch == '*') {
                    	    	nextCh();
                    	    	if (ch == '/') { // end of multilineNumber comment
                    	    		endOfCommentFound = true;
                    	    		nextCh();
                    	    	} // if                    	    		
                            }
                    	    else {
                    	    	nextCh();
                    	    }
                       } // while
                       return new Token(TokenEnum.NONE, lineNumber);
                   } // if
                   else {
                       return new Token(TokenEnum.DIVIDE, lineNumber);
                   }
                } 
            }
        case '+':
            nextCh();
            if (ch == '=') {
                nextCh();
                return new Token(TokenEnum.PLUS_EQUALS, lineNumber);
            } else 
            if (ch == '+') {
                nextCh();
                return new Token(TokenEnum.INCREMENT, lineNumber);
            } else {
            	switch (ch) {
            		case '1':
            		case '2':
            		case '3':
            		case '4':
            		case '5':
            		case '6':
            		case '7':
            		case '8':
            		case '9':
            			buffer = new StringBuffer();
            			sign = '+';
            			return decimalLiteral(sign, buffer);
            		default:
            			nextCh();
            			return new Token(TokenEnum.PLUS, lineNumber);
            	}
            }
        case '-':
            nextCh();
            if (ch == '-') {
                nextCh();
                return new Token(TokenEnum.DECREMENT, lineNumber);
            } else {
            	switch (ch) {
        		case '1':
        		case '2':
        		case '3':
        		case '4':
        		case '5':
        		case '6':
        		case '7':
        		case '8':
        		case '9':
        			buffer = new StringBuffer();
        			sign = '-';
        			return decimalLiteral(sign, buffer);
        		default:
        			nextCh();
        			return new Token(TokenEnum.MINUS, lineNumber);
            	}
            }
        case '&':
            nextCh();
            if (ch == '&') {
                nextCh();
                return new Token(TokenEnum.AMPERSAND_AMPERSAND, lineNumber);
            } else {
                return new Token(TokenEnum.AMPERSAND, lineNumber);
            }
        case '>':
            nextCh();
            if (ch == '=') {
                nextCh();
                return new Token(TokenEnum.GT_EQUALS, lineNumber);
            } else
            if (ch == '>') {
                nextCh();
                if (ch == '>') {
                    nextCh();
                    if (ch == '=') {
                        nextCh();
                    	return new Token(TokenEnum.UNSIGNED_SHIFT_RIGHT_EQUALS, lineNumber);
                    }
                    else {
                       	return new Token(TokenEnum.UNSIGNED_SHIFT_RIGHT, lineNumber);
                    }
                } else 
                    if (ch == '=') {
                        nextCh();
                    	return new Token(TokenEnum.SHIFT_RIGHT_EQUALS, lineNumber);
                    }
                    else {
                       	return new Token(TokenEnum.SHIFT_RIGHT, lineNumber);
                    }
            }  else 
            	return new Token(TokenEnum.GT, lineNumber);
        case '<':
            nextCh();
            if (ch == '=') {
                nextCh();
                return new Token(TokenEnum.LESS_EQUALS, lineNumber);
            } else {
            	if (ch == '<') {
            		nextCh();
                    if (ch == '=') {
                    	nextCh();
                    	return new Token(TokenEnum.SHIFT_LEFT_EQUALS, lineNumber);
                    }
                    else {
                        return new Token(TokenEnum.SHIFT_LEFT, lineNumber);
                    }
                }
                else {
                    return new Token(TokenEnum.LESS, lineNumber);
                }
            }          
        case '\'':
            buffer = new StringBuffer();
            buffer.append('\'');
            nextCh();
            if (ch == '\\') {
                nextCh();
                buffer.append(escape());
            } else {
                buffer.append(ch);
                nextCh();
            }
            if (ch == '\'') {
                buffer.append('\'');
                nextCh();
                return new Token(TokenEnum.CHAR, buffer.toString(), lineNumber);
            } else {
                // Expected a ' ; report error and try to
                // recover.
                reportScannerError(ch
                        + " found by scanner where closing ' was expected.");
                while (ch != '\'' && ch != ';' && ch != '\n') {
                    nextCh();
                }
                return new Token(TokenEnum.CHAR, buffer.toString(), lineNumber);
            }
        case '"':
            buffer = new StringBuffer();
            buffer.append("\"");
            nextCh();
            while (ch != '"' && ch != '\n' && ch != EOFCH) {
                if (ch == '\\') {
                    nextCh();
                    buffer.append(escape());
                } else {
                    buffer.append(ch);
                    nextCh();
                }
            }
            if (ch == '\n') {
                reportScannerError("Unexpected end of lineNumber found in String");
            } else if (ch == EOFCH) {
                reportScannerError("Unexpected end of file found in String");
            } else {
                // Scan the closing "
                nextCh();
                buffer.append("\"");
            }
            return new Token(TokenEnum.STRING_LITERAL, buffer.toString(), lineNumber);
        case EOFCH:
        	nextCh();
            return new Token(TokenEnum.EOF, lineNumber);
        case '0':
            nextCh();
            buffer = new StringBuffer();
            buffer.append('0');
            
            if (ch == 'x' || ch == 'X') {
            	buffer.append(ch);
            	nextCh();
            	while (isHexDigit(ch)) {
            		buffer.append(ch);
            		nextCh();           		
            	}
 	            return new Token(TokenEnum.BASE16_INTEGER, buffer.toString(), lineNumber);
            }
            else
            if (ch == 'b' || ch == 'B') {
            	buffer.append(ch);
            	nextCh();
            	while (isBinaryDigit(ch)) {
            		buffer.append(ch);
            		nextCh();
            		
            	}
 	            return new Token(TokenEnum.BASE2_INTEGER, buffer.toString(), lineNumber);
           }
            else {
                if (ch == 'f' || ch == 'F' || ch == '.') {
                	if (ch == '.') {
                		buffer.append(ch);
                		nextCh();
                	}
                	
                	while (isDigit(ch)) {
                		buffer.append(ch);
                		nextCh();
                		
                	}
                    if (ch == 'f' || ch == 'F') {
                        buffer.append(ch);
                        nextCh();
         	            return new Token(TokenEnum.FLOAT, buffer.toString(), lineNumber);
                    }
                    else
         	            return new Token(TokenEnum.DOUBLE, buffer.toString(), lineNumber);
                }
                else
            	while (isOctalDigit(ch)) {
            		buffer.append(ch);
            		nextCh();
            	}
 	            return new Token(TokenEnum.BASE8_INTEGER, buffer.toString(), lineNumber);
            }
            
//          buffer = new StringBuffer();
  case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
        	buffer = new StringBuffer();
        	return decimalLiteral(sign, buffer);
        default:
           	buffer = new StringBuffer();
            if (ch == '@') {
            	buffer.append(ch);
            	nextCh();
            }
            if (isIdentifier(ch)) {
                while (isIdentifierPartial(ch)) {
                    buffer.append(ch);
                    nextCh();
                }
                String identifier = buffer.toString();
                if (reserved.containsKey(identifier)) {
                    return new Token(reserved.get(identifier), lineNumber);
                } else {
                    return new Token(TokenEnum.IDENTIFIER, identifier, lineNumber);
                }
            } else {
            	if (ch != '\n')
            		reportScannerError("Unidentified input token: '%c'", ch);
                nextCh();
                return getNextToken();
            }
        }
   }

    /**
     *  Build a INT, FLOAT or a DOUBLE
     *
     *  @params void
     *
     *  @returns Token
     */
    public Token decimalLiteral(char sign, StringBuffer buffer) {
    	// Add in the sign
    	if (sign == '-' || sign == '+')
    		buffer.append(sign);
    	
        // Get the integer part of a number
        while (isDigit(ch)) {
            buffer.append(ch);
            nextCh(); 
        }
       
        // its a floating point number
        if (ch == '.' || ch == 'e' || ch == 'E') {
            if (ch == '.') {
                nextCh();
                if ((ch == 'e' || ch == 'E') && buffer.length() == 0)
    				return new Token(TokenEnum.PERIOD, buffer.toString(), lineNumber);
                if ((ch == 'f' || ch == 'F') && buffer.length() == 0)
    				return new Token(TokenEnum.PERIOD, buffer.toString(), lineNumber);
                buffer.append('.');
            }
            // Get the fractional part of the number
            while (isDigit(ch)) {
                buffer.append(ch);
                nextCh(); 
            }
            if (ch == 'e' || ch == 'E' || ch == 'f' || ch == 'F' || ch == 'd' || ch == 'D') {
            	if (ch == 'e' || ch == 'E' ) {
            		buffer.append(ch);
                    nextCh();
            	}
            	
                if (ch == '+' || ch == '-') {
                    buffer.append(ch);
                    nextCh();
                }
                // Get the exponent part of the number
                while (isDigit(ch)) {
                	buffer.append(ch);
                	nextCh();
                }
                
                // make the number a float
                if (ch == 'f' || ch == 'F') {
                    buffer.append(ch);
                    nextCh();
         	        return new Token(TokenEnum.FLOAT, buffer.toString(), lineNumber);
                } 
                else {
                	// make the number a double
                	if (ch == 'd' || ch == 'D') {
                		buffer.append(ch);
                		nextCh();
                		return new Token(TokenEnum.DOUBLE, buffer.toString(), lineNumber);
                	}
                	else {
                		if (buffer.length() > 1) {
                			return new Token(TokenEnum.DOUBLE, buffer.toString(), lineNumber);
                		}
                		else {
                			if (buffer.length() == 1 && buffer.charAt(0) == '.') {
                				return new Token(TokenEnum.PERIOD, buffer.toString(), lineNumber);
                			}
                		}
                	}
                }
            }
            else {
        		if (buffer.length() > 1) {
        			if ((buffer.indexOf("d") > 0) || (buffer.indexOf("D") > 0)) 
        			   return new Token(TokenEnum.DOUBLE, buffer.toString(), lineNumber);
        			else
        			if ((buffer.indexOf("f") > 0) || (buffer.indexOf("F") > 0)) 
         			   return new Token(TokenEnum.FLOAT, buffer.toString(), lineNumber);
        			else
     			       return new Token(TokenEnum.DOUBLE, buffer.toString(), lineNumber);
        		}
        		else {
        			if (buffer.length() == 1 && buffer.charAt(0) == '.') {
        				return new Token(TokenEnum.PERIOD, buffer.toString(), lineNumber);
        			}
        		
        			return new Token(TokenEnum.EOF, buffer.toString(), lineNumber);
        		}
            }           
        }      
        else {
            if (ch == 'l' || ch == 'L') {
            	buffer.append(ch);
            	nextCh();
        	    return new Token(TokenEnum.LONG_LITERAL, buffer.toString(), lineNumber); 
            }
            
    	    return new Token(TokenEnum.INT, buffer.toString(), lineNumber); 
        }
        
		return null;
    }

    // Add to case '.' & cases 1..9. Call is "return decimalLiteral()"
    /**
     * Scan and return an escaped character.
     * 
     * @return escaped character.
     */

    private String escape() {
        switch (ch) {
        case 'b':
            nextCh();
            return "\\b";
        case 't':
            nextCh();
            return "\\t";
        case 'n':
            nextCh();
            return "\\n";
        case 'f':
            nextCh();
            return "\\f";
        case 'r':
            nextCh();
            return "\\r";
        case '"':
            nextCh();
            return "\"";
        case '\'':
            nextCh();
            return "\\'";
        case '\\':
            nextCh();
            return "\\\\";
        default:
            reportScannerError("Badly formed escape: \\%c", ch);
            nextCh();
            return "";
        }
    }

    /**
     * Advance ch to the next character from input, and update the lineNumber number.
     */

    private void nextCh() {
        lineNumber = input.lineNumber();
        try {
            ch = input.nextChar();
        } catch (Exception e) {
            reportScannerError("Unable to read characters from input");
        }
    }

    /**
     * Report a lexcial error and record the fact that an error has occured.
     * This fact can be ascertained from the Scanner by sending it an
     * errorHasOccurred() message.
     * 
     * @param message
     *            message identifying the error.
     * @param args
     *            related values.
     */

    private void reportScannerError(String message, Object... args) {
        isInError = true;
        System.err.printf("%s:%d: ", fileName, lineNumber);
        System.err.printf(message, args);
        System.err.println();
    }

    /**
     * Return true if the specified character is a hex digit; false otherwise.
     * 
     * @param c
     *            character.
     * @return true or false.
     */

    private boolean isHexDigit(char c) {
    	boolean ok = false;
        switch(c) {
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'l':
        case 'L':
        case '_':
        	ok = true;
        	break;
        }
    
        return ok;
    }

    /**
     * Return true if the specified character is a octal digit; false otherwise.
     * 
     * @param c
     *            character.
     * @return true or false.
     */

    private boolean isOctalDigit(char c) {
    	boolean ok = false;
        switch(c) {
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case 'l':
        case 'L':
        case '_':
        	ok = true;
        	break;
        }
    
        return ok;
    }

    /**
     * Return true if the specified character is a binary digit; false otherwise.
     * 
     * @param c
     *            character.
     * @return true or false.
     */

    private boolean isBinaryDigit(char c) {
    	boolean ok = false;
        switch(c) {
        case '0':
        case '1':
        case 'l':
        case 'L':
        case '_':
        	ok = true;
        	break;
        }
    
        return ok;
    }

    /**
     * Return true if the specified character is a digit (0-9); false otherwise.
     * 
     * @param c
     *            character.
     * @return true or false.
     */

    private boolean isDigit(char c) {
        return ((c >= '0' && c <= '9') || ch == '_');
    }

   /**
     * Return true if the specified character is a whitespace; false otherwise.
     * 
     * @param c
     *            character.
     * @return true or false.
     */

    private boolean isWhitespace(char c) {
        switch (c) {
        case ' ':
        case '\t':
        case '\n': // CharReader maps all new lineNumbers to '\n'
        case '\f':
            return true;
        }
        return false;
    }

    /**
     * Return true if the specified character can start an identifier name;
     * false otherwise.
     * 
     * @param c
     *            character.
     * @return true or false.
     */

    private boolean isIdentifier(char c) {
        return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_' || c == '$');
    }

    /**
     * Return true if the specified character can be part of an identifier name;
     * false otherwise.
     * 
     * @param c
     *            character.
     * @return true or false.
     */

    private boolean isIdentifierPartial(char c) {
        return (isIdentifier(c) || isDigit(c));
    }

    /**
     * Has an error occurred up to now in lexical analysis?
     * 
     * @return true or false.
     */

    public boolean errorHasOccurred() {
        return isInError;
    }

    /**
     * The name of the source file.
     *     	token = scanner.getNextToken();
    	System.out.println(token.enumeration);

     * @return name of the source file.
     */

    public String fileName() {
        return fileName;
    }

    public static void main(String[] args) {
    	Token token = null;
    	Scanner scanner = null;
    	TokenList tokenList = new TokenList();
    	
		try {
			scanner = new Scanner("src/jplusplus/scanner/TestInput.java");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	token = scanner.getNextToken();
    	System.out.println(token.enumeration);
    	while  (token.enumeration != TokenEnum.EOF) {
    		token = scanner.getNextToken();
    		tokenList.add(token);
    	}
    	tokenList.print(tokenList.getList());
    }
}

package test;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import main.java.scanner.Scanner;
/**
 * @author dks
 *
 */
public class TestInput extends Scanner implements FocusListener {

	private transient Integer bbbbbb = 0;
	volatile Long ccccccc = new Long(0);
	static int aaaaaaaaa;
	private final int ZERO = 0;
	
	public TestInput() throws IOException { // 1 procedure mccabe=1 for start of procedure 
		super("");                          //             mccabe=2 for "throws"
		boolean found = false;
		
		do {                                //             mccabe=3 for do/while
			found = true;
		} while (!found);
		
	}
	
	synchronized private void forStatement() { // 2 procedure mccabe=1 for start of procedure
		int i;
		int num = 15; // comment
		
		for (i = 0; i == 0; i++)               // mccabe=2 for "for" 
			num--;
		for (i = 0; i>10; i++) {               // mccabe=3 for "for"
			num--;
		}
		for (i = 0; i<10; i++) {               // mccabe=4 for "for"
			num--;
		}
        for (int j = 0; j<10; j++) {           // mccabe=5 for "for"
        	num--;
        	continue;                          // mccabe=6 for continue"
        }
	}
	
	protected boolean booleanStatement() { // 3 procedure mccabe=1 for start of procedure
		boolean bool = false;
		
		bool = true;
		bool = false;
		
		return true;
	}
	
	private Long charMethod() { // 4 proceudure mccabe=1 for start of procedure
		char ch = 'A';
		char ch2 = '\n';
		
		return 1l;
	}
	
	private void ifStatement(boolean bool) { // 5 procedure mccabe=1 for start of procedure
		int i = 0;
		int ZERO = 0;
		
		if (i == 0)                          // mccabe=2 for "if" stmt
			i += 1;
		else {                               // mccabe=3 for "else" stmt
			i = 2;
		}
		
		if (i == ZERO)                       // mccabe=4 for "if" stmt
			i += 1;
		else {                               // mccabe=5 for "else" stmt
			i = 2;
		}
		
		if (bool)                            // mccabe=6 for "if" stmt
			i += 1;
		else {                               // mccabe=7 for "else" stmt
			i = 2;
		}
		
		if (bool && true)                    // mccabe=9 for "if" stmt && "&&"
			i += 1;
		else {                               // mccabe=10 for "else" stmt
			i = 2;
		}
		
		return;
	}
	
	private void whileStatement() throws IOException { // 6 procedure mccabe=1 for start of procedure
		TestInput p = null;                            //             mccabe=2 for "throws"
		
		while (!(p == null)) {                         //             mccabe=3 for "while"
			p = new TestInput();
	    	break;                                     //             mccabe=4 for "break"
     	}
		
		System.out.println(this);
		System.out.println(p);
	}
	
	public void operators() { // 7 procedure mccabe=1 for start of procedure
		int i = 0;
		i = i * 1;
		i = i + 1;
		i--;
		--i;
		i = i - 1;
		if (i <= 0) {         // mccabe=2 for "if" stmt
			i = 1;
		}
		else                  // mccabe=3 for "else" stmt
		if (i > 2) {          // mccabe=4 for "if" stmt
	    	i = 2;
	    }
	    else                  // mccabe=5 for "else" stmt 
	    if (!false) {         // mccabe=6 for "if" stmt
	    	i = 3;
	    }
		assert (i > 0);       
	}
	
	private void charLiteral() { // 8 procedure mccabe=1 for start of procedure
		char ch;
		
		ch = 'a';
		ch = '%';
		ch = '\t';
		ch = '\\';
		ch = '\'';
	}
	
	private void stringLiteral() { // 9 procedure mccabe=1 for start of procedure
		Integer i = new Integer(5);
		
		System.out.println("Hello World.");
		i = 0x00;
		System.out.println(i);
	}
	
	private void base2_Integer() { // 10 mccabe=1 for start of procedure
		int Integer;
		
		Integer = 0b0000_0011;
		Integer = 0B00_00_11_11;
	}
	
	private void base8_Integer() { // 11 mccabe=1 for start of procedure
		int Integer;
		
		Integer = 0;
		Integer = 00_00_00_11;
		Integer = 01777;
	}
	
	private void base10_Integer() { // 12 mccabe=1 for start of procedure
		int Integer;
		
		Integer = 0;
		Integer = 2;
		Integer = 1_996;
		Integer = -1_996;
		Integer = +1_996;
	}
	
	private void base16_Integer() { // 13 mccabe=1 for start of procedure
		int Integer;
		
		Integer = 0x0000_0011;
		Integer = 0X00_00_11_11;
	}
	
	private void longs() { // 14 mccabe=1 for start of procedure
		long Long;
		
		Long = 0l;
		Long = 0777L;
	    Long = 0x00000000L;
		Long = 2_147_483_647L;
		Long = 0xC0B0L;	
	}
	
	private void floatLiteral() { // 15 mccabe=1 for start of procedure
	    float Float;
		
		Float = 1e1f;
		Float = 2.f;
		Float = .3f;
		Float = 0f;
		Float = 3.14f;
		Float = 6.022137e+23F;
	}
	
	private void doubleLiteral() { // 16 mccabe=1 for start of procedure
		double d;
		
		d = 1e1;
		d = 2.;
		d = .3;
		d = 0.0;
		d = 3.14;
		d = 1e-9d;
		d = 1e+9D;
		d = 1e137;
	}
	
	private void comma(int i, int j) { // 17 mccabe=1 for start of procedure
		return;
	}
	
	private List<Integer> arrays() { // 18 mccabe=1 for start of procedure
        List<Integer> array = new ArrayList();
        
        array.add(0);
        
        return array;
    }
        
	public String analyze(String context) { // 19 mccabe=1 for start of procedure
		return null;
	}
	
	public void comments() { // 20 mccabe=1 for start of procedure
		/** ;
        */
		// ;
	}
	
	
	public void tilde() { // 21 mccabe=1 for start of procedure
		int Integer; 
		
		Integer = ~0x8000;
	}
	
	public void shifts() { // 22 mccabe=1 for start of procedure
		int a = 0;
		
		a <<= 0;
		a >>= 1;
		a = 0x0001 >> 1;
        a = 0x1000 >>> 6;
		a >>>= 1;
		a = 0x0001 << 1;
	}
	
	public void other() { // 23 mccabe=1 for start of procedure
		short    a = 5;
		boolean  bool = true;
		byte     Byte;
		double   Double;
		long     Long;
		short    Short;

		String s = null;
		if (s instanceof String)         // mccabe=2 for "if" stmt
			throw new SecurityException(""); // mccabe=3 for "if" stmt
	}
	
	public void other2() { // 23 mccabe=1 for start of procedure
		short    a = 5;
		boolean  bool;
		byte     Byte;
		double   Double;
		long     Long;
		short    Short;
		
		bool = a < 10 ? true : false;    // mccabe=3 for "?/:" stmt
		bool |= true;
		if (bool != true) {              // mccabe=4 for "if" stmt
			a = 6;
		}
		if (!bool) {                     // mccabe=5 for "if" stmt
			bool = true;
		}
		if (true || false) {             // mccabe=7 for "if/||" stmt
			a |= 0x02;
		}
	}
	
	public void other3() { // 23 mccabe=1 for start of procedure
		short    a = 5;
		boolean  bool = true;
		byte     Byte;
		double   Double;
		long     Long;
		short    Short;
		
		a ^= 2;
		a %= 2;
		a *= 2;
		a /= 2;
		if (a >= 2) {                    // mccabe=2 for "if" stmt
			bool = true;
		};
		
		try {
			a = 1 ^ 2;
			a = 1 + 1;
			a = 2 - 1;
			a = 1 & 1;
		} catch (Exception e) {          // mccabe=3 for "if" stmt
		   a = 0;
	    } finally {                      // mccabe=4 for "if" stmt
	    	a = 0;
	    }
		
		if (a % 1 == 0) {                // mccabe=5 for "if" stmt
			bool = true;
		}
		if (a / 2 == 0) {                // mccabe=6 for "if" stmt
			bool = true;
		}
	}
	
	public void other4() { // 23 mccabe=1 for start of procedure
		short    a = 5;
		boolean  bool = true;
		byte     Byte;
		double   Double;
		long     Long;
		short    Short;
		
		switch (a) {
			case 0:                      // mccabe=2 for "case" stmt
				break;                   // mccabe=3 for "break" stmt
		    default:                     // mccabe=4 for "default" stmt
		    	break;                   // mccabe=5 for "break" stmt
		}
		
		do {
			;
		} while (bool);                  // mccabe=6 for "do/while" stmt
	}
	
	public native void helloWorld(); // 24 mccabe=1 for start of procedure

	@Override
	public void focusGained(FocusEvent arg0) { // 25 mccabe=1 for start of procedure
		// TODO Auto-generated method stub	
	}

	@Override
	public void focusLost(FocusEvent arg0) { // 26 mccabe=1 for start of procedure
		// TODO Auto-generated method stub
	}
	
	abstract class AbstractClass { 
		@SuppressWarnings("unused")
		int i;
		
		public AbstractClass() { // procedure 26.5 mccabe=1 for start of procedure
			
		}
	}
	
	interface Interface { 
		
	}
	
	@Deprecated
	strictfp class StrictfpClass {
	}
	
	enum Enum {
	}
	
	public long subProcedure() throws FileNotFoundException { // 27 mccabe=1 for start of procedure
		Integer i;                                            //    mccabe=2 for "throws" stmt
		
		try {                                                
			String filename = "filename.txt";
		}
		catch (Exception e) {                                 // mccabe=3 for "catch" stmt
			throw new FileNotFoundException();                // mccabc=4 for "throw" stmt
		}
		finally {                                             // mccabe=5 for "finally" stmt
			i = 2;
		}
		
		return 1l;
	}
	
	public static void main3() { // 28 procedure mccabe=1 for start of procedure
		Integer j = 0;
		boolean found;
		
		do {                                 // mccabe=2 for "do/while" stmt
			found = true;
			do {                             // mccabe=3 for "do/while" stmt
				j++;
			} while (j > 0);
			while (found && j >= 0) {        // mccabe=5 for "while" and "&&"
				j++;
			}
		} while (!found);		
	}
	
	public static void main2() { // 29 procedure mccabe=1 for start of procedure
		Integer j = 0;
		boolean found;
		
		do {                                 // mccabe=2 for "do/while" stmt
			found = true;
			while (found && j >= 0) {        // mccabe=4 for "while" and "&&"
				j++;
			}
		} while (!found);
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) { // 30 procedure mccabe=1 for start of procedure
		Integer j = 0;
		boolean found;
		

		
		if (true)                            // mccabe=2 for "if" stmt
		   return;                           // mccabe=3 for "return" not last stmt
		
		for (int i=0; i<10; i++)             // mccabe=4 for "for" stmt
			continue;                        // mccabe=5 for "continue" stmt;
		
		do {                                 // mccabe=6 for "do/while" stmt
			found = true;
		} while (!found);
		
		while (found && j >= 0) {            // mccabe=8 for "while" and "&&"
			j++;
		}
		found = (true) ? false : true;       // mccabe=10 for "?" and ":"

		return; 
	}
}
package test.java.main;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.main.Metrics;
import main.java.main.Options;
import main.java.main.TokenList;
import main.java.scanner.Token;
import main.java.scanner.TokenEnum;

public class TokenListTest {
	TokenList tokenList;
	
	@Before
	public void setUp() {
		tokenList = new TokenList();
	}
	
	@After
	public void teardown() {
		tokenList = null;
	}
	
	@Test
	public void setGetCurrentIndex() {
		tokenList.setCurrentIndex(5);
		assertEquals(5, (int)tokenList.getCurrentIndex());
	}
	
	@Test
	public void testOfClone() {
	  TokenList list = new TokenList();
	  List<Token> newList = new LinkedList<Token>();
	   
	  list.add(new Token(TokenEnum.GT, 0));
	  list.add(new Token(TokenEnum.PLUS, 0));
	  list.print(list.getList());
	  newList = list.clone();
	  
	  assertEquals(2, newList.size());
	}
}

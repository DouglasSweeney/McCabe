package test.java.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.main.Main;
import main.java.main.Options;
import main.java.metrics.Tests;

public class TestsTest {
    Tests tests;
    
    @Before
    public void setUp() {
        tests = new Tests();
    }
    
    @After
    public void teardown() {
        tests = null;
    }
    
    @Test
    public void Tests_getNumberOfTests() {
        Main main = new Main(); 
        boolean PRINT_JUST_FILENAME = false;
        
        String[] args = new String[1];
        args[0] = "/home/dks/git/McCabe_v1/McCabe/src/test/java/debug";
        Options options = new Options(args);

        main.run(PRINT_JUST_FILENAME, options);
        
        assertEquals(13, tests.getNumberOfTests());
    }
}

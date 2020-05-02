package test.java.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.main.Main;
import main.java.main.Options;
import main.java.metrics.CountFiles;
import main.java.metrics.Tests;

public class CountFilesTest {
    CountFiles files;
    
    @Before
    public void setUp() {
        files = new CountFiles();
    }
    
    @After
    public void teardown() {
        files = null;
    }
    
    @Test
    public void CountFiles_getNumberOfTests() {
        Main main = new Main(); 
        boolean PRINT_JUST_FILENAME = false;
        
        String[] args = new String[1];
        args[0] = "/home/dks/git/McCabe_v1/McCabe/src/test/java/debug";
        Options options = new Options(args);

        main.run(PRINT_JUST_FILENAME, options);
        
        assertEquals(2, files.getNumberOfFiles());
    }

}

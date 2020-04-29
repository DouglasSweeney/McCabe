package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({test.java.debug.CategoriesTest.class,
	           test.java.debug.DebugTest.class,
	         
	           test.java.main.MainTest.class,
	           test.java.main.MetricsTest.class,
	           test.java.main.OptionsTest.class,
	           test.java.main.TokenListTest.class,
	           
	           test.java.metrics.McCabeTest.class,
	           
	           test.java.scanner.ScannerTest.class})

public class AllTests {

}

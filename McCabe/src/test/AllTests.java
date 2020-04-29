package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({test.debug.CategoriesTest.class,
	
	           test.main.OptionsTest.class,
	
	           test.metrics.McCabeTest.class,
	           
	           test.scanner.ScannerTest.class})
public class AllTests {

}

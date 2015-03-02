package tp.pr3.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tp.pr3.control.test.ControladorTest;

@RunWith(Suite.class) 
@Suite.SuiteClasses( { 
	Connect4Tests.class,
	ComplicaTests.class,
	GravityTests.class,
	ControladorTest.class	
	})
public class AllTests {

}

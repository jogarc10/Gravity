package tp.pr3.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tp.pr3.logic.test.FourInLineGravityTest;
import tp.pr3.logic.test.GravityFactoryTest;
import tp.pr3.logic.test.MovimientoGravityTest;
import tp.pr3.logic.test.GravityGameTest;
import tp.pr3.logic.test.GravityRulesTest;
import tp.pr3.logic.test.BoardTest;
import tp.pr3.logic.test.UndoGravityTest;

@RunWith(Suite.class) 
@Suite.SuiteClasses( { 
	BoardTest.class,
	MovimientoGravityTest.class,
	GravityRulesTest.class,
	GravityGameTest.class,
	UndoGravityTest.class,
	FourInLineGravityTest.class,
	GravityFactoryTest.class,
	})
public class GravityTests {

}

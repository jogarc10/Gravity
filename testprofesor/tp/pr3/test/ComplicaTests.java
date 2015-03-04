package tp.pr3.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tp.pr3.logic.test.FourInLineComplicaTest;
import tp.pr3.logic.test.ComplicaFactoryTest;
import tp.pr3.logic.test.ComplicaMoveTest;
import tp.pr3.logic.test.ComplicaGameTest;
import tp.pr3.logic.test.ComplicaRulesTest;
import tp.pr3.logic.test.BoardTest;
import tp.pr3.logic.test.UndoComplicaTest;

@RunWith(Suite.class) 
@Suite.SuiteClasses( { 
	BoardTest.class,
	ComplicaMoveTest.class,
	ComplicaRulesTest.class,
	ComplicaGameTest.class,
	UndoComplicaTest.class,
	FourInLineComplicaTest.class,
	ComplicaFactoryTest.class,
	})
public class ComplicaTests {

}

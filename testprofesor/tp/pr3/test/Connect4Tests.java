package tp.pr3.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tp.pr3.logic.test.FourInLineConecta4Test;
import tp.pr3.logic.test.Connect4FactoryTest;
import tp.pr3.logic.test.Connect4MoveTest;
import tp.pr3.logic.test.Connect4GameTest;
import tp.pr3.logic.test.Connect4RulesTest;
import tp.pr3.logic.test.BoardTest;
import tp.pr3.logic.test.UndoConecta4Test;


@RunWith(Suite.class) 
@Suite.SuiteClasses( { 
	BoardTest.class,
	Connect4MoveTest.class,
	Connect4RulesTest.class,
	Connect4GameTest.class,
	UndoConecta4Test.class,
	FourInLineConecta4Test.class,
	Connect4FactoryTest.class,
	})
public class Connect4Tests {

}

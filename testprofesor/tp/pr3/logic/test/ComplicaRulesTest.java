package tp.pr3.logic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tp.pr3.logic.ComplicaRules;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Board;

public class ComplicaRulesTest extends Connect4RulesTest {

	@Override
	protected GameRules getReglas() {
		return new ComplicaRules();
	}

	@Test
	@Override
	public void testIniciaTablero() {
		Board t = r.newBoard();
		
		assertEquals("The board does not have the appropriate width", 4, t.getWidth());
		assertEquals("The board does not have the appropriate height", 7, t.getHeight());
		assertTrue("The board should be empty at the beginning", Utils.tableroVacio(t));
	}
}

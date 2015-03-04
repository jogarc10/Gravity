package tp.pr3.logic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.logic.Counter;
import tp.pr3.logic.Connect4Rules;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Board;

public class Connect4RulesTest {
	
	protected GameRules r;
	
	@Before
	public void init() {
		r = getReglas();
	}
	
	protected GameRules getReglas() {
		return new Connect4Rules();
	}

	@Test
	public void testIniciaTablero() {
		Board t = r.newBoard();
		assertEquals("The board does not have the appropriate width", 7, t.getWidth());
		assertEquals("The board does not have the appropriate height", 6, t.getHeight());
		assertTrue("The board should be empty at the beginning", Utils.tableroVacio(t));
	}
	
	@Test
	public void testJugadorInicial() {
		assertEquals("WHITE should start playing", Counter.WHITE, r.initialPlayer());
	}
	
	@Test
	public void testnextTurn() {
		Board t = r.newBoard();
		
		// Independiente del estado
		assertEquals("After WHITE plays BLACK", Counter.BLACK, r.nextTurn(Counter.WHITE, t));
		assertEquals("After WHITE plays BLACK", Counter.BLACK, r.nextTurn(Counter.WHITE, t));
		assertEquals("After BLACK plays WHITE", Counter.WHITE, r.nextTurn(Counter.BLACK, t));
		assertEquals("After BLACK plays WHITE", Counter.WHITE, r.nextTurn(Counter.BLACK, t));
		
		// Independiente del tablero
		for (int x=t.getWidth(); x>=1; x--) {
			for (int y=t.getHeight(); y>=1; y--) {
				Counter ficha = (x + y + y/3) % 2 == 0 ? Counter.BLACK : Counter.WHITE;
				t.setPosition(x, y, ficha);
				assertEquals("After BLACK plays WHITE", Counter.BLACK, r.nextTurn(Counter.WHITE, t));
				assertEquals("After BLACK plays WHITE", Counter.WHITE, r.nextTurn(Counter.BLACK, t));
			}
		}
	}
}

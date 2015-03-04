package tp.pr3.logic.test;

import org.junit.*;

import static org.junit.Assert.*;

import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.Connect4Move;
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.Game;
import tp.pr3.logic.Connect4Rules;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Board;

public class Connect4GameTest {
	
	private Game p;
	
	@Before
	public void init() {
		p = new Game(getReglas());
	}
	
	private GameRules getReglas() {
		return new Connect4Rules();
	}
	
	private Move getMovimiento(int donde, Counter color) {
		return new Connect4Move(donde, color);
	}
	
	@Test
	public void testCtor() {
		assertFalse("A recentrly started game should not be finished", p.isFinished());
		assertEquals("Games always start with a move of WHITE.", Counter.WHITE, p.getTurn());
		assertEquals("The board is of size 7x6", 7, p.getBoard().getWidth());
		assertEquals("The board is of size 7x6", 6, p.getBoard().getHeight());
		assertFalse("At the begining of game there is nothing to undo.", p.undo());
	}
	
	@Test
	public void testexecuteMoveSimple() throws InvalidMove {
		p.executeMove(getMovimiento(1, Counter.WHITE));
		assertEquals("After placing in column 1, position (1, 6) should be occupied by WHITE counters",
				Counter.WHITE,
				p.getBoard().getPosition(1,  6));
		assertFalse("The game should not terminate after a move.", p.isFinished());
		assertEquals("After WHITE plays BLACK.", Counter.BLACK, p.getTurn());
	}
	
	@Test(expected=InvalidMove.class)
	public void testEjecutaInvalidMove1() throws InvalidMove {
		p.executeMove(getMovimiento(1, Counter.BLACK));
		fail("executeMove() should not accept a move of a counter that does not have the turn.");
	}
	
	@Test
	public void testEjecutaInvalidMove2() throws InvalidMove {
		p.executeMove(getMovimiento(3, Counter.WHITE));
		p.executeMove(getMovimiento(3, Counter.BLACK));
		p.executeMove(getMovimiento(3, Counter.WHITE));
		p.executeMove(getMovimiento(3, Counter.BLACK));
		p.executeMove(getMovimiento(3, Counter.WHITE));
		p.executeMove(getMovimiento(3, Counter.BLACK));
		try {
			p.executeMove(getMovimiento(3, Counter.WHITE));
			fail("executeMove() should fail with a full column.");
		} catch(InvalidMove e) { }
	}
	
	@Test
	public void testEjecutaInvalidMove3() {
		for (int x = -10; x <= 10; ++x) {
			if ((1 <= x) && (x <= 7)) continue;
			try {
				p.executeMove(getMovimiento(x, Counter.WHITE));
				fail("executeMove() should fail with an invalid column.");
			} catch(InvalidMove e) { }
		}
	}
	
	@Test
	public void persistenciaTablero() throws InvalidMove {
		// Comprobación que no está en la documentación pero de implementación
		// de sentido común (y, dicho sea de paso, que nos permite tomar atajos
		// en los test del cuatro en raya).
		Board t = p.getBoard();
		p.executeMove(getMovimiento(3, Counter.WHITE));
		assertTrue("The board object should not change in the middle of game (only if reset() is called).",
				t == p.getBoard());
		assertEquals("After placing in the 3rd column, position (3, 7) should be occupied with WHITE.",
				Counter.WHITE,
				t.getPosition(3,  6));
	}
	
	
	@Test
	public void partidaEnTablas() throws InvalidMove {
		for (int x = 1; x <= 7; ++x) {
			if (x == 4) continue;
			for (int i = 0; i < 6; ++i) {
				if ((x == 7) && (i == 5)) continue;
				p.executeMove(getMovimiento(x, p.getTurn()));
			}
		}
		
		for (int i = 0; i < 6; ++i) {
			p.executeMove(getMovimiento(4, p.getTurn()));
		}
		
		p.executeMove(getMovimiento(7, p.getTurn()));

		assertTrue("A game with a full board should be a draw.", p.isFinished());
		assertEquals("There is no winner when there is a draw.", Counter.EMPTY, p.getWinner());
		
		for (int i = 1; i <= 7; ++i) {
			try {
				p.executeMove(getMovimiento(i, p.getTurn()));
				fail("If the game is draw, we cannot place counters.");
			} catch(InvalidMove e) {}
		}
	}
	
	@Test
	public void testReset1() throws InvalidMove {
		
		p.executeMove(getMovimiento(3, Counter.WHITE));
		p.reset(getReglas());
		assertTrue("After reset, the board should become empty", Utils.tableroVacio(p.getBoard()));
		assertEquals("After reset, the turn is for WHITE", Counter.WHITE, p.getTurn());

	}
}

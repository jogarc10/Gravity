package tp.pr3.logic.test;

import org.junit.*;

import static org.junit.Assert.*;
import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.GravityMove;
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.Game;
import tp.pr3.logic.GravityRules;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Board;

public class GravityGameTest {
	
	private Game p;
	
	@Before
	public void init() {
		p = new Game(getReglas());
	}
	
	protected GameRules getReglas() {
		return new GravityRules(10, 15);
	}
	
	protected Move getMovimiento(int columna, int fila, Counter color) {
		return new GravityMove(columna, fila, color);
	}
	
	protected void reset() {
		p.reset(getReglas());
	}
	
	@Test
	public void testCtor() {
		reset();
		
		assertFalse("A recently started game cannot be finished", p.isFinished());
		assertEquals("Games always start with a WHITE move", Counter.WHITE, p.getTurn());
		assertEquals("The board is of size 10x15", 10, p.getBoard().getWidth());
		assertEquals("The board is of size 10x15", 15, p.getBoard().getHeight());
		assertFalse("At the beginning of a game there is nothing to undo.", p.undo());
	}
	
	@Test
	public void testexecuteMoveSimple() throws InvalidMove {
		reset();
		
		p.executeMove(getMovimiento(1, 1, Counter.WHITE));
		assertEquals("After placing WHITE in pisition (1, 1), that position does not have WHITE",
				Counter.WHITE,
				p.getBoard().getPosition(1,  1));
		assertFalse("After a move, the game should not finish.", p.isFinished());
		assertEquals("After WHITE plays BLACK.", Counter.BLACK, p.getTurn());
	}
	
	@Test(expected=InvalidMove.class)
	public void testEjecutaInvalidMove1() throws InvalidMove {
		reset();
		
		Counter Counter = Utils.contraria(p.getTurn());
		p.executeMove(getMovimiento(2, 2, Counter));
		fail("executeMove() should not admit a movement of a counter that does not have the turn.");
	}
	
	@Test
	public void testEjecutaInvalidMove2() throws InvalidMove {
		reset();
		Board t = p.getBoard();
		
		for (int y=1; y<=3; y++) {
			for (int x=1+y-1; x<=t.getWidth()-y+1; x++) {
				p.executeMove(getMovimiento(x, y, p.getTurn()));
				try {
					p.executeMove(getMovimiento(x, y, p.getTurn()));;
					fail("executeMove() should fail when placing a counter in an occupied position.");
				} catch (InvalidMove e) { }
			}
		}
	}
	
	@Test
	public void testEjecutaInvalidMove3() {
		reset();
		Board t = p.getBoard();
		
		for (int x=-20; x<=20; x++) {
			for (int y=-20; y<=20; y++) { 
				if ((x < 1) || (x > t.getWidth()) || (y < 1) || (y > t.getHeight())) {
					try {
						p.executeMove(getMovimiento(x, y, Counter.WHITE));
						fail("executeMove() should fail when placing a Counter outside the board.");
					} catch(InvalidMove e) { }
				}
			}
		}
	}

	@Test
	public void testEjecutaInvalidMove4() throws InvalidMove {
		reset();
		Board t = p.getBoard();
		
		p.executeMove(getMovimiento(3, 2, Counter.WHITE));
		assertEquals("After placing in position (3, 2), position (3, 1) should be occupied by WHITE",
				Counter.WHITE, t.getPosition(3,  1));
				
		try
		{
			p.executeMove(getMovimiento(3, 1, Counter.BLACK)); 		
			fail("executeMove should fail because position (3,1) is already occupied");
		}
		catch (InvalidMove e) {	 }

		
		p.executeMove(getMovimiento(3, 3, Counter.BLACK));
		assertEquals("After placing in position (3, 3), positon (1, 1) should be occupied by BLACK",
				Counter.BLACK, t.getPosition(1,  1));
		
	
		p.executeMove(getMovimiento(3, 14, Counter.WHITE));		
		assertEquals("After placing in position  (3, 14), position (3, 15) should be occupied by WHITE",
				Counter.WHITE, t.getPosition(3,  15));
		
		try
		{
			p.executeMove(getMovimiento(3, 15, Counter.BLACK)); 		
			fail("executeMove should fail because position (3,15) is already occupied");
		}
		catch (InvalidMove e) {	 }
	}
	
	
	
	@Test
	public void persistenciaTablero() throws InvalidMove {
		// Comprobación que no está en la documentación pero de implementación
		// de sentido común (y, dicho sea de paso, que nos permite tomar atajos
		// en los test del cuatro en raya).
		Board t = p.getBoard();
		p.executeMove(getMovimiento(3, 15, Counter.WHITE));
		assertTrue("The board object should not change in the middle of game (only if reset() is called).",
				t == p.getBoard());
		assertEquals("After placing in position (3, 15), it should be occupied by WHITE",
				Counter.WHITE,
				t.getPosition(3,  15));
	}
	
	@Test
	public void partidaEnTablas() throws InvalidMove {
		p.reset(new GravityRules(4, 4));
		
		for (int y=1; y<=2; y++) {
			for (int x=1; x<=4; x++) {
				p.executeMove(getMovimiento(x, y, p.getTurn()));
				assertFalse("The game cannot finishe when there in not winner and it is not a draw.", p.isFinished());
			}
		}
		
		for (int y=4; y>=3; y--) {
			for (int x=4; x>=1; x--) {
				if ((x==1) && (y==3))
					continue;
				
				p.executeMove(getMovimiento(x, y, p.getTurn()));
				assertFalse("The game cannot finishe when there in not winner and it is not a draw.", p.isFinished());
			}
		}
		
		p.executeMove(getMovimiento(1, 3, p.getTurn()));
		assertTrue("A game with a full board should be a draw.", p.isFinished());
		assertEquals("There is no winner when there is a draw.", Counter.EMPTY, p.getWinner());	
	}
	
	@Test
	public void testReset1() throws InvalidMove {
		p = new Game(getReglas());
		
		p.executeMove(getMovimiento(3, 1, Counter.WHITE));
		reset();
		assertTrue("After reset, the board should become empty.", Utils.tableroVacio(p.getBoard()));
		assertEquals("After reset, the turn is for WHITE", Counter.WHITE, p.getTurn());

	}
}

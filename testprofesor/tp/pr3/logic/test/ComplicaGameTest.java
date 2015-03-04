package tp.pr3.logic.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.ComplicaMove;
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.Game;
import tp.pr3.logic.ComplicaRules;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Board;

public class ComplicaGameTest {
	
	private Game p;
	
	@Before
	public void init() {
		p = new Game(getReglas());
	}
	
	private GameRules getReglas() {
		return new ComplicaRules();
	}
	
	private Move getMovimiento(int donde, Counter color) {
		return new ComplicaMove(donde, color);
	}
	
	@Test
	public void testCtor() {
		assertFalse("A recentrly started game should not be finished", p.isFinished());
		assertEquals("Games always start with a move of WHITE", Counter.WHITE, p.getTurn());
		assertEquals("The board is of size 4x7", 4, p.getBoard().getWidth());
		assertEquals("The board is of size 4x7", 7, p.getBoard().getHeight());
		assertFalse("At the begining of game there is nothing to undo.", p.undo());
	}
	
	@Test
	public void testexecuteMoveSimple() throws InvalidMove {
		p.executeMove(getMovimiento(1, Counter.WHITE));
		assertEquals("After placing in column 1, position (1, 7) should be occupied by WHITE counters",
				Counter.WHITE,
				p.getBoard().getPosition(1,  7));
		assertFalse("The game should not terminate after a move.", p.isFinished());
		assertEquals("After WHITE plays BLACK.", Counter.BLACK, p.getTurn());
	}
	
	@Test(expected=InvalidMove.class)
	public void testEjecutaInvalidMove1() throws InvalidMove {
		p.executeMove(getMovimiento(1, Counter.BLACK));
		fail("executeMove() should not accept a move of a counter that does not have the turn.");
	}
	
	@Test
	public void testEjecutaInvalidMove3() {
		for (int x = -10; x <= 10; ++x) {
			if ((1 <= x) && (x <= 4)) continue;
			try {
				p.executeMove(getMovimiento(x, Counter.WHITE));
				fail("executeMove() should fail with an invalid columns.");
			} catch(InvalidMove e) {}; 
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
		assertEquals("After placing in the 3rd column, position (3, 7) should be occupied with WHITE",
				Counter.WHITE,
				t.getPosition(3,  7));
	}
	
	@Test
	public void testReset1() throws InvalidMove {
		
		p.executeMove(getMovimiento(3, Counter.WHITE));
		p.reset(getReglas());
		assertTrue("After reset, the board should become empty", Utils.tableroVacio(p.getBoard()));
		assertEquals("After reset, the turn is for the WHITE", Counter.WHITE, p.getTurn());

	}
	
	private void completaColumna(int c) throws InvalidMove {
		for (int y=1; y<=7; y++) {
			p.executeMove(getMovimiento(c, p.getTurn()));
			assertFalse("The game should not finish if there is no winner", p.isFinished());
			assertEquals("The game detects a winner without 4 in line", p.getWinner(), Counter.EMPTY);
		}
	}
	
	@Test
	public void partidaEnTablas() throws InvalidMove {
		
		p = new Game(getReglas());
		
		// Tablero sin conecta 4
		completaColumna(1);
		completaColumna(3);
		completaColumna(2);
		completaColumna(4);
		
		// Tablero con varios conecta cuatro
		p.executeMove(getMovimiento(3, p.getTurn()));
		assertFalse("The game should not finish if there are no 4 counters in line", p.isFinished());
		assertEquals("There shouldn't be a winner if there is no 4 counter in line", p.getWinner(), Counter.EMPTY);
		
		p.executeMove(getMovimiento(4, p.getTurn()));
		assertFalse("The game shoudl not finish if there are 4 conutes of each color on the board", p.isFinished());
		assertEquals("There shouldn't be a winner if there are 4 conutes of each color on the board", p.getWinner(), Counter.EMPTY);
	}
	
	@Test
	public void varios4EnRaya() {
		p = new Game(getReglas());
		Board t = p.getBoard();
		
		// Tablero con un grupo de 4 en raya blanco y otro negro, con
		// columnas completas y otras no completas. Al meter una nueva
		// ficha blanca en una columna no completa se crea otro grupo 
		// de blancas. No debería haber ganador.
		
		// Rellenar dos columnas
		Counter ficha;
		for (int x = 3; x <= 4; x++) {
			ficha = Counter.WHITE;
			for (int y = 7; y >= 1; y--) {
				t.setPosition(x, y, ficha);
				ficha = Utils.contraria(ficha);
			}
		}
		
		// Poner fichas en otra dos columnas
		t.setPosition(2, 7, Counter.WHITE);
		t.setPosition(2, 6, Counter.BLACK);
		t.setPosition(2, 5, Counter.WHITE);
		
		t.setPosition(1, 7, Counter.WHITE);
		t.setPosition(1, 6, Counter.BLACK);
		
		// Ejecutar movimiento
		Move mov = getMovimiento(1, Counter.WHITE);
		try {
			p.executeMove(mov);
		} catch(InvalidMove e) {
			fail("Should be possible to place a counter in an incomplete column");
		}
		assertEquals("There should be no winner if a move creates 4 WHITEs in line, but there are 4 BLACKs in line in the board (and they are there even after the move)", p.getWinner(), Counter.EMPTY);
		assertFalse("The game should not terminate is there are several 4 in line of both colors", p.isFinished());
	}
}

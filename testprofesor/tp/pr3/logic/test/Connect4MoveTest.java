package tp.pr3.logic.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.Connect4Move;
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.Connect4Rules;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Board;

public class Connect4MoveTest {
	
	protected GameRules r;
	
	@Before
	public void init() {
		r = getReglas();
	}
	
	protected Move getMovimiento(int donde, Counter color) {
		return new Connect4Move(donde, color);
	}
	
	protected GameRules getReglas() {
		return new Connect4Rules();
	}
	
	@Test
	public void testGetJugador() {
		Move mov = getMovimiento(1, Counter.WHITE);
		assertEquals("getPlayer() does not return the player with whom the movement was created", mov.getPlayer(), Counter.WHITE);
		
		mov = getMovimiento(1, Counter.BLACK);
		assertEquals("getPlayer() does not return the player with whom the movement was created", mov.getPlayer(), Counter.BLACK);
	}
	
	// Movimientos con columna dentro del tablero
	@Test
	public void testEjecutaMovimientoDentro() {
		Board t = r.newBoard(); 

		for (int x=1; x<=t.getWidth(); x++) {
			Move mov = getMovimiento(x, Counter.WHITE);
			try {
				mov.executeMove(t);
			} catch(InvalidMove e) {
				fail("executeMove() should not fail when introducing the first counter in a valid column");
			}
		}
	}
	
	// Movimientos con columna fuera del tablero
	@Test
	public void testEjecutaMovimientoFuera() {
		Board t = r.newBoard(); 

		for (int x=-20; x<=20; x++) {
			if ((x < 1) || (x > t.getWidth())) {
				try {
					Move mov = getMovimiento(x, Counter.WHITE);
					mov.executeMove(t);
					fail("executeMove() should fail when called with an incorrect value for column");
				} catch(InvalidMove e) {	}
			}
		}
	}
	
	// Movimientos cuando la columna no está llena
	@Test
	public void testEjecutaMovimientoColumnaNoLlena() throws InvalidMove {
		Board t = r.newBoard();
		
		for (int x = t.getWidth(); x >= 1; x--) {
			for (int y = t.getHeight(); y >= 1; y--) {
				Counter ficha = (x + y * t.getWidth()/2)  % 2 == 0 ? Counter.BLACK : Counter.WHITE;
				Move mov = getMovimiento(x, ficha);
				mov.executeMove(t);
				assertEquals("executeMove() does not place the counter correctly on theboard", t.getPosition(x, y), ficha);
			}
		}
	}
	
	// Movimientos cuando la columna está llena
	@Test
	public void testEjecutaMovimientoColumnaLlena() throws InvalidMove {

		Board t = r.newBoard();
		for (int i=0; i<t.getHeight(); i++) {
			Move mov = getMovimiento(1, Counter.WHITE);
			mov.executeMove(t);
		}
		
		Move mov = getMovimiento(1, Counter.WHITE);
		try {
			mov.executeMove(t);
			fail("executeMove() should fail when placing a counter in a full column");
		} catch(InvalidMove e) {};
	}

	// Undo cuando la columna no se ha llenado
	@Test
	public void testUndo() throws InvalidMove {
		Board t = r.newBoard();
		
		// Ejecutar movimientos
		Move mov[] = new Move[t.getHeight()];
		for (int i=0; i<t.getHeight(); i++) {
			mov[i] = getMovimiento(2, Counter.WHITE);
			mov[i].executeMove(t);
		}
		
		// Deshacer
		for (int i=1; i<=t.getHeight(); i++) {
			assertTrue("undo() does leave the board as it was before executeMove()", t.getPosition(2, i) == Counter.WHITE);
			mov[t.getHeight()-i].undo(t);
			assertTrue("undo() does not eliminate the counter placed with executeMove()", t.getPosition(2, i) == Counter.EMPTY);
		}
	}
}

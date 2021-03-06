package tp.pr3.logic.test;

import java.util.Stack;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.GravityMove;
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.Game;
import tp.pr3.logic.GravityRules;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Board;

public class UndoGravityTest {
	
	protected GameRules r;
	
	@Before
	public void init() {
		r = getReglas(10,10);
	}
	
	protected GameRules getReglas(int columnas, int filas) {
		return new GravityRules(columnas, filas);
	}
	
	protected Move getMovimiento(int columna, int fila, Counter color) {
		return new GravityMove(columna, fila, color);
	}
	
	@Test
	public void testUndoTrasMovimiento() throws InvalidMove {
		Game p = new Game(r);
		
		p.executeMove(getMovimiento(1, 1, Counter.WHITE));
		assertTrue("After a move, undo() should work", p.undo());
		assertTrue("When calling undo() after a move, the board should be empty.", Utils.tableroVacio(p.getBoard()));
		assertEquals("When calling undo() after a move, the turn should be for WHITE.", Counter.WHITE, p.getTurn());
		assertFalse("When calling undo() after a move, the game should not be finshed.", p.isFinished());
	}
	
	@Test
	public void testUndo() throws InvalidMove {
		Game p = new Game(r);
		Board t = p.getBoard();
		
		Stack<Board> pila = new Stack<Board>();
		for (int x=1; x<=t.getWidth(); x++) {
			pila.push(Utils.copiaTablero(t));

			p.executeMove(getMovimiento(x, 2, p.getTurn()));
		}
		
		for (int i=0; i<Math.min(t.getWidth(), 10); i++) {
			assertTrue("should be possible to execute undo() but it should return false", p.undo());
			assertTrue("undo() does not leave the board as it was before", Utils.TablerosIguales(t, pila.pop()));
		}
		
		
		pila.push(Utils.copiaTablero(t));
		p.executeMove(getMovimiento(9, 2, p.getTurn()));
		assertTrue("should be possible to execute undo() but it should return false", p.undo());
		assertTrue("undo() does not leave the board as it was before", Utils.TablerosIguales(t, pila.pop()));
	}
	

	@Test
	public void testUndoMuchasVeces() throws InvalidMove {
		Game p = new Game(r);
		Board t = p.getBoard();
		
		Stack<Board> pila = new Stack<Board>();
		
		for (int y=1; y<=3; y++) {
			for (int x=1; x<=t.getWidth(); x++) {
				if (t.getPosition(x, y) == Counter.EMPTY) {
					pila.push(Utils.copiaTablero(t));
					p.executeMove(getMovimiento(x, y, p.getTurn()));
				}
			}
		}
		
		for (int y=t.getHeight(); y>t.getHeight()-3; y--) {
			for (int x=1; x<=t.getWidth(); x++) {
				if (t.getPosition(x, y) == Counter.EMPTY) {
					pila.push(Utils.copiaTablero(t));
					p.executeMove(getMovimiento(x, y, p.getTurn()));
				}
			}
		}
				
		for (int i=0; i<10; i++) {
			assertTrue("should be possible to execute undo() but it should return false", p.undo());
			assertTrue("undo() does not leave the board as it was when executing 10 moves", Utils.TablerosIguales(t, pila.pop()));
		}
	}
	
	@Test
	public void testNoUndoTrasReset() throws InvalidMove {
		Game p = new Game(r);
		
		p.executeMove(getMovimiento(3, 1, p.getTurn()));
		p.reset(getReglas(10,10));
		assertFalse("After reset, undo() should not work.", p.undo());
	}
}

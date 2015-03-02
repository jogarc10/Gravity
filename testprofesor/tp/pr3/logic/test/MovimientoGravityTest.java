package tp.pr3.logic.test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.GravityMove;
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.GravityRules;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Board;

public class MovimientoGravityTest {
	
	protected GameRules r;
	
	@Before
	public void init() {
		r = getReglas(10,10);  
	}
	
	protected Move getMovimiento(int columna, int fila, Counter color) {
		return new GravityMove(columna, fila, color);
	}
	
	protected GameRules getReglas(int columnas, int filas) {
		return new GravityRules(columnas, filas);
	}
	
	@Test
	public void testgetPlayer() {
		Move mov = getMovimiento(1, 1,Counter.WHITE);
		assertEquals("getPlayer() does not return the player with whom the move was created", mov.getPlayer(), Counter.WHITE);
		
		mov = getMovimiento(1, 1, Counter.BLACK);
		assertEquals("getPlayer() does not return the player with whome the move was created", mov.getPlayer(), Counter.BLACK);
	}
	
	// Movimientos con posiciones dentro del tablero
	@Test
	public void testEjecutaMovimientoDentro() {
		Board t = r.newBoard(); 

		// Poner fichas hasta que el tablero se llene
		int puestas = 0;
		while (puestas < t.getHeight()*t.getHeight()) {
			
			for (int x=1; x<=t.getWidth(); x++)
				for (int y=1; y<=t.getHeight(); y++) {
					
					Move mov = getMovimiento(x, y, Counter.WHITE);
					
					if (t.getPosition(x, y) == Counter.EMPTY) {
						// Se puede poner
						puestas++;	
						try {
							mov.executeMove(t);
						} catch(InvalidMove e) {
							fail("executeMove() should not fail when placing a counter in a vlaid position");
						}
					} else {
						
						// No se puede poner
						try {
							mov.executeMove(t);
							fail("executeMove() should fail when placing a counter in an occupied positions");
						} catch(InvalidMove e) { }
					}
					
			}
		}
		
		
	}
	
	// Movimientos posiciones fuera del tablero
	@Test
	public void testEjecutaMovimientoFuera() {
		Board t = r.newBoard(); 

		for (int x=-20; x<=20; x++)
			for (int y=-20; y<=20; y++) {
				if ((x < 1) || (x > t.getWidth()) || (y < 1) || (y > t.getHeight())) {			
					try {
						Move mov = getMovimiento(x, y, Counter.WHITE);
						mov.executeMove(t);
						fail("executeMove() shuold fail when placing a counter in an invalid position");
					} catch(InvalidMove e) {	}
			}
		}
	}
	
	
	// Colocar ficha en (x,y) y debería caer hasta (x2,y2)
	private void coloca(Board t, int x, int y, int x2, int y2) throws InvalidMove {
		Counter f = Counter.WHITE;
		Move m = getMovimiento(x, y, f);
		assertTrue(t.getPosition(x2, y2) == Counter.EMPTY);
		
		m.executeMove(t);
		assertTrue(t.getPosition(x2, y2) == f);
	}
	
	@Test
	public void testMovimientoGravedad1() throws InvalidMove {
		GameRules reglasAntiguas = r; 
		r = new GravityRules(8, 8);
		
		Board t = r.newBoard();
		assertTrue(Utils.tableroVacio(t));
		
		// diag1
		coloca(t, 4, 4, 1, 1);
		coloca(t, 4, 4, 2, 2);
		coloca(t, 4, 4, 3, 3);
		coloca(t, 4, 4, 4, 4);
		
		// diag2
		coloca(t, 5, 4, 8, 1);
		coloca(t, 5, 4, 7, 2);
		coloca(t, 5, 4, 6, 3);
		coloca(t, 5, 4, 5, 4);
		
		// diag3
		coloca(t, 4, 5, 1, 8);
		coloca(t, 4, 5, 2, 7);
		coloca(t, 4, 5, 3, 6);
		coloca(t, 4, 5, 4, 5);
		
		// diag4
		coloca(t, 5, 5, 8, 8);
		coloca(t, 5, 5, 7, 7);
		coloca(t, 5, 5, 6, 6);
		coloca(t, 5, 5, 5, 5);
		
		// arriba
		coloca(t, 4, 3, 4, 1);
		coloca(t, 4, 3, 4, 2);
		coloca(t, 4, 3, 4, 3);
		coloca(t, 3, 2, 3, 1);
		coloca(t, 3, 2, 3, 2);
		coloca(t, 2, 1, 2, 1);		
		coloca(t, 5, 3, 5, 1);
		coloca(t, 5, 3, 5, 2);
		coloca(t, 5, 3, 5, 3);
		coloca(t, 6, 2, 6, 1);
		coloca(t, 6, 2, 6, 2);
		coloca(t, 7, 1, 7, 1);
		
		// abajo
		coloca(t, 4, 6, 4, 8);
		coloca(t, 4, 6, 4, 7);
		coloca(t, 4, 6, 4, 6);
		coloca(t, 3, 7, 3, 8);
		coloca(t, 3, 7, 3, 7);
		coloca(t, 2, 8, 2, 8);		
		coloca(t, 5, 6, 5, 8);
		coloca(t, 5, 6, 5, 7);
		coloca(t, 5, 6, 5, 6);
		coloca(t, 6, 7, 6, 8);
		coloca(t, 6, 7, 6, 7);
		coloca(t, 7, 8, 7, 8);
		
		// izquierda
		coloca(t, 3, 4, 1, 4);
		coloca(t, 3, 4, 2, 4);
		coloca(t, 3, 4, 3, 4);
		coloca(t, 2, 3, 1, 3);
		coloca(t, 2, 3, 2, 3);
		coloca(t, 1, 2, 1, 2);		
		coloca(t, 3, 5, 1, 5);
		coloca(t, 3, 5, 2, 5);
		coloca(t, 3, 5, 3, 5);
		coloca(t, 2, 6, 1, 6);
		coloca(t, 2, 6, 2, 6);
		coloca(t, 1, 7, 1, 7);
		
		// derecha
		coloca(t, 6, 4, 8, 4);
		coloca(t, 6, 4, 7, 4);
		coloca(t, 6, 4, 6, 4);
		coloca(t, 7, 3, 8, 3);
		coloca(t, 7, 3, 7, 3);
		coloca(t, 8, 2, 8, 2);		
		coloca(t, 6, 5, 8, 5);
		coloca(t, 6, 5, 7, 5);
		coloca(t, 6, 5, 6, 5);
		coloca(t, 7, 6, 8, 6);
		coloca(t, 7, 6, 7, 6);
		coloca(t, 8, 7, 8, 7);
		
		r = reglasAntiguas;
	}
	
	@Test
	public void testMovimientoGravedad2() throws InvalidMove {
		GameRules reglasAntiguas = r; 
		r = new GravityRules(7, 7);
		
		Board t = r.newBoard();
		assertTrue(Utils.tableroVacio(t));
		
		// equilibrio
		coloca(t, 4, 4, 4, 4);
		
		// diag1
		coloca(t, 3, 3, 1, 1);
		coloca(t, 3, 3, 2, 2);
		coloca(t, 3, 3, 3, 3);
		
		// diag2
		coloca(t, 5, 3, 7, 1);
		coloca(t, 5, 3, 6, 2);
		coloca(t, 5, 3, 5, 3);
		
		// diag3
		coloca(t, 3, 5, 1, 7);
		coloca(t, 3, 5, 2, 6);
		coloca(t, 3, 5, 3, 5);
		
		// diag4
		coloca(t, 5, 5, 7, 7);
		coloca(t, 5, 5, 6, 6);
		coloca(t, 5, 5, 5, 5);
		
		// arriba
		coloca(t, 4, 3, 4, 1);
		coloca(t, 4, 3, 4, 2);
		coloca(t, 4, 3, 4, 3);
		coloca(t, 3, 2, 3, 1);
		coloca(t, 3, 2, 3, 2);
		coloca(t, 2, 1, 2, 1);	
		coloca(t, 5, 2, 5, 1);
		coloca(t, 5, 2, 5, 2);
		coloca(t, 6, 1, 6, 1);
		
		// abajo
		coloca(t, 4, 5, 4, 7);
		coloca(t, 4, 5, 4, 6);
		coloca(t, 4, 5, 4, 5);
		coloca(t, 3, 6, 3, 7);
		coloca(t, 3, 6, 3, 6);
		coloca(t, 2, 7, 2, 7);	
		coloca(t, 5, 6, 5, 7);
		coloca(t, 5, 6, 5, 6);	
		coloca(t, 6, 7, 6, 7);
		
		
		// izquierda
		coloca(t, 3, 4, 1, 4);
		coloca(t, 3, 4, 2, 4);
		coloca(t, 3, 4, 3, 4);
		coloca(t, 2, 3, 1, 3);
		coloca(t, 2, 3, 2, 3);
		coloca(t, 1, 2, 1, 2);
		coloca(t, 2, 5, 1, 5);
		coloca(t, 2, 5, 2, 5);
		coloca(t, 1, 6, 1, 6);
		
		// derecha
		coloca(t, 5, 4, 7, 4);
		coloca(t, 5, 4, 6, 4);
		coloca(t, 5, 4, 5, 4);
		coloca(t, 6, 3, 7, 3);
		coloca(t, 6, 3, 6, 3);
		coloca(t, 7, 2, 7, 2);
		coloca(t, 6, 5, 7, 5);
		coloca(t, 6, 5, 6, 5);
		coloca(t, 7, 6, 7, 6);
		
		r = reglasAntiguas;
	}
	
	@Test
	public void testMovimientoGravedad3() throws InvalidMove {
		GameRules reglasAntiguas = r; 
		r = new GravityRules(7, 5);
		
		Board t = r.newBoard();
		assertTrue(Utils.tableroVacio(t));
		
		// equilibrio
		coloca(t, 4, 3, 4, 3);
		
		// diag1
		coloca(t, 2, 2, 1, 1);
		coloca(t, 2, 2, 2, 2);
		
		// diag2
		coloca(t, 6, 2, 7, 1);
		coloca(t, 6, 2, 6, 2);
		
		// diag3
		coloca(t, 2, 4, 1, 5);
		coloca(t, 2, 4, 2, 4);
		
		// diag4
		coloca(t, 6, 4, 7, 5);
		coloca(t, 6, 4, 6, 4);
		
		// arriba
		coloca(t, 2, 1, 2, 1);
		coloca(t, 3, 2, 3, 1);
		coloca(t, 3, 2, 3, 2);
		coloca(t, 4, 2, 4, 1);
		coloca(t, 4, 2, 4, 2);
		coloca(t, 5, 2, 5, 1);
		coloca(t, 5, 2, 5, 2);
		coloca(t, 6, 1, 6, 1);
		
		// abajo
		coloca(t, 2, 5, 2, 5);
		coloca(t, 3, 4, 3, 5);
		coloca(t, 3, 4, 3, 4);
		coloca(t, 4, 4, 4, 5);
		coloca(t, 4, 4, 4, 4);
		coloca(t, 5, 4, 5, 5);
		coloca(t, 5, 4, 5, 4);
		coloca(t, 6, 5, 6, 5);
		
		// izquierda
		coloca(t, 1, 2, 1, 2);
		coloca(t, 3, 3, 1, 3);
		coloca(t, 3, 3, 2, 3);
		coloca(t, 3, 3, 3, 3);
		coloca(t, 1, 4, 1, 4);
		
		// derecha
		coloca(t, 7, 2, 7, 2);
		coloca(t, 5, 3, 7, 3);
		coloca(t, 5, 3, 6, 3);
		coloca(t, 5, 3, 5, 3);
		coloca(t, 7, 4, 7, 4);
		
		r = reglasAntiguas;
	}


	@Test
	public void testUndo() throws InvalidMove {
		Board t = r.newBoard();
		
		Stack<Board> tableros = new Stack<>();
		Stack<Move> movimientos = new Stack<>();
		
		// Hacer movimientos
		for (int i=1; i<=5; i++) {
			tableros.push(Utils.copiaTablero(t));
			Move m = getMovimiento(5, 5, Counter.BLACK);
			movimientos.push(m);
			m.executeMove(t);
		}
		
		for (int i=1; i<=5; i++) {
			tableros.push(Utils.copiaTablero(t));
			Move m = getMovimiento(6, 6, Counter.WHITE);
			movimientos.push(m);
			m.executeMove(t);
		}
		
		for (int i=1; i<=5; i++) {
			tableros.push(Utils.copiaTablero(t));
			Move m = getMovimiento(5, 6, Counter.BLACK);
			movimientos.push(m);
			m.executeMove(t);
		}
		
		for (int i=1; i<=5; i++) {
			tableros.push(Utils.copiaTablero(t));
			Move m = getMovimiento(6, 5, Counter.WHITE);
			movimientos.push(m);
			m.executeMove(t);
		}
		
		for (int i=1; i<=4; i++) {
			tableros.push(Utils.copiaTablero(t));
			Move m = getMovimiento(5, 4, Counter.BLACK);
			movimientos.push(m);
			m.executeMove(t);
		}
		
		for (int i=1; i<=4; i++) {
			tableros.push(Utils.copiaTablero(t));
			Move m = getMovimiento(5, 7, Counter.WHITE);
			movimientos.push(m);
			m.executeMove(t);
		}
		
		for (int i=1; i<=4; i++) {
			tableros.push(Utils.copiaTablero(t));
			Move m = getMovimiento(4, 5, Counter.BLACK);
			movimientos.push(m);
			m.executeMove(t);
		}
		
		for (int i=1; i<=4; i++) {
			tableros.push(Utils.copiaTablero(t));
			Move m = getMovimiento(7, 5, Counter.WHITE);
			movimientos.push(m);
			m.executeMove(t);
		}
		
		// Deshacer movimientos
		while (!movimientos.isEmpty()) {
			Move mp = movimientos.pop();
			Board tp = tableros.pop();
			
			mp.undo(t);
			
			assertTrue("undo() does not leave the board as it was before executeMove()", Utils.TablerosIguales(t, tp));
		}
	}
	
}

package tp.pr3.logic.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.ComplicaMove;
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.ComplicaRules;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Board;

public class ComplicaMoveTest extends Connect4MoveTest {
	
	@Override
	protected Move getMovimiento(int donde, Counter color) {
		return new ComplicaMove(donde, color);
	}
	
	@Override
	protected GameRules getReglas() {
		return new ComplicaRules();
	}
	
	// Movimientos cuando la columna está llena
	@Test
	@Override
	public void testEjecutaMovimientoColumnaLlena() throws InvalidMove {
		Counter fichas[] = {
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE, 
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE,
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE,
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE
				};
		
		Board t = r.newBoard();
		
		for (int i=0; i<fichas.length; i++) {
			Move mov = getMovimiento(1, fichas[i]);
			mov.executeMove(t);
			
			// Comprobar desplazamiento
			if (i >= t.getHeight()) {
				for (int j=0; j<t.getHeight(); j++) {
					Counter esperada = fichas[i - t.getHeight() + j + 1];
					Counter real = t.getPosition(1, t.getHeight() - j);
					assertEquals("executeMove() does not place the counters correctly when the column is full", real, esperada);
				}
			}
			
		}
	}

	// Undo cuando la columna estaba llena y se ha desplazado
	@Test
	public void testUndoColumnaLlena() throws InvalidMove {
		Counter fichas[] = {
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE, 
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE,
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE,
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE
				};
		
		Board t = r.newBoard();
		
		// Hacer los movimientos
		Move movimientos[] = new Move[fichas.length];
		for (int i=0; i<fichas.length; i++) {
			movimientos[i] = getMovimiento(1, fichas[i]);
			movimientos[i].executeMove(t);
		}

		// Deshacer los movimientos
		for (int i=1; i <= fichas.length - t.getHeight(); i++) {
			movimientos[movimientos.length-i].undo(t);
			
			// Comprobar
			for (int j=1; j<=t.getHeight(); j++) {
				Counter esperada = fichas[fichas.length - j - i];
				Counter real = t.getPosition(1, j);
				assertEquals("undo() does not undo well the movements when the columns is full", real, esperada);
			}
		}
	}
}

package tp.pr3.logic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.ComplicaMove;
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.Game;
import tp.pr3.logic.ComplicaRules;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Board;

public class FourInLineComplicaTest extends FourInLineConecta4Test {

	@Override
	protected GameRules getReglas() {
		 return new ComplicaRules();
	}
	
	@Override
	protected Move getMovimiento(int donde, Counter color) {
		return new ComplicaMove(donde, color);
	}
	
	private boolean preparaColocacionFichaDesplaza(Game p, Counter color, int x, int y) throws InvalidMove {

		if (p.isFinished()) return false;
		
		Board t = p.getBoard();

		// Sanity-check: encima de y no hay nada
		for (int i = y; i >= 1; --i)
			if (t.getPosition(x, i) != Counter.EMPTY)
				return false;
		
		// Sacamos la fila sobre la que nos apoyaríamos
		int ultimaConFicha = y + 1;
		while ((ultimaConFicha <= t.getHeight()) && (t.getPosition(x, ultimaConFicha) == Counter.EMPTY))
			ultimaConFicha++;
		
		int aPoner = ultimaConFicha - y; // Con la ficha final que no pondremos
		
		// Cambiar turno?
		if ((aPoner % 2 == 1) != (p.getTurn() == color)) {
			
			// HACK: poner en una columna vacía y quitar la ficha
			p.executeMove(getMovimiento(1, p.getTurn()));
			t.setPosition(1, t.getHeight(), Counter.EMPTY);
		}
		
		// Rellenar la columna
		aPoner = ultimaConFicha;
		while (aPoner > 1) {
			Move mov = getMovimiento(x, p.getTurn());
			p.executeMove(mov);
			
			assertTrue("Identifying winner incorrectly after executing a move", r.winningMove(mov, t) == Counter.EMPTY);
			assertFalse("Identifying a draw incorrectly after executing a move", r.isDraw(mov.getPlayer(), t));
			
			aPoner--;
		}
		
		// Cambiar turno?
		if (color != p.getTurn()) {
			// HACK: poner en una columna vacía y quitar la ficha
			int columnaVacia = x % t.getWidth() + 1;
			p.executeMove(getMovimiento(columnaVacia, p.getTurn()));
			t.setPosition(columnaVacia, t.getHeight(), Counter.EMPTY);
		}
		
		return true;
	}	
	
	private void testCuatroEnRayaDesplaza(int posX[], int posY[], int ultima, Counter color) throws InvalidMove {
		Game p = new Game(getReglas());
		Board t = p.getBoard();

		if (!preparaColocacionFichaDesplaza(p, color, posX[ultima], posY[ultima]))
			fail("Internal tests error :-?");
		
		for (int i = 0; i < posX.length; ++i) {
			if (i != ultima)
				t.setPosition(posX[i], posY[i], color);
		}
		
		assertFalse("Game finished with a board consisteing of 3 counters of color " + color, p.isFinished());
		Move mov = getMovimiento(posX[ultima], p.getTurn());
		p.executeMove(mov);
		
		assertTrue("Game did not finished after 4 in a line of " + color, p.isFinished());
		assertTrue("The result of winningMove is incorrect after a win of " + color, r.winningMove(mov, t) == color);
		assertFalse("The result of isDraw is incorrect after a win of " + color, r.isDraw(color, t));
		assertEquals("The result of getWinner is incorrct after a win of " + color, color, p.getWinner());

		for (int x = 1; x <= 7; ++x) {
			try {
				p.executeMove(getMovimiento(x, Counter.WHITE));
				fail("It should not be possible to make a move after game has terminated.");
			} catch(InvalidMove e) {};
			
			try {
				p.executeMove(getMovimiento(x, Counter.BLACK));
				fail("It should not be possible to make a move after game has terminated.");
			} catch(InvalidMove e) {};
		}
	}

	
	private void pruebaCuatroEnRayaDesplaza(int posX[], int posY[]) throws InvalidMove {
		for (int i = 0; i < posX.length; ++i) {
			posY[i]--;
			testCuatroEnRayaDesplaza(posX, posY, i, Counter.WHITE);
			testCuatroEnRayaDesplaza(posX, posY, i, Counter.BLACK);
			posY[i]++;
		}
	}
	
	
	// Partidas que terminan con todas las posibles 4 en raya
	// horizontal
	@Test
	public void testCuatroEnRayaHorizontalDesplaza() throws InvalidMove {
		Board t = r.newBoard();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int x = 1; x <= t.getWidth() - 3; ++x) {
			for (int y = 1; y <= t.getHeight(); ++y) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = x + l;
					posY[l] = y;
				}
				pruebaCuatroEnRayaDesplaza(posX, posY);
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// vertical
	// Nota: en vertical no tiene sentido

	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal /
	@Test
	public void testCuatroEnRayaDiag1Desplaza() throws InvalidMove {
		Board t = r.newBoard();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int i = 1; i <= t.getHeight() + t.getWidth() - 1; ++i) {
			int sx = Math.max(1, i-t.getHeight()-1);
			int sy = Math.min(i, t.getHeight());
			while ((sy - 4 >= 0) && (sx + 3 <= t.getWidth())) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = sx + l;
					posY[l] = sy - l;
				}
				
				pruebaCuatroEnRayaDesplaza(posX, posY);
				sy--; sx++;
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal \
	@Test
	public void testCuatroEnRayaDiag2Desplaza() throws InvalidMove {
		Board t = r.newBoard();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int i = 1; i <= t.getHeight() + t.getWidth() - 1; ++i) {
			int sx = Math.min(i,  t.getWidth());
			int sy = Math.min(t.getWidth() + t.getHeight() - i, t.getHeight());
			while ((sy - 4 >= 0) && (sx - 4 >= 0)) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = sx - l;
					posY[l] = sy - l;
				}
				pruebaCuatroEnRayaDesplaza(posX, posY);
				sy--; sx--;
			}
		}
	}
}

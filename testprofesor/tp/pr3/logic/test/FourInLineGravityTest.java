package tp.pr3.logic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.GravityMove;
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.Game;
import tp.pr3.logic.GravityRules;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Board;

public class FourInLineGravityTest {
	
	private static final int[][][] DIR = {
		{{ -1, -1}, { -1, 0}, { -1, 0}, { -1, 0}, { -1, 0}, { -1, 0}, { -1, 0}, { -1, 0}, { -1, 0}, { -1, 1}, },
		{{ 0, -1}, { -1, -1}, { -1, 0}, { -1, 0}, { -1, 0}, { -1, 0}, { -1, 0}, { -1, 0}, { -1, 1}, { 0, 1}, },
		{{ 0, -1}, { 0, -1}, { -1, -1}, { -1, 0}, { -1, 0}, { -1, 0}, { -1, 0}, { -1, 1}, { 0, 1}, { 0, 1}, },
		{{ 0, -1}, { 0, -1}, { 0, -1}, { -1, -1}, { -1, 0}, { -1, 0}, { -1, 1}, { 0, 1}, { 0, 1}, { 0, 1}, },
		{{ 0, -1}, { 0, -1}, { 0, -1}, { 0, -1}, { -1, -1}, { -1, 1}, { 0, 1}, { 0, 1}, { 0, 1}, { 0, 1}, },
		{{ 0, -1}, { 0, -1}, { 0, -1}, { 0, -1}, { 1, -1}, { 1, 1}, { 0, 1}, { 0, 1}, { 0, 1}, { 0, 1}, },
		{{ 0, -1}, { 0, -1}, { 0, -1}, { 1, -1}, { 1, 0}, { 1, 0}, { 1, 1}, { 0, 1}, { 0, 1}, { 0, 1}, },
		{{ 0, -1}, { 0, -1}, { 1, -1}, { 1, 0}, { 1, 0}, { 1, 0}, { 1, 0}, { 1, 1}, { 0, 1}, { 0, 1}, },
		{{ 0, -1}, { 1, -1}, { 1, 0}, { 1, 0}, { 1, 0}, { 1, 0}, { 1, 0}, { 1, 0}, { 1, 1}, { 0, 1}, },
		{{ 1, -1}, { 1, 0}, { 1, 0}, { 1, 0}, { 1, 0}, { 1, 0}, { 1, 0}, { 1, 0}, { 1, 0}, { 1, 1}, },
	};

	
	protected GameRules r;
	
	@Before
	public void init() {
		r = getReglas();
	}
	
	protected GameRules getReglas() {
		return new GravityRules(10,10);
	}
	
	protected Move getMovimiento(int columna, int fila, Counter color) {
		return new GravityMove(columna, fila, color);
	}
	
	// Tableros sin 4 en raya
	@Test
	public void testNoCuatroEnRaya() throws InvalidMove {
		Game p = new Game(r);
		Board t = p.getBoard();

		for (int y=1; y<=3; y++) {
			for (int x=y; x <= t.getWidth()-y+1; x++) {
				Move mov = getMovimiento(x, y, p.getTurn());
				p.executeMove(mov);
				
				assertEquals("Incorrectly detecting 4 in line after executing a move", Counter.EMPTY, r.winningMove(mov, t));
				assertEquals("Incorrectly detecting a winner after executing a move", p.getWinner(), Counter.EMPTY);
				assertFalse("Incorrectly detecting a draw after executing a move", r.isDraw(mov.getPlayer(), t));		
				assertFalse("Incorrectly detecting a finished game after executing a move", p.isFinished());	
			}
		}
	}
	
	// DIR[x][y] = {dx,dy}
	/*
	private static final int[][][] DIR = {
		{},
		
	}
	*/
	
	// Prepara la partida para que se pueda colocar, en el siguiente movimiento
	// la ficha del color dado en la posición indicada. Para eso utiliza
	// las reglas de la partida de Gravity.
	private boolean preparaColocacionFicha(Game p, Counter color, int x, int y) throws InvalidMove {

		if (p.isFinished()) return false;
		
		Board t = p.getBoard();

		int dx = DIR[x-1][y-1][0];
		int dy = DIR[x-1][y-1][1];
		
		// Cuantas fichas tenemos que poner
		int aPoner = 0;
		if ((dx != 0) || (dy != 0)) {
			int nx = x + dx;
			int ny = y  + dy;
			while ((nx >= 1) && (nx <= 10) && (ny >= 1) && (ny <= 10) && t.getPosition(nx, ny) == Counter.EMPTY) {
				aPoner++;
				
				nx += dx;
				ny += dy;
			}
		}
		
		// Hay que ajustar turno
		if ((aPoner % 2 == 0) != (p.getTurn() == color)) {
			// HACK: poner y quitar la ficha para cambiar el turno
			if ((t.getPosition(1, 1) == Counter.EMPTY) && 
				(t.getPosition(1, 2) == Counter.EMPTY) && 
				(t.getPosition(2, 1) == Counter.EMPTY) &&
				(t.getPosition(2, 2) == Counter.EMPTY)){
				
				p.executeMove(getMovimiento(1, 1, p.getTurn()));
				t.setPosition(1, 1, Counter.EMPTY);
			} else {
				p.executeMove(getMovimiento(10, 10, p.getTurn()));
				t.setPosition(10, 10, Counter.EMPTY);	
			}
		}
		
		// Poner las fichas
		while (aPoner >= 1) {
			Move mov = getMovimiento(x, y, p.getTurn());
			p.executeMove(mov);
			
			assertTrue("Incorrectly detecting a winner after executing a move", r.winningMove(mov, t) == Counter.EMPTY);
			assertFalse("Incorrectly detecting draw after executing a move", r.isDraw(mov.getPlayer(), t));
			
			aPoner--;
		}
		
		return true;
	}	
	
	private void testCuatroEnRaya(int posX[], int posY[], int ultima, Counter color, int idxPrepara) throws InvalidMove {
		Game p = new Game(getReglas());
		Board t = p.getBoard();

		for (int i = 0; i < posX.length; ++i) {
			if (i != ultima)
				t.setPosition(posX[i], posY[i], color);
		}
		
		if (!preparaColocacionFicha(p, color, posX[idxPrepara], posY[idxPrepara]))
			fail("Internal tests error :-?");
		
		assertFalse("The game has finished with a board that consists of 3 counters of color " + color, p.isFinished());
		Move mov = getMovimiento(posX[ultima], posY[ultima], color);
		p.executeMove(mov);

		assertTrue("Game did not finish after 4 in a line of " + color, p.isFinished());
		assertTrue("The result of winningMove is incorrect after a win of " + color, r.winningMove(mov, t) == color);
		assertFalse("The result of isDraw is incorrect after a win of " + color, r.isDraw(color, t));
		assertEquals("The resuls to getWinner is incorrect after a win of " + color, color, p.getWinner());
		
		for (int x = 1; x <= t.getWidth(); ++x) {
			try {
				p.executeMove(getMovimiento(x, 1, Counter.WHITE));
				fail("It should not be possible to make a move after game has terminated.");
			} catch(InvalidMove e) {};
			
			try {
				p.executeMove(getMovimiento(x, 1, Counter.BLACK));
				fail("It should not be possible to make a move after game has terminated.");
			} catch(InvalidMove e) {};
		}
	}
	
	private void pruebaCuatroEnRaya(int posX[], int posY[]) throws InvalidMove {
		for (int i = 0; i < posX.length; ++i) {
			testCuatroEnRaya(posX, posY, i, Counter.WHITE, i);
			testCuatroEnRaya(posX, posY, i, Counter.BLACK, i);
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// horizontal
	@Test
	public void testCuatroEnRayaHorizontal() throws InvalidMove {
		Board t = r.newBoard();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int x = 1; x <= t.getWidth() - 3; ++x) {
			for (int y = 1; y <= t.getHeight(); ++y) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = x + l;
					posY[l] = y;
				}
				pruebaCuatroEnRaya(posX, posY);
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// vertical
	@Test
	public void testCuatroEnRayaVertical() throws InvalidMove {
		Board t = r.newBoard();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int x = 1; x <= t.getWidth(); ++x) {
			for (int y = 1; y <= t.getHeight() - 3; ++y) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = x;
					posY[l] = y + l;
				}
				pruebaCuatroEnRaya(posX, posY);
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal /
	@Test
	public void testCuatroEnRayaDiag1() throws InvalidMove {
		Board t = r.newBoard();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int i = 1; i <= t.getHeight() + t.getWidth() - 1; ++i) {
			int sx = Math.max(1, i-t.getHeight()-1);
			int sy = Math.min(i, t.getWidth());
			while ((sy - 4 >= 0) && (sx + 3 <= t.getWidth())) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = sx + l;
					posY[l] = sy - l;
				}
				pruebaCuatroEnRaya(posX, posY);
				sy--; sx++;
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal \
	@Test
	public void testCuatroEnRayaDiag2() throws InvalidMove {
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
				pruebaCuatroEnRaya(posX, posY);
				sy--; sx--;
			}
		}
	}
	
	// La comprobación de 4 en raya se hace después de dejar caer la ficha 
	// y no antes
	@Test
	public void testCuatroEnRayaDespuesDeGravedad() throws InvalidMove {
		Game p = new Game(r);
		Board t = p.getBoard();
		
		t.setPosition(2, 1, Counter.BLACK);
		t.setPosition(3, 2, Counter.BLACK);
		t.setPosition(4, 3, Counter.BLACK);
		t.setPosition(8, 1, Counter.BLACK);
		t.setPosition(7, 2, Counter.BLACK);
		t.setPosition(6, 3, Counter.BLACK);
		t.setPosition(2, 4, Counter.BLACK);
		t.setPosition(3, 4, Counter.BLACK);
		t.setPosition(4, 4, Counter.BLACK);
		t.setPosition(6, 4, Counter.BLACK);
		t.setPosition(7, 4, Counter.BLACK);
		t.setPosition(8, 4, Counter.BLACK);
		t.setPosition(4, 5, Counter.BLACK);
		t.setPosition(3, 6, Counter.BLACK);
		t.setPosition(2, 7, Counter.BLACK);
		t.setPosition(6, 5, Counter.BLACK);
		t.setPosition(7, 6, Counter.BLACK);
		t.setPosition(8, 7, Counter.BLACK);
		t.setPosition(5, 5, Counter.BLACK);
		t.setPosition(5, 6, Counter.BLACK);
		t.setPosition(5, 7, Counter.BLACK);
		
		// Para que el turno sea de negras
		Move m = getMovimiento(10, 10, Counter.WHITE);
		p.executeMove(m);
		assertFalse(p.isFinished());
		
		// No comprobar 4 en raya antes de aplicar gravedad
		m = getMovimiento(5, 4, Counter.BLACK);
		p.executeMove(m);
		assertFalse("The check of the 4 in line should be done after applying the gravity on the counter.", p.isFinished());
		
		t.setPosition(5, 1, Counter.EMPTY);
		t.setPosition(3, 1, Counter.BLACK);
		t.setPosition(4, 1, Counter.BLACK);
		
		// Para que el turno sea de negras
		m = getMovimiento(10, 9, Counter.WHITE);
		p.executeMove(m);
		assertFalse(p.isFinished());
		
		// Comprobar 4 en raya después de aplicar gravedad
		m = getMovimiento(5, 4, Counter.BLACK);
		p.executeMove(m);
		assertTrue("Did not detect a 4 in line after applying the gravity on the counter.", p.isFinished());
		assertTrue("Did not detect the winner correctly after 4 in line of BLACK counter.", p.getWinner() == Counter.BLACK);
	}
}

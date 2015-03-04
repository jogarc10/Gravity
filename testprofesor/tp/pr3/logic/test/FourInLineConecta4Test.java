package tp.pr3.logic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.Connect4Move;
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.Game;
import tp.pr3.logic.Connect4Rules;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Board;

public class FourInLineConecta4Test {
	
	protected GameRules r;
	
	@Before
	public void init() {
		r = getReglas();
	}
	
	protected GameRules getReglas() {
		return new Connect4Rules();
	}
	
	protected Move getMovimiento(int donde, Counter color) {
		return new Connect4Move(donde, color);
	}
	
	// Tableros sin 4 en raya
	@Test
	public void testNoCuatroEnRaya() throws InvalidMove {
		Game p = new Game(r);
		Board t = p.getBoard();

		for (int x=t.getWidth(); x>=1; x--) {
			for (int y=3; y>=1; y--) {
				Move mov = getMovimiento(x, p.getTurn());
				p.executeMove(mov);
				
				assertEquals("Incorrectly detecting 4 in line after executing a move", Counter.EMPTY, r.winningMove(mov, t));
				assertEquals("Incorrectly detecting a winner after executing a move", p.getWinner(), Counter.EMPTY);
				assertFalse("Incorrectly detecting a draw after executing a move", r.isDraw(mov.getPlayer(), t));		
				assertFalse("Incorrectly detecting a finished game after executing a move", p.isFinished());				
			}
		}
	}
	
	// Prepara la partida para que se pueda colocar, en el siguiente movimiento
	// la ficha del color dado en la posición indicada. Para eso utiliza
	// las reglas de la partida de C4. Para que pueda hacerlo, debe haber una columna
	// vacía en el tablero y que la columna donde se quiere colocar
	// cumpla las restricciones del conecta 4...
	private boolean preparaColocacionFicha(Game p, Counter color, int x, int y) throws InvalidMove {

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

		if ((aPoner % 2 == 1) != (p.getTurn() == color)) {
			// Hay que poner una en una columna dummy para ajustar
			// turno
			
			/*
			int aux = columnaAdecuada(t, p.getTurno(), x);
			if (aux == 0) return false;
			p.ejecutaMovimiento(getMovimiento(aux, p.getTurno()));
			*/
			
			// HACK: poner y quitar la ficha para cambiar el turno
			p.executeMove(getMovimiento(1, p.getTurn()));
			t.setPosition(1, t.getHeight(), Counter.EMPTY);
		}
		
		// Antes de poner, garantizamos que no hay huecos por
		// debajo...
		for (int i = ultimaConFicha + 1; i <= t.getHeight(); ++i) {
			if (t.getPosition(x,i) == Counter.EMPTY)
				t.setPosition(x, i, (color == Counter.WHITE) ? Counter.BLACK : Counter.WHITE);
		}
		
		while (aPoner > 1) {
			Move mov = getMovimiento(x, p.getTurn());
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

		if (!preparaColocacionFicha(p, color, posX[idxPrepara], posY[idxPrepara]))
			fail("Internal tests error :-?");
		
		for (int i = 0; i < posX.length; ++i) {
			if (i != ultima)
				t.setPosition(posX[i], posY[i], color);
		}
		
		assertFalse("The game has finished with a board that consists of 3 counters of color " + color, p.isFinished());
		Move mov = getMovimiento(posX[ultima], color);
		p.executeMove(mov);
		
		assertTrue("Game did not finish after 4 in a line of " + color, p.isFinished());
		assertTrue("The result of winningMove is incorrect after a win of " + color, r.winningMove(mov, t) == color);
		assertFalse("The result of isDraw is incorrect after a win of " + color, r.isDraw(color, t));
		assertEquals("The resuls to getWinner is incorrect after a win of " + color, color, p.getWinner());
		
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
				testCuatroEnRaya(posX, posY, 0, Counter.WHITE, 3);
				testCuatroEnRaya(posX, posY, 0, Counter.BLACK, 3);
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
			int sy = Math.min(i, t.getHeight());
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
}

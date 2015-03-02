package tp.pr3.logic.test;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import tp.pr3.logic.Counter;
import tp.pr3.logic.Board;

public class BoardTest {
	
	private static final int TX = 10;
	private static final int TY = 8;
	
	@Test
	public void testCtor() {

		for (int x = 1; x <= 10; ++x) {
			for (int y = 1; y <= 10; ++y) {
				Board t = new Board(x, y);
				assertEquals("The size of the board does not coincide with the size passed to the constructor", x, t.getWidth());
				assertEquals("The size of the board does not coincide with the size passed to the constructor", y, t.getHeight());
			}
		}
	}
	
	@Test
	public void testCtorParamsIncorrectos() {
		try {
			for (int x = -10; x <= 0; ++x) {
				for (int y = -10; y <= 0; ++y) {
					Board t = new Board(x, y);
					assertEquals("The constructor of the Board should create a board of size (1, 1) when the parameters are incorrect (smaller than 1).", 1, t.getWidth());
					assertEquals("The constructor of the Board should create a board of size (1, 1) when the parameters are incorrect (smaller than 1).", 1, t.getHeight());
				}
			}
		} catch (Exception ex) {
			fail("The constructor of the Board should create a board of size (1, 1) when the parameters are incorrect (smaller than 1).");
		}
	}
	
	@Test
	public void testCtorVaciaTablero() {
		Board t = new Board(TX, TY);
		assertTrue("The cells of the Board should be empty at the beginning.", Utils.tableroVacio(t));
	}
	
	@Test
	public void testgetPosition() {
		Board t = new Board(TX, TY);
		for (int x = 1; x <= TX; ++x) {
			for (int y = 1; y <= TY; ++y) {
				t.setPosition(x,  y,  Counter.WHITE);
				assertEquals("getPosition() does not return the value that we have just placed.", Counter.WHITE, t.getPosition(x,  y));
				t.setPosition(x,  y,  Counter.BLACK);
				assertEquals("getPosition() does not return the value that we have just placed.", Counter.BLACK, t.getPosition(x,  y));
				t.setPosition(x,  y,  Counter.EMPTY);
				assertEquals("getPosition() does seem to allow deleteing cells by using Counter.EMPTY.", Counter.EMPTY, t.getPosition(x,  y));
			}
		}
	}
	
	@Test
	public void testgetPositionIncorrecta() {
		try {
			Board t = new Board(TX, TY);
			for (int x = -TX; x <= 2*TX; ++x) {
				if ((1 <= x) && (x <= TX)) continue;
				for (int y = -TY; y <= 2*TY; ++y) {
					if ((1 <= y) && (y <= TY)) continue;
					assertEquals("getPosition() should return Counter.EMPTY if called with invalid position.", Counter.EMPTY, t.getPosition(x,  y));
				}
			}
		} catch (Exception ex) {
			fail("getPosition() should not fail if called with invalid positons.");
		}
	}
	
	@Test
	public void testsetPositionIncorrecta() {
		try {
			Board t = new Board(TX, TY);
			for (int x = -TX; x <= 2*TX; ++x) {
				if ((1 <= x) && (x <= TX)) continue;
				for (int y = -TY; y <= 2*TY; ++y) {
					if ((1 <= y) && (y <= TY)) continue;
					t.setPosition(x, y, Counter.BLACK);
				}
			}
		} catch (Exception ex) {
			fail("setPosition() should not fail if called with invalid position.");
		}
	}
}

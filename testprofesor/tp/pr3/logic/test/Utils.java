package tp.pr3.logic.test;

import tp.pr3.logic.Counter;
import tp.pr3.logic.Board;

public class Utils {

	/**
	 * Comprueba si el tablero está vacío.
	 */
	public static boolean tableroVacio(Board t) {
		for (int x = 1; x <= t.getWidth(); ++x)
			if (!columnaEMPTY(t, x))
				return false;
		return true;
	}
	
	/**
	 * Comprueba si la columna x del tablero está vacía. 
	 */
	public static boolean columnaEMPTY(Board t, int x) {
		for (int y = 1; y <= t.getHeight(); ++y)
			if (t.getPosition(x,  y) != Counter.EMPTY)
				return false;
		return true;
	}
	
	/**
	 * Devuelve una copia del tablero.
	 */
	public static Board copiaTablero(Board t) {
		Board copia = new Board(t.getWidth(), t.getHeight());
		
		for (int x=1; x<=t.getWidth(); x++) {
			for (int y=1; y<=t.getHeight(); y++) {
				copia.setPosition(x, y, t.getPosition(x, y));
			}
		}
		
		return copia;
	}
	
	/**
	 * Comprueba si dos tablero son iguales.
	 */
	public static boolean TablerosIguales(Board t1, Board t2) {
		if (t1 == t2)
			return true;
		
		if ((t1 == null && t2 != null) || (t1 != null && t2 == null))
			return false;
		
		if (t1.getWidth() != t2.getWidth())
			return false;
		if (t1.getHeight() != t2.getHeight())
			return false;
		
		for (int x=1; x<=t1.getWidth(); x++) {
			for (int y=1; y<=t1.getHeight(); y++) {
				if (t1.getPosition(x, y) != t2.getPosition(x, y))
					return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Devuelve la Counter contraria
	 */
	public static Counter contraria(Counter f) {
		if (f == Counter.WHITE)
			return Counter.BLACK;
		else if (f == Counter.BLACK)
			return Counter.WHITE;
		else
			return Counter.EMPTY;
	}
}

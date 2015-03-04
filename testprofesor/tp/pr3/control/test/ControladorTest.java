package tp.pr3.control.test;

import java.util.Scanner;

import org.junit.Test;

import static org.junit.Assert.fail;
import tp.pr3.control.Controller;
import tp.pr3.control.Connect4Factory;
import tp.pr3.control.GameTypeFactory;
import tp.pr3.logic.Game;

public class ControladorTest {
	
	// Comprobar que existe la clase Controlador
	@Test
	public void testCtor() {
		try {
			GameTypeFactory f = new Connect4Factory();
			Game p = new Game(f.createRules());
			Scanner sc = new Scanner(System.in);
			new Controller(f, p, sc);
		} catch (Exception e) {
			fail("The constructor of the Controller failed!.");
		}
	}
}

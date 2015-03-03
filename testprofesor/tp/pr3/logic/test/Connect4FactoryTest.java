package tp.pr3.logic.test;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.control.Connect4Factory;
import tp.pr3.control.Player;
import tp.pr3.control.RandomConnect4Player;
import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.Connect4Move;
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.Game;
import tp.pr3.logic.Connect4Rules;
import tp.pr3.logic.Board;

public class Connect4FactoryTest {

	Connect4Factory f;

	@Before
	public void init() {
		f = new Connect4Factory();
	}

	@Test
	public void testInterfaz() {
		Connect4Rules r = (Connect4Rules) f.createRules();
		assertNotNull("The Connect4Factory does not return valid Connect4 rules", r);

		Connect4Move m = (Connect4Move) f.createMove(1, 1, Counter.WHITE);
		assertNotNull("The Connect4Factory does not return a valid move", m);

		RandomConnect4Player ja = (RandomConnect4Player) f.createRandomPlayer();
		assertNotNull("The Connect4Factory does not return a valid random player", ja);

		Scanner sc = new Scanner(System.in);
		Player jh = f.createHumanPlayerAtConsole(sc);
		assertNotNull("The Connect4Factory does not return a valid human player", jh);
	}

	@Test
	public void testJugadorAleatorio() throws InvalidMove {

		// Jugar 50 partidas aleatorias
		for (int i=0; i<50; i++) {
			
			Game p = new Game(f.createRules());
			Board t = p.getBoard();
			Player[] js = new Player[2];
			js[0] = f.createRandomPlayer();
			js[1] = f.createRandomPlayer();

			int k=0;
			while (!p.isFinished()) {
				Move m = js[k].getMove(t, p.getTurn());
				System.out.println("Random: "+ m.getPlayer());;
				p.executeMove(m);
				k = (k + 1) % 2;
			}
		}
	}
}

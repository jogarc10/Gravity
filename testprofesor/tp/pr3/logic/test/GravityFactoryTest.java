package tp.pr3.logic.test;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.control.GravityFactory;
import tp.pr3.control.Player;
import tp.pr3.control.RandomGravityPlayer;
import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.GravityMove;
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.Game;
import tp.pr3.logic.GravityRules;
import tp.pr3.logic.Board;

public class GravityFactoryTest {
	
	GravityFactory f;
	
	@Before
	public void init() {
		f = new GravityFactory(11,15);
	}
	
	@Test
	public void testInterfaz() {
		GravityRules r = (GravityRules) f.createRules();
		assertNotNull("The GravityFactory does not return valid Gravity rules", r);
		
		GravityMove m = (GravityMove) f.createMove(1, 1, Counter.WHITE);
		assertNotNull("The GravityFactory does not return a valid move", m);
		
		RandomGravityPlayer ja = (RandomGravityPlayer) f.createRandomPlayer();
		assertNotNull("The GravityFactory does not return a valid random player", ja);
		
		Scanner sc = new Scanner(System.in);
		Player jh = f.createHumanPlayerAtConsole(sc);
		assertNotNull("The GravityFactory does not return a valid human player", jh);
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
				p.executeMove(m);
				k = (k + 1) % 2;
			}
		}
	}
}

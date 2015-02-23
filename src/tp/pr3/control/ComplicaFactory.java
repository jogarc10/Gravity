package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.logic.ComplicaMove;
import tp.pr3.logic.ComplicaRules;
import tp.pr3.logic.Counter;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Move;

public class ComplicaFactory implements GameTypeFactory {

	public GameRules createRules() {
		return new ComplicaRules();
	}

	public Move createMove(int col, int row, Counter colour) {
		return new ComplicaMove(col, colour);
	}

	public Player createHumanPlayerAtConsole(Scanner in) {
		/** What the fuck??? Cómo voy a crear un objecto de una interface?  */
		return null;
	}

	public Player createRandomPlayer() {
		return new RandomComplicaPlayer();
	}
 
}

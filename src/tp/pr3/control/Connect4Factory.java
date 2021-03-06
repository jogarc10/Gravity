package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.logic.Connect4Move;
import tp.pr3.logic.Connect4Rules;
import tp.pr3.logic.Counter;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Move;

public class Connect4Factory implements GameTypeFactory {

	public GameRules createRules() {
		return new Connect4Rules();
	}

	public Move createMove(int col, int row, Counter colour) {
		return new Connect4Move(col, colour);
	}

	public Player createHumanPlayerAtConsole(Scanner in) {
		return new HumanPlayer(false, in, this);
	}
  
	public Player createRandomPlayer() {
		return new RandomConnect4Player();
	}

} 

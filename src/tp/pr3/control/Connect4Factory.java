package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.logic.Counter;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Move;

public class Connect4Factory implements GameTypeFactory {

	@Override
	public Player createHumanPlayerAtConsole(Scanner in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Move createMove(int col, int row, Counter colour) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player createRandomPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameRules createRules() {
		// TODO Auto-generated method stub
		return null;
	}
	 
} 

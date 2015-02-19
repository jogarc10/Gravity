package tp.pr3.control;

import tp.pr3.logic.Move;
import tp.pr3.logic.Counter;
import tp.pr3.logic.GameRules;

public interface GameTypeFactory {

	public Player createHumanPlayerAtConsole(java.util.Scanner in);
	
	public Move createMove(int col, int row, Counter colour);
	
	public Player createRandomPlayer();
	
	public GameRules createRules();
	
}

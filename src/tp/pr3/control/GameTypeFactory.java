package tp.pr3.control;

import tp.pr3.logic.Counter;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.Move;

public interface GameTypeFactory {

	// Constructs the concrete rules of the game.
	public abstract GameRules createRules();

	// Constructs a move for a particular Game
	public abstract Move createMove(int col, int row, Counter colour);
	
	// Constructs a player responsible for asking the user via the console 
	// which command he or she would like to execute.
	public abstract Player createHumanPlayerAtConsole(java.util.Scanner in);
	
	// Constructs a player capable of playing the current game by randomly choosing moves.
	public abstract Player createRandomPlayer();
	
}






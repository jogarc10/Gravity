package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.Resources.Resources;
import tp.pr3.logic.Counter;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.GravityMove;
import tp.pr3.logic.GravityRules;
import tp.pr3.logic.Move;

public class GravityFactory implements GameTypeFactory{

	public GameRules createRules() {	
		return new GravityRules(Resources.DIMX_GRAVITY, Resources.DIMY_GRAVITY);
	}

	public Move createMove(int col, int row, Counter colour) {
		return new GravityMove(col, row, colour);
	}

	public Player createHumanPlayerAtConsole(Scanner in) {
		return new HumanPlayer(true, in, this);
	}

	public Player createRandomPlayer() {
		return new RandomGravityPlayer();
	}

}

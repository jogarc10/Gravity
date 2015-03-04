package tp.pr3.control;

import tp.pr3.logic.Board;
import tp.pr3.logic.ComplicaMove;
import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.Resources.Resources;

import java.util.Random;

public class RandomComplicaPlayer implements Player{

	public Move getMove(Board board, Counter counter) {
		int column;
		//Random randomNum = new Random();
		
		// column = randomNum.nextInt(Resources.DIMX_COMPLICA);
		column = (int) ((Math.random() * Resources.DIMX_COMPLICA) + 1);
				
		// Move randomMove 
		
		return new ComplicaMove(column, counter);
	}

}

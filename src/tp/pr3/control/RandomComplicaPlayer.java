package tp.pr3.control;

import tp.pr3.logic.Board;
import tp.pr3.logic.ComplicaMove;
import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.Resources.Resources;

import java.util.Random;

public class RandomComplicaPlayer implements Player{

	public Move getMove(Board board, Counter counter) {
		Random randomNum = new Random();
		int column;
		
		column = randomNum.nextInt(Resources.DIMX_COMPLICA- 1) + 1;
				
		Move randomMove = new ComplicaMove(column, counter);
		
		return randomMove;
	}

}

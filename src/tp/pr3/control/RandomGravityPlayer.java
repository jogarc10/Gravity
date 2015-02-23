package tp.pr3.control;

import java.util.Random;

import tp.pr3.Resources.Resources;
import tp.pr3.logic.Board;
import tp.pr3.logic.ComplicaMove;
import tp.pr3.logic.Counter;
import tp.pr3.logic.GravityMove;
import tp.pr3.logic.Move;

public class RandomGravityPlayer implements Player{

	public Move getMove(Board board, Counter counter) {
		Random randomNum = new Random();
		int column = 0;
		int row = 0;
		boolean valid = false;
		
		while (!valid){
			column = randomNum.nextInt(Resources.DIMX_GRAVITY);
			row = randomNum.nextInt(Resources.DIMY_GRAVITY);
			
			if (board.getPosition(column, row) == Counter.EMPTY){
				valid = true;
			}
		}
		Move randomMove = new GravityMove(column, row, counter);
		
		return randomMove;
	}

}

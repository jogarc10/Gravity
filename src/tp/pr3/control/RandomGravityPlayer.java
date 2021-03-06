package tp.pr3.control;

import java.util.Random;

import tp.pr3.Resources.Resources;
import tp.pr3.logic.Board;
import tp.pr3.logic.Counter;
import tp.pr3.logic.GravityMove;
import tp.pr3.logic.Move;

public class RandomGravityPlayer implements Player{

	public Move getMove(Board board, Counter counter) {
		boolean valid = false;
		int column = 0, row = 0;
		
		do { 
			row    = (int) ((Math.random() * Resources.DIMY_GRAVITY) + 1);
			column = (int) ((Math.random() * Resources.DIMX_GRAVITY) + 1);
			 
			if (board.getPosition(column, row) == Counter.EMPTY) {
				valid = true;
			}
		}  while(!valid);
		
		return new GravityMove(column, row, counter);
	}

}

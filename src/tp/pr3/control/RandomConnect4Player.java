package tp.pr3.control;

import tp.pr3.logic.Board;
import tp.pr3.logic.Connect4Move;
import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.Resources.Resources;

import java.util.Random;

public class RandomConnect4Player implements Player{

	public Move getMove(Board board, Counter counter) {
		Random randomNum = new Random();
		int column;
		boolean valid = false;
		Move randomMove = null;
		
		while (!valid){
			column = randomNum.nextInt(Resources.DIMX_CONNECT4 - 1) + 1;
			
			if (!Resources.FullColumnConnect(column, board)){
				randomMove = new Connect4Move(column, counter);
				valid = true;
			}
			
		}
		return randomMove;
	}

}
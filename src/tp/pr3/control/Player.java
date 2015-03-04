package tp.pr3.control;

import tp.pr3.logic.Move;
import tp.pr3.logic.Counter;
import tp.pr3.logic.Board;


public interface Player {
	public Move getMove(Board board, Counter counter);
}

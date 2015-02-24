package tp.pr3.logic;

import tp.pr3.Resources.Resources;

public class GravityMove extends Move{
	private int row;

	public GravityMove(int moveColumn, int moveRow, Counter moveColour) {
		super(moveColour, moveColumn);
		this.row = moveRow;
	}

	@Override
	public boolean executeMove(Board board) {
		boolean validMove = false;
		
		if (board.getPosition(column, row) == Counter.EMPTY){
			validMove = true;
			Resources.applyGravity(board, column, row, currentPlayer);
		}
		

		return validMove;
	}

	public void undo(Board board) {
		int columnToUndo, rowToUndo;
		
		columnToUndo = column;
		rowToUndo = row;
		board.setPosition(columnToUndo, rowToUndo, Counter.EMPTY); 
	}

}

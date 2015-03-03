package tp.pr3.logic;

import tp.pr3.Resources.Resources;

public class GravityMove extends Move{
	private int row;

	public GravityMove(int moveColumn, int moveRow, Counter moveColour) {
		super(moveColour, moveColumn);
		this.row = moveRow;
	}

	@Override
	public boolean executeMove(Board board) throws InvalidMove {
		boolean validMove = false;
		
		if ((column >= 1 && column <= Resources.DIMX_GRAVITY) && (row >= 1 && row <= Resources.DIMY_GRAVITY)) {
			if (board.getPosition(column, row) == Counter.EMPTY) {
				validMove = true;
				Resources.applyGravity(board, column, row, super.getPlayer());
			}
			else {
				throw new InvalidMove("Invalid move: position (" + this.column + "," + this.row + ") is already occupied.");
			}
		}
		else {
			throw new InvalidMove("Invalid move: position (" + this.column + "," + this.row + ") is not on the board.");
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

package tp.pr3.logic;

import tp.pr3.Resources.Resources;

public class GravityMove extends Move{
	private int row;

	public GravityMove(Counter color, int column, int row) {
		super(color, column);
		this.row = row;
	}

	@Override
	public boolean executeMove(Board board) {
		// TODO Auto-generated method stub
		return false;
	}

	public void undo(Board board) {
		int columnToUndo, rowToUndo;
		
		columnToUndo = column;
		rowToUndo = row;
		board.setPosition(columnToUndo, rowToUndo, Counter.EMPTY); 
	}

}

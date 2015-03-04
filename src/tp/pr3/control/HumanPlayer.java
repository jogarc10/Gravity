package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.logic.Board;
import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;

public class HumanPlayer implements Player {
	private boolean withRow;
	private Scanner in;
	private GameTypeFactory factory;
	
	public HumanPlayer( boolean withRow, java.util.Scanner in, GameTypeFactory factory ) {
		this.withRow = withRow;
		this.in = in;
		this.factory = factory;
	}
	
	public Move getMove(Board board, Counter counter) {
		int col, row = -1;
		String auxStr;

		System.out.print("Please provide the column number: ");

		col = this.in.nextInt();
		auxStr = this.in.nextLine();
		
		if (withRow) {
			System.out.print("Please provide the row number: ");
			row = this.in.nextInt();
			auxStr = this.in.nextLine();				
		}
		
		return factory.createMove(col, row, counter);
	}
	
}

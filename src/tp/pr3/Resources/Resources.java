package tp.pr3.Resources;

import java.util.Scanner;

import tp.pr3.logic.Board;
import tp.pr3.logic.Counter;
import tp.pr3.logic.Game; 
import tp.pr3.logic.GravityMove;

public class Resources {
	public static final int MAX_STACK = 100;
	public static final int TILES_TO_WIN = 4;
	public static final int DIMX_CONNECT4 = 7, DIMY_CONNECT4 = 6;
	public static final int DIMX_COMPLICA = 4, DIMY_COMPLICA = 7;

	
	public static int DIMX_GRAVITY = 9, DIMY_GRAVITY = 10; // Is not final

	public static int freeRowPosition(int col, Board board) {
		int row = -1;
		int y =  board.getHeight();
		boolean empty = false;
		
		while ((!empty) && (y >= 1)) {
			if (board.getPosition(col,y) == Counter.EMPTY) {
				empty = true;
				row = y;
			}
			else {
				y--;
			}			
		} 
		return row;
	}

	// This functions is used for the Undo Function
	// Which returns the first row occupied (means that is the last movement of the user in that colum)
	
	public static int occupiedRowPosition(Board board, int col) {
		int height = board.getHeight();
		int row = height, y = 1;
		boolean occupied = false;
		
		while (!occupied && y <= height) {
			if (board.getPosition(col, y) != Counter.EMPTY) {
				occupied = true;
				row = y;
			}
			y++;
		}
		return row;
	}
	
		
	public static int menu(Game game, Scanner input) {
		int option = - 1;
		String optionString = "";
		String lowerCaseStr;
		boolean valid = false;
		
		while (!valid) {

			game.getBoard().printBoard();
			whoMoves(game);
			System.out.print ("Please enter a command: ");

			optionString = input.nextLine().toUpperCase();
			lowerCaseStr = optionString.toLowerCase();
			String[] words = optionString.split(" ");

			if (words.length == 1) {
				if (words[0].equals("UNDO")) {
					option = 1;
				}
				else if (words[0].equals("RESTART")) {
					option = 2;
				}
				else if (words[0].equals("EXIT")) {
					option = 3;
				}
				else if (words[0].equals("HELP")){
					option = 7;
				}
				else {
					System.err.println(lowerCaseStr + ": command not understood, please try again");
				}
			}
			else if (words.length == 2) {
				if (words[0].equals("PLAY")) {
					if (words[1].equals("C4")) {
						option = 4;
					}
					else if (words[1].equals("CO")) {
						option = 5;
					}
					else {
						System.err.println(lowerCaseStr + ": command not understood, please try again");
					}
				}
				else {
					System.err.println(lowerCaseStr + ": command not understood, please try again");
				}
			}
			else if (words.length == 3) {
				if (words[0].equals("MAKE")) {
					if (words[1].equals("A")) {
						if (words[2].equals("MOVE")) {
							option = 0;
						}
						else {
							System.err.println(lowerCaseStr + ": command not understood, please try again");
						}	
					}
					else {
						System.err.println(lowerCaseStr + ": command not understood, please try again");
					}
					
				}
				else if (words[0].equals("PLAYER")) {
					if (words[1].equals("WHITE")) {
						if(words[2].equals("HUMAN")) {
							option = 8; // WHITE HUMAN
						}
						else {
							option = 9; // WHITE RANDOM
						}
					} 
					else if (words[1].equals("BLACK")) {
						if(words[2].equals("HUMAN")) {
							option = 10; // BLACK HUMAN
						}
						else {
							option = 11; // BLACK RANDOM
						}
					} 
				}
				else {
					System.err.println(lowerCaseStr + ": command not understood, please try again");
				}
				
			}
			else if (words.length == 4){
				if (words[0].equals("PLAY")) {
					if (words[1].equals("GR")) {
						try {
							   Integer.parseInt(words[2]);
							   Integer.parseInt(words[3]);
							   setGravityDimX(Integer.parseInt(words[2]));
							   setGravityDimY(Integer.parseInt(words[3]));
							   option = 6;
							} catch (NumberFormatException e) {
								System.err.println(lowerCaseStr + ": command not understood, please try again");
							}
					}
				}
			}
			else {
				System.err.println(lowerCaseStr + ": command not understood, please try again");
			}
			
			if ((option >= 0) && (option <= 11)) {
				valid = true;
			}
		}
		return option;
	}
		
	public static void whoMoves(Game game) {
		if (game.getTurn() == Counter.WHITE) {
			System.out.println("White to move");
		}
		else if (game.getTurn() == Counter.BLACK) {
			System.out.println("Black to move");
		}
	}
	
	public static void moveColumnDown(Board board, int column)
	{
		for (int i = board.getHeight(); i > 1; i--) {
			board.setPosition(column, i, board.getPosition(column , i - 1));
		}
	}
	public static void moveColumnUp(Board board, int column)
	{
		for (int i = 1 ; i < board.getHeight(); i++)
		{
			board.setPosition(column, i, board.getPosition(column, i + 1));
		}
	}
	
	// He tenido que escribir de nuevo la función de pedro para 
	// que funcione el conecta 4.
	
	public static boolean fullColumn(int column, Board b) {
		int row = b.getHeight();
		boolean isFull = true;
		
		while ((isFull) && (row >= 1)) {
			if (b.getPosition(column, row) == Counter.EMPTY) {
				isFull = false;
			}
			row--;
		}
		
		return isFull;
	}
	
	public static void setGravityDimX(int x){
		DIMX_GRAVITY = x;
	}
	
	public static void setGravityDimY(int y){
		DIMY_GRAVITY = y;
	}
	
	public static GravityMove applyGravity(Board board, int column, int row, Counter counter){
		int distRight, distLeft, distUp, distBottom;
		distLeft = column-1;
		distRight = DIMX_GRAVITY - column;
		distUp = row-1;
		distBottom = DIMY_GRAVITY - row;
		double minDIM = 0;
		int minDimInt = 0;
		GravityMove movement = null;
		
		minDIM = Math.min(DIMX_GRAVITY, DIMY_GRAVITY);
		minDimInt = (int) Math.ceil(minDIM/2);
		
		if((distLeft == distRight) && (distUp == distBottom)){
			movement = displaceCounter(board, column, row, 0, 0, counter);
		}
		else if (distLeft == distRight){
			if (distUp < distBottom){
				movement = displaceCounter(board, column, row, 0, -1, counter);
			}
			else if (distBottom < distUp){
				movement = displaceCounter(board, column, row, 0, +1, counter);
			}
		}
		else if (distUp == distBottom){
			if (distLeft < distRight){
				movement = displaceCounter(board, column, row, -1, 0, counter);
			}
			else if (distLeft > distRight){
				movement = displaceCounter(board, column, row, +1, 0, counter);
			}
		}
		else if ((distUp == distRight) && (distUp < minDimInt)){
			movement = displaceCounter(board, column, row, +1, -1, counter);
		}
		else if ((distUp == distLeft) && (distUp < minDimInt)){
			movement = displaceCounter(board, column, row, -1, -1, counter);	
		}
		else if ((distBottom == distRight) && (distBottom < minDimInt)){
			movement = displaceCounter(board, column, row, +1, +1, counter);
		}
		else if ((distBottom == distLeft) && (distBottom < minDimInt)){
			movement = displaceCounter(board, column, row, -1, +1, counter);
		}
		else if ((distUp < distRight) && (distUp < distLeft) && (distUp < distBottom)){
			movement = displaceCounter(board, column, row, 0, -1, counter);
		}
		else if ((distRight < distUp) && (distRight < distLeft) && (distRight < distBottom)){
			movement = displaceCounter(board, column, row, +1, 0, counter);	
		}
		else if ((distLeft < distRight) && (distLeft < distUp) && (distLeft < distBottom)){
			movement = displaceCounter(board, column, row, -1, 0, counter);
		}
		else if ((distBottom < distRight) && (distBottom < distLeft) && (distBottom < distUp)){
			movement = displaceCounter(board, column, row, 0, +1, counter);
		}
		return movement;
	}
	
	public static GravityMove displaceCounter(Board board, int posCol, int posRow, int movCol, int movRow, Counter counter){
		boolean ocuppy = false;
		int actualRow, actualColumn;
		actualRow = posRow;
		actualColumn = posCol;
		
		if ((movCol == 0) && (movRow == 0)){
			board.setPosition(actualColumn, actualRow, counter);
		}
		
		while (!ocuppy){
			if (actualColumn > 1 && actualColumn < DIMX_GRAVITY && actualRow > 1 && actualRow < DIMY_GRAVITY){
				if (board.getPosition(actualColumn + movCol, actualRow + movRow) != Counter.EMPTY){
					ocuppy = true;
				}
				else{
					actualColumn += movCol;
					actualRow += movRow;
				}
			}
			else
			{
				ocuppy = true;
			}
		}
		return new GravityMove(actualColumn, actualRow, counter);	
		}
	
	}
	


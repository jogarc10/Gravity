package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.Resources.Resources;
import tp.pr3.logic.ComplicaRules;
import tp.pr3.logic.Connect4Move;
import tp.pr3.logic.Connect4Rules;
import tp.pr3.logic.Counter;
import tp.pr3.logic.Game;
import tp.pr3.logic.GravityMove;
import tp.pr3.logic.GravityRules;
import tp.pr3.logic.Move;
import tp.pr3.logic.ComplicaMove;
import tp.pr3.logic.Rules;

public class Controller {
	private Game game;
	private Scanner in;
	
	public Controller(Game g, java.util.Scanner in) {
		game = g;
		this.in = in;
	}

	public void run() {
		int option = -1, col;
		boolean exit = false;
		boolean valid = false;
		boolean undo;
		boolean whiteBot, blackBot;
		String auxStr;
		Rules gameRules = Rules.C4;
		Move move = null;
		
		do {
			
			option = Resources.menu(game, in);
			
			switch(option) {
			case 0: 
				// Make a move 
				System.out.print("Please provide the column number: ");
				col = this.in.nextInt();
				auxStr = this.in.nextLine();
				// we should check which type of move we are working with 
				//depending on which game we are playing with a enum type
				
				if (gameRules.equals(Rules.C4)){
					move = new Connect4Move(col, game.getTurn());
				}
				else if (gameRules.equals(Rules.CO)){
					move = new ComplicaMove(col, game.getTurn());
				}
				
				valid = game.executeMove(move);
				
				if (!valid) {
					System.out.println("Invalid move, please try again");
				}
				if (game.isFinished())
				{
					exit = true;
				}
					
				
				break;
			case 1:
				// Undo 
				undo = false;
				undo = game.undo();
				
				if (!undo) { // no deberia hacer falta hace cambios en cuanto a este undo
					System.out.println("Nothing to undo, please try again");
				}

				break;
			case 2:
				// Restart 
				game.reset(gameRules.getRules());// falta por ponerle las game rules que va a ser un parametro del controller
				System.out.println("Game restarted");
				
				break;
			case 3:
				// Exit
				exit = true;
				break;
				
			case 4://c4
				gameRules = Rules.C4;
				game.reset(new Connect4Rules());
				break;
				
			case 5://co
				gameRules = Rules.CO;
				game.reset(new ComplicaRules());
				break;
			case 6: //gr
				gameRules = Rules.GR;
				game.reset(new GravityRules());
				break;
			}
			
			// If it's finished. Then exit the loop.
			
			if (game.isFinished()) 
			{
				game.getBoard().printBoard();
				in.close();
				Counter counterWinner = game.getWinner();
				exit = true;
				
				System.out.print("Game over."); 
				if (counterWinner != Counter.EMPTY) {
					if (counterWinner == Counter.WHITE)
					{
						System.out.println("White wins"); 
					}
					if (counterWinner == Counter.BLACK)
					{
						System.out.println("Black wins"); 
					}
				}
				else
				{
					System.out.println("Tie game, no winner");
				}					
			}  
		} while(!exit);	
		System.out.println("Closing the game... ");
		 
	}	
}

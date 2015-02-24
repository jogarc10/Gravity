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
	private GameTypeFactory gameType;
	private Player[] players; 
	private Counter[] c = { Counter.WHITE, Counter.BLACK }; 
	private int currentPlayer;
	
	public Controller(Game g, java.util.Scanner in) {
		game = g;
		this.in = in;
		players = new Player[2];
	}

	public void initGame() {
		game.reset(gameType.createRules());
		players[0] = gameType.createHumanPlayerAtConsole(in);
		players[1] = gameType.createHumanPlayerAtConsole(in);
	}
	
	public void run() {
		String auxStr;
		Move move = null;
		int option, col, row;
		boolean exit = false, valid, undo;
		
		do {
			
			option = Resources.menu(game, in);
			
			switch(option) {
			case 0: 
				
				move = players[currentPlayer].getMove(game.getBoard(), c[currentPlayer]);
				valid = move.executeMove(game.getBoard());
		 
				if (!valid) {
					System.out.println("Invalid move, please try again");
				}
				if (game.isFinished()) {
					exit = true;
				}
					
				break;
			case 1:
				// Undo 
				undo = false;
				undo = game.undo();
				
				if (!undo) { 
					System.out.println("Nothing to undo, please try again");
				}

				break;
			case 2:
				// Restart 
				
				initGame(); // restart the game
				System.out.println("Game restarted");
				
				break;
				
			case 3:
				// Exit
				exit = true;
				break;
				
			case 4://c4
				gameType = new Connect4Factory();
				initGame();
				
				// gameRules = Rules.C4;
				// game.reset(new Connect4Rules());
				
				break;
				
			case 5://co
				gameType = new ComplicaFactory();
				initGame();

				// gameRules = Rules.CO;
				// game.reset(new ComplicaRules());
				
				break;
				
			case 6: //gr
				gameType = new GravityFactory();
				initGame();

				// gameRules = Rules.GR;
				// game.reset(new GravityRules());
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
					if (counterWinner == Counter.WHITE) {
						System.out.println("White wins"); 
					}
					if (counterWinner == Counter.BLACK) {
						System.out.println("Black wins"); 
					}
				}
				else {
					System.out.println("Tie game, no winner");
				}					
			}  
		} while(!exit);	
		
		System.out.println("Closing the game... ");
		 
	}	
}

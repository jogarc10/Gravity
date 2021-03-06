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
import tp.pr3.logic.InvalidMove;
import tp.pr3.logic.Move;
import tp.pr3.logic.ComplicaMove;
import tp.pr3.logic.Rules;

public class Controller {
	private Game game;
	private Scanner in;
	private Player[] players; 
	private int currentPlayer;
	private GameTypeFactory gameType;
	private Counter[] c = { Counter.WHITE, Counter.BLACK }; 	
	private Rules rules;
	
	public Controller(GameTypeFactory f, Game g, java.util.Scanner in) {
		this.gameType = f;
		this.game = g;
		this.in = in;
		this.players = new Player[2]; // Create players array
		this.gameType = f;
		initGame(); // initialize the rest of atributes
	}

	public void initGame() {
		this.gameType = gameType;
		game.reset(gameType.createRules());
		players[0] = gameType.createHumanPlayerAtConsole(in);
		players[1] = gameType.createHumanPlayerAtConsole(in);
		currentPlayer = 0;
	}
	
	public void changePlayer() {
		if (currentPlayer == 0) {
			currentPlayer = 1;
		}
		else if (currentPlayer == 1) {
			currentPlayer = 0;
		}
	}
	
	public void run() {
		Move move = null;
		int option;
		boolean exit = false, valid, undo;
		
		do {
			
			option = Resources.menu(game, in);
			
			switch(option) {
			case 0: 
				
				move = players[currentPlayer].getMove(game.getBoard(), c[currentPlayer]);
				
				try {
					valid = game.executeMove(move);
					if (valid) {
						changePlayer(); // Change Current player
					}
				}
				catch(InvalidMove e) {
					System.err.println(e.getMessage());
				} 
				if (game.isFinished()) {
					exit = true;
				} 
					
				break;
			case 1:
				// Undo 
				undo = false;
				undo = game.undo();
				if (undo){
					changePlayer(); // Change Current player
				}else{ 
					System.out.println("Nothing to undo, please try again");
				}

				break;
			case 2:
				// Restart 
				initGame(); // restart the game
				System.out.println("Game restarted.");
				break;
				
			case 3:
				// Exit
				exit = true;
				System.out.println("Exit requested. ");
				break;

			case 4://c4
				
				gameType = new Connect4Factory();
				initGame();
				System.out.println("Game restarted.");
				 
				break;
			case 5://co
				
				gameType = new ComplicaFactory();
				initGame();
				System.out.println("Game restarted.");
				
				break;
			case 6: //gr
				
				gameType = new GravityFactory(Resources.DIMX_GRAVITY, Resources.DIMY_GRAVITY); 
				initGame();
				System.out.println("Game restarted.");

				break;
			case 7:
				Resources.help();
				break;
			case 8:
				// WHITE HUMAN
				players[0] = gameType.createHumanPlayerAtConsole(in);
 				
				break;
			case 9:
				// WHITE RANDOM
				players[0] = gameType.createRandomPlayer();

				break;
			case 10:
				// BLACK HUMAN
				players[1] = gameType.createHumanPlayerAtConsole(in);

				break;
			case 11:
				// BLACK RANDOM
				players[1] = gameType.createRandomPlayer();
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
		
		System.out.println("Closing the game...  ");
		 
	}	

	public int getCurrentPlayer() {
		return this.currentPlayer;
	}
	public Counter[] getCounter() {
		return this.c;
	}
	
}

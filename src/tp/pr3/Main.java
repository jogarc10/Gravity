package tp.pr3;

import java.util.Scanner;

import tp.pr3.control.Connect4Factory;
import tp.pr3.control.Controller;
import tp.pr3.control.GameTypeFactory;
import tp.pr3.logic.Connect4Rules;
import tp.pr3.logic.Game;
import tp.pr3.logic.GameRules;

public class Main {

	public static void main(String[] args) {
		Game game;
		Scanner in;
		Controller controller;
		GameRules gameRules = new Connect4Rules();
		GameTypeFactory f = new Connect4Factory();
		
		in = new Scanner(System.in); //	Read from the keyboard
		game = new Game(gameRules);
		
		controller = new Controller(f, game, in);
		controller.run();
		
		System.exit(0);
		
	}	
}

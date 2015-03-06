package tp.pr3;

import java.util.Scanner;

import tp.pr3.Resources.Resources;
import tp.pr3.control.ComplicaFactory;
import tp.pr3.control.Connect4Factory;
import tp.pr3.control.Controller;
import tp.pr3.control.GameTypeFactory;
import tp.pr3.control.GravityFactory;
import tp.pr3.logic.ComplicaRules;
import tp.pr3.logic.Connect4Rules;
import tp.pr3.logic.Game;
import tp.pr3.logic.GameRules;
import tp.pr3.logic.GravityRules;

public class Main {

	public static void main(String[] args) {
		Game game;
		Scanner in = new Scanner(System.in);;
		Controller controller;
		boolean valid = false;
		String optionString = "", lowerCaseStr;
		
		GameRules gameRules = null;
		GameTypeFactory f = null;
		
		
		while (!valid){
			System.out.println("%> java [details omitted] tp.pr3.Main");
			
			optionString = in.nextLine().toUpperCase();
			lowerCaseStr = optionString.toLowerCase();
			String[] words = optionString.split(" ");
			
			if (words.length == 1) {
				if (words[0].equals("-H") || words[0].equals("--HELP")){
					Resources.helpInit();
				}
				else{
					System.err.println("Incorrect use: Unrecognized option: " + lowerCaseStr);
					System.err.println("For more details, use -h|--help.");
				}
			}
			else if (words.length == 2) {
				if (words[0].equals("-G") || words[0].equals("-GAME")){
					if (words[1].equals("C4")){
						gameRules = new Connect4Rules();
						f = new Connect4Factory();
						valid = true;
					}
					else if (words[1].equals("CO")){
						gameRules = new ComplicaRules();
						f = new ComplicaFactory();
						valid = true;
					}
					else if (words[1].equals("GR")){
						gameRules = new GravityRules(Resources.DIMX_GRAVITY, Resources.DIMY_GRAVITY);
						f = new GravityFactory(Resources.DIMX_GRAVITY, Resources.DIMY_GRAVITY);
						valid = true;
					}
					else {
						System.err.println("Incorrect use: game ’" + words[1].toLowerCase() + "’ incorrect.");
						System.err.println("For more details, use -h|--help.");
					}
				}
				else {
					System.err.println("Incorrect use: Unrecognized option: " + words[0].toLowerCase());
					System.err.println("For more details, use -h|--help.");
				}
			}
			else if (words.length == 6){
				if (words[0].equals("-G") || words[0].equals("--GAME")){
					if (words[1].equals("GR")){
						if (words[2].equals("-X") || words[2].equals("--DIMX")){
							try {
								   Integer.parseInt(words[3]);
								   if (words[4].equals("-Y") || words[4].equals("--DIMY")){
									   try {
										   Integer.parseInt(words[5]);
										   Resources.setGravityDimX(Integer.parseInt(words[3]));
										   Resources.setGravityDimY(Integer.parseInt(words[5]));
										   gameRules = new GravityRules(Resources.DIMX_GRAVITY, Resources.DIMY_GRAVITY);
										   f = new GravityFactory(Resources.DIMX_GRAVITY, Resources.DIMY_GRAVITY);
										   valid = true;
										   
									   }
									   catch(NumberFormatException e){
										   System.err.println("Incorrect use: illegal arguments: " + words[2] + " " + words[3] + " " + words[4] + " " + words[5]);
										   System.err.println("For more details, use -h|--help.");
									   }
								   }
								   else {
									   System.err.println("Incorrect use: illegal arguments: " + words[2] + " " + words[3] + " " + words[4] + " " + words[5]);
									   System.err.println("For more details, use -h|--help.");
								   }
							}
							catch(NumberFormatException e){
								System.err.println("Incorrect use: illegal arguments: " + words[2] + " " + words[3] + " " + words[4] + " " + words[5]);
								System.err.println("For more details, use -h|--help.");
							}
						}
						else {
							System.err.println("Incorrect use: illegal arguments: " + words[2] + " " + words[3] + " " + words[4] + " " + words[5]);
							System.err.println("For more details, use -h|--help.");
						}
					}
					else if(words[1].equals("C4") || words[1].equals("CO")){
						System.err.println("Incorrect use: illegal arguments: " + words[2] + " " + words[3] + " " + words[4] + " " + words[5]);
						System.err.println("For more details, use -h|--help.");
					}
					else{
						System.err.println("Incorrect use: game ’" + words[1].toLowerCase() + "’ incorrect.");
						System.err.println("For more details, use -h|--help.");
					}
				}
				else {
					System.err.println("Incorrect use: Unrecognized option: " + words[0].toLowerCase());
					System.err.println("For more details, use -h|--help.");
				}
			}
			else {
				System.err.println("Incorrect use: Unrecognized option: " + words[0].toLowerCase());
				System.err.println("For more details, use -h|--help.");
			}
			
		}
		
		game = new Game(gameRules);
		
		controller = new Controller(f, game, in);
		controller.run();
		
		System.exit(0);
		
	}	
}

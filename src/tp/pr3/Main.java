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
		
		GameRules gameRules = new Connect4Rules();
		GameTypeFactory f = new Connect4Factory();
		
			
			if (args.length == 1) {
				if (args[0].equals("-h") || args[0].equals("--help")){
					Resources.helpInit();
				}
				else{
					System.err.println("Incorrect use: Unrecognized option: " + args);
					System.err.println("For more details, use -h|--help.");
				}
			}
			else if (args.length == 2) {
				if (args[0].equals("-g") || args[0].equals("--game")){
					if (args[1].equals("c4")){
						gameRules = new Connect4Rules();
						f = new Connect4Factory();
					}
					else if (args[1].equals("co")){
						gameRules = new ComplicaRules();
						f = new ComplicaFactory();
					}
					else if (args[1].equals("gr")){
						gameRules = new GravityRules(Resources.DIMX_GRAVITY, Resources.DIMY_GRAVITY);
						f = new GravityFactory(Resources.DIMX_GRAVITY, Resources.DIMY_GRAVITY);
					}
					else {
						System.err.println("Incorrect use: game ’" + args[1].toLowerCase() + "’ incorrect.");
						System.err.println("For more details, use -h|--help.");
					}
				}
				else {
					System.err.println("Incorrect use: Unrecognized option: " + args[0].toLowerCase());
					System.err.println("For more details, use -h|--help.");
				}
			}
			else if (args.length == 6){
				if (args[0].equals("-g") || args[0].equals("--game")){
					if (args[1].equals("gr")){
						if (args[2].equals("-x") || args[2].equals("--dimX")){
							try {
								   Integer.parseInt(args[3]);
								   if (args[4].equals("-y") || args[4].equals("--dimY")){
									   try {
										   Integer.parseInt(args[5]);
										   Resources.setGravityDimX(Integer.parseInt(args[3]));
										   Resources.setGravityDimY(Integer.parseInt(args[5]));
										   gameRules = new GravityRules(Resources.DIMX_GRAVITY, Resources.DIMY_GRAVITY);
										   f = new GravityFactory(Resources.DIMX_GRAVITY, Resources.DIMY_GRAVITY);
										   
									   }
									   catch(NumberFormatException e){
										   System.err.println("Incorrect use: illegal arguments: " + args[2] + " " + args[3] + " " + args[4] + " " + args[5]);
										   System.err.println("For more details, use -h|--help.");
									   }
								   }
								   else {
									   System.err.println("Incorrect use: illegal arguments: " + args[2] + " " + args[3] + " " + args[4] + " " + args[5]);
									   System.err.println("For more details, use -h|--help.");
								   }
							}
							catch(NumberFormatException e){
								System.err.println("Incorrect use: illegal arguments: " + args[2] + " " + args[3] + " " + args[4] + " " + args[5]);
								System.err.println("For more details, use -h|--help.");
							}
						}
						else {
							System.err.println("Incorrect use: illegal arguments: " + args[2] + " " + args[3] + " " + args[4] + " " + args[5]);
							System.err.println("For more details, use -h|--help.");
						}
					}
					else if(args[1].equals("c4") || args[1].equals("co")){
						System.err.println("Incorrect use: illegal arguments: " + args[2] + " " + args[3] + " " + args[4] + " " + args[5]);
						System.err.println("For more details, use -h|--help.");
					}
					else{
						System.err.println("Incorrect use: game ’" + args[1].toLowerCase() + "’ incorrect.");
						System.err.println("For more details, use -h|--help.");
					}
				}
				else {
					System.err.println("Incorrect use: Unrecognized option: " + args[0].toLowerCase());
					System.err.println("For more details, use -h|--help.");
				}
			}
			else {
				System.err.println("Incorrect use: Unrecognized option: " + args[0].toLowerCase());
				System.err.println("For more details, use -h|--help.");
			}
		
		game = new Game(gameRules);
		
		controller = new Controller(f, game, in);
		controller.run();
		
		System.exit(0);
		
	}	
}

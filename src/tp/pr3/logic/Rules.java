package tp.pr3.logic;

public enum Rules {
	C4 (new Connect4Rules()) ,
	CO (new ComplicaRules()) ,
	GR (new GravityRules());
		
	private final GameRules gameRules;
	
	Rules(GameRules gameRules){
		this.gameRules = gameRules;
	}
	
	public GameRules getRules (){
		return this.gameRules;
	}
};

//https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html


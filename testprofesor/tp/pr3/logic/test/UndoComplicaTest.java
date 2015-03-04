package tp.pr3.logic.test;

import tp.pr3.logic.Counter;
import tp.pr3.logic.Move;
import tp.pr3.logic.ComplicaMove;
import tp.pr3.logic.ComplicaRules;
import tp.pr3.logic.GameRules;

public class UndoComplicaTest extends UndoConecta4Test {
	
	@Override
	protected Move getMovimiento(int donde, Counter color) {
		return new ComplicaMove(donde, color);
	}
	
	@Override
	protected GameRules getReglas() {
		return new ComplicaRules();
	}
}

package tp.pr2.logic;

public abstract class Move {
	protected Counter currentPlayer;
	protected int column;
	
	public Move(Counter color, int column) {
		currentPlayer = color;
		this.column = column;
	}
	
	//ejecutaMovimiento(Tablero tab)
	public abstract boolean executeMove(Board board);
	
	//getJugador() devuelve el color del jugador al que pertenece el movimiento
	public Counter getPlayer()
	{
		return currentPlayer;
	}
	
	//undo(Tablero tab) deshace el ultimo movimiento del tablero recibido como parametro
	public abstract void undo(Board board);
}

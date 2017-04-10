package engine;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LogicHandler{
	private ChessLogic logic;
	private Player currentPlayer;
	
	public LogicHandler(PlayerType gameType) throws FileNotFoundException,  IOException{
		logic = new ChessLogic(gameType);
		currentPlayer = new Player(logic.getP1());
	}
	
	public LogicHandler(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
		logic = (ChessLogic) FileIOHelper.readObject(file);
	}
	
	public boolean validInit(Coordinates init) {
		return logic.validInit(init, currentPlayer.getColor());
	}
	
	public boolean validFin(Coordinates init, Coordinates fin) {
		return logic.validFin(init, fin, currentPlayer, true);
	}
	
	/**
	 * Sets the piece reference of every button in the button array to that of the
	 * corresponding piece in the piece array.
	 */
	public Button[][] updateButtons() {
		Button[][] buttons = new Button[ChessGame.SIZE][ChessGame.SIZE];
		for (int x = 0; x < ChessGame.SIZE; x++) {
			for (int y = 0; y < ChessGame.SIZE; y++) {
				buttons[x][y] = new Button(logic.getPieceAt(new Coordinates(x, y)));
			}
		}
		return buttons;
	}
	
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             	/**
	 * Updates the entire board based on user input.
  	 * Takes the piece at initial coordinates, and moves it to final coordinates.
	 * Does not check for valid movement;
	 * user must only call update after movement has been error-checked.
	 * @param init The coordinates of the piece that is moving.
	 * @param fin The coordinates that the piece is moving to.
	 */
	public void makeMove(Coordinates init, Coordinates fin) {
		logic.movePiece(init, fin);
	}
	
	public void nextTurn(){
		logic.nextTurn();
	}
	
	public boolean gameOver() {
		return logic.p1Lost() || logic.p2Lost() || logic.stalemate();
	}
	
	public Player getCurrentPlayer() {
		return new Player(currentPlayer);
	}
}

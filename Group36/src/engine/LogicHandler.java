package engine;

import java.io.FileNotFoundException;

public class LogicHandler{
	private ChessLogic logic;
	private Player currentPlayer;
	
	public LogicHandler() throws FileNotFoundException {
		logic = new ChessLogic();
		currentPlayer = new Player(logic.getP1());
	}
	
	/*public LogicHandler(LogicHandler copy) throws FileNotFoundException {
		this();
		for(int x = 0; x < ChessGame.SIZE; x++) {
			for(int y = 0; y < ChessGame.SIZE; y++) {
				gameBoard[x][y] = copy.getPieceAt(new Coordinates(x, y));
			}
		}
	}*/
	
	/**
	 * Constructor for the LogicHandler class that copies a 2d array of chessboard buttons.
	 * @param grid The array of buttons to copy.
	 */
	/*
	 * public LogicHandler(Button[][] grid) {
		this();
		buttons = grid;

		for (int x = 0; x < ChessGame.SIZE; x++)
			for (int y = 0; y < ChessGame.SIZE; y++)
				gameBoard[x][y] = buttons[x][y].getPieceRef();
	}
	*/
	
	public boolean validInit(Coordinates init) {
		return logic.validInit(init, currentPlayer.getColor());
	}
	
	public boolean validFin(Coordinates init, Coordinates fin) {
		return logic.validFin(init, fin, currentPlayer);
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
		System.out.println("Selected Piece:" + logic.getType(init));
		System.out.println("Piece Color: " + logic.getColor(init));
		
		//move the specified piece to the specified location
		
		logic.movePiece(init, fin);
		
		/*if (currentPlayer.getColor() == PieceColor.White)
			currentPlayer = logic.getP2();
		else
			currentPlayer = logic.getP1();*/ //this should only be used in multiplayer
	}
	
	public boolean gameOver() {
		return logic.p1Lost() || logic.p2Lost();
	}
	
	public Player getCurrentPlayer() {
		return new Player(currentPlayer);
	}
}

package engine;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Handles all communication between Chesslogic and ChessGame
 * @author Group 36
 */
public class LogicHandler {
	
	private ChessLogic logic;
	private Player currentPlayer;
	
	private static final String CONSTANT_USE_FILE = "bin/secret.dat";
	private static final String START_FILE_NAME = "src/engine/Standard.dat";
	
	/**
	 * Default constructor for LogicHandler class.
	 * @throws FileNotFoundException if chessboard arrangement is missing.
	 * @throws IOException in case of an unknown error with scanning arrangement file.
	 * @throws ClassNotFoundException 
	 */
	public LogicHandler() throws FileNotFoundException, IOException, ClassNotFoundException {
		logic = (ChessLogic) FileIOHelper.readObject(START_FILE_NAME);
		//logic = new ChessLogic();     //TO UPDATE OUTDATED START FILE
		currentPlayer = logic.getCurrentPlayer();
	}
	
	/**
	 * Constructs a LogicHandler object whose internal logic object is based upon a given chessboard arrangement.
	 * @param file The chessboard arrangement's filename.
	 * @throws FileNotFoundException if chessboard arrangement is missing.
	 * @throws IOException in case of an unknown error with scanning arrangement file.
	 * @throws ClassNotFoundException if FileIOHelper cannot load the class properly.
	 */
	public LogicHandler(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
		logic = (ChessLogic) FileIOHelper.readObject(file);
		currentPlayer = logic.getCurrentPlayer();
	}
	
	/**
	 * Returns the result of running validInit on its own internal logic object.
	 * @param init The coordinates to check.
	 * @return True if the coordinates are a valid initial location, false otherwise.
	 */
	public boolean validInit(Coordinates init) {
		return logic.validInit(init, currentPlayer.getColor());
	}
	
	/**
	 * Returns the result of running validFin on its own internal logic object.
	 * @param init The initial coordinates to check.
	 * @param fin The final coordinates to check.
	 * @return True if the coordinates create a valid move, false otherwise.
	 */
	public boolean validFin(Coordinates init, Coordinates fin) {
		return logic.validFin(init, fin, currentPlayer, true);
	}
	
	/**
	 * Writes its internal logic object to a given filename via FileIOHelper.
	 * @param file The filename to use.
	 * @throws FileNotFoundException if file in question does not exist.
	 * @throws IOException in case of an unknown error while writing to file.
	 */
	public void writeLogic(String file) throws FileNotFoundException, IOException {
		FileIOHelper.writeObject(file, logic);
	}
	
	/**
	 * Returns a 2d array of buttons corresponding to the 2d array of pieces contained
	 * within the internal logic object.
	 * @return The completed 2d array of buttons.
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
	 * Does not check for valid movement; user must only call update after movement has been error-checked.
	 * Saves and reloads to a standard .dat file after every move to avoid EDT-related bugs.
	 * @param init The coordinates of the piece that is moving.
	 * @param fin The coordinates that the piece is moving to.
	 * @throws FileNotFoundException if chessboard arrangement is missing.
	 * @throws IOException in case of an unknown error with scanning arrangement file.
	 * @throws ClassNotFoundException if FileIOHelper cannot load the class properly.
	 */
	public void makeMove(Coordinates init, Coordinates fin) throws FileNotFoundException, IOException, ClassNotFoundException {
		logic.movePiece(init, fin);
		FileIOHelper.writeObject(CONSTANT_USE_FILE, logic);
		logic = (ChessLogic)FileIOHelper.readObject(CONSTANT_USE_FILE);
	}
	
	/**
	 * Undoes the entire last turn of the chess game.
	 */
	public void undo() {
		logic.undoMove();
		logic.undoMove();
	}
	
	/**
	 * Switches the turn over within internal logic object.
	 */
	public void nextTurn() {
		logic.nextTurn();
	}
	
	/**
	 * Checks whether game has been lost by either player, resulted in a stalemate, or come to a draw.
	 * @return The game over status.
	 */
	public boolean gameOver() {
		return logic.p1Lost() || logic.p2Lost() || logic.stalemate() || logic.draw();
	}
}

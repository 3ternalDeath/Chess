package deprecated;

import engine.PlayerType;
import pieces.PieceColor;

/**
 * Container for attributes and methods necessary to run a single game of chess.
 * @author Group 36
 */
public class Game {
	private int currentTurn;
	private Player p1;
	private Player p2;
	private Board board;
	public static boolean DEBUG = true;
	
	/**
	 * Constructor for the Game class.
	 * @param type The type of game; single- or multi-player.	 
	 */
	public Game(GameType type) {
		currentTurn = 1;
		switch (type) {
		case SINGLE:
			p1 = new Player(PieceColor.White, PlayerType.User);
			p2 = new Player(PieceColor.Black, PlayerType.Computer);
			break;
		case MULTI:
			p1 = new Player(PieceColor.White, PlayerType.User);
			p2 = new Player(PieceColor.Black, PlayerType.User);
			break;
		}
		board = new Board();
	}
	
	/**
	 * Handles all functions necessary to play an entire game of chess
	 * in the selected type of game. Runs in a perpetual loop until
	 * either player is in a state of checkmate. Exits loop and prints 
	 * outcome of game when a player has won.
	 */
	public void run() {
		board.populateBoard();
		
		while (!board.blackLose() && !board.whiteLose()) {
			board.display();
			if (currentTurn % 2 == 1) {
				System.out.println(p1.getColor());
				p1.makeMove(board);
			}
			else {
				System.out.println(p2.getColor());
				p2.makeMove(board);
			}
			currentTurn++;
		}
		
		board.display();
		
		if(board.blackLose()) {
			System.out.println("White has won, Black has lost");
		}
		else if(board.whiteLose()) {
			System.out.println("Black has won, White has lost");
		}
	}
	
	/**
	 * Displays a debugging message, if the debug switch is turned on.
	 * @param msg The message to display.
	 */
	public static void debugMsg(String msg) {
		if(DEBUG)
			System.out.println(msg);
	}
}

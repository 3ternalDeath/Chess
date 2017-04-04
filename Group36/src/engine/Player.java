package engine;

import java.util.ArrayList;
import java.util.Random;

import pieces.Piece;
import pieces.PieceColor;

/**
 * Allows user to interact with and move pieces on board,
 * or to have pieces be moved by an AI.
 * @author Group 36
 */
public class Player {
	private PieceColor color;
	private PlayerType type;
	private boolean myTurn;
	private Coordinates kingCoor;
	private boolean inCheck;
	private boolean lost;
	 
	/**
	 * Constructor for the Player class.
	 * @param color The color of the player.
	 * @param type The type of the player.
	 * @param first The player's turn status.
	 * @param king The coordinates of the player's king piece.
	 */
	public Player(PieceColor color, PlayerType type, boolean first, Coordinates king) {
		this.color = color;
		this.type = type;
		myTurn = first;
		setInCheck(false);
		setLost(false);
		setKingCoor(new Coordinates(king.getX(), king.getY()));
	}
	
	/**
	 * Copy constructor for the Player class.
	 * @param player The Player object to copy.
	 */
	public Player(Player player) {
		this(player.color, player.type, player.myTurn, player.kingCoor);
	}
	
	/**
	 * Returns the player's color.
	 * @return The color of the player.
	 */
	public PieceColor getColor() {
		return color;
	}
	
	/**
	 * Chooses a random piece belonging to the player.
	 * @param board The current configuration of the chessboard.
	 * @return The coordinates corresponding to the chosen piece.
	 */
	
	
	/**
	 * Chooses a random move for a given piece to make.
	 * @param piece The coordinates of the piece to move.
	 * @param board The current configuration of the chessboard.
	 * @return The coordinates corresponding to the chosen move.
	 */
	public Coordinates pickMove(Coordinates piece, Piece[][] board) {
		ArrayList<Coordinates> moves = board[piece.getX()][piece.getY()].getPossibleMoves();
		Coordinates move = piece;
		if (moves.size() != 0) {
			for(Coordinates c : moves) {
				if(board[c.getX()][c.getY()] != null) {
					if(board[c.getX()][c.getY()].getColor() != board[piece.getX()][piece.getY()].getColor()) {
						move = c;
					}
				}
			}
			
			if (move.equals(piece)) {
				int index = new Random().nextInt(moves.size());
				return moves.get(index);
			}
		}
		
		return move;
	}
	
	/**
	 * Returns the player's type.
	 * @return The player's type.
	 */
	public PlayerType getType() {
		return type;
	}
	
	/**
	 * Returns whether or not it is the player's turn.
	 * @return True if it is the player's turn, false otherwise.
	 */
	public boolean isMyTurn() {
		return myTurn;
	}
	
	/**
	 * Switches whether or not it is the player's turn.
	 */
	public void switchTurn() {
		if (myTurn)
			myTurn = false;
		else
			myTurn = true;
	}

	/**
	 * Returns the coordinates of the player's king piece.
	 * @return The king piece's coordinates.
	 */
	public Coordinates getKingCoor() {
		return new Coordinates(kingCoor.getX(), kingCoor.getY());
	}

	/**
	 * Sets the coordinates of the player's king piece to the given coordinates.
	 * @param kingCoor The new coordinates for the king piece.
	 */
	public void setKingCoor(Coordinates kingCoor) {
		this.kingCoor = new Coordinates(kingCoor.getX(), kingCoor.getY());
	}

	/**
	 * Returns whether or not the player is in check.
	 * @return True if the player is in check, false otherwise.
	 */
	public boolean isInCheck() {
		return inCheck;
	}

	/**
	 * Sets whether or not the player is in check.
	 * @param inCheck The new check status.
	 */
	public void setInCheck(boolean inCheck) {
		this.inCheck = inCheck;
	}

	/**
	 * Returns whether or not the player has lost.
	 * @return True if the player has lost, false otherwise.
	 */
	public boolean isLost() {
		return lost;
	}

	/**
	 * Sets whether or not the player has lost.
	 * @param lost The new loss status.
	 */
	public void setLost(boolean lost) {
		this.lost = lost;
	}
}

package engine;

import java.util.ArrayList;
import java.util.Stack;

import deprecated.Game;
import pieces.Empty;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceType;

/**
 * Handles all the internal logic of a chess game.
 * @author Group 36
 *
 */
public class ChessLogic {
	Button[][] buttons;
	Piece[][] gameBoard;
	private Stack<int[][]> moves;
	private Stack<Piece> deadPiece;
	private int[] castle;
	private boolean castleNow;
	
	/**
	 * Constructor for the ChessLogic class that sets all variables to default values. 
	 */
	public ChessLogic() {
		moves = new Stack<int[][]>();
		deadPiece = new Stack<Piece>();
		castle = new int[2];
		castle[0] = -1;
		castle[1] = -1;
		gameBoard = new Piece[ChessGame.SIZE][ChessGame.SIZE];
	}

	/**
	 * Constructor for the ChessLogic class that copies a grid of chessboard buttons.
	 * @param grid
	 */
	public ChessLogic(Button[][] grid) {
		this();
		buttons = grid;

		for (int i = 0; i < ChessGame.SIZE; i++)
			for (int j = 0; j < ChessGame.SIZE; j++)
				gameBoard[i][j] = buttons[i][j].getPieceRef();
	}
	
	/**
	 * Copy constructor for the ChessLogic class.
	 * @param logic The ChessLogic object to copy.
	 */
	public ChessLogic(ChessLogic logic) {
		gameBoard = new Piece[ChessGame.SIZE][ChessGame.SIZE];
		for (int i = 0; i < ChessGame.SIZE; i++)
			for (int j = 0; j < ChessGame.SIZE; j++)
				if (logic.buttons[i][j].getPieceRef() != null)
					gameBoard[i][j] = logic.buttons[i][j].getPiece();
	}
	
	/**
	 * Randomly generates a set of initial coordinates for a computer player.
	 * @param comp The computer player.
	 * @return The set of initial coordinates.
	 */
	public Coordinates compGetInit(Player comp) {
		return comp.pickPiece(gameBoard);
	}
	
	/**
	 * Randomly generates a set of final coordinates for a computer player.
	 * @param comp The computer player.
	 * @param init The corresponding set of initial coordinates.
	 * @return The set of final coordinates.
	 */
	public Coordinates compGetFin(Player comp, Coordinates init) {
		return comp.pickMove(init, gameBoard);
	}
	
	/**
	 * Sets the piece reference of every button in the button array to that of the
	 * corresponding piece in the piece array.
	 */
	public void updateButton() {
		for (int x = 0; x < ChessGame.SIZE; x++) {
			for (int y = 0; y < ChessGame.SIZE; y++) {
				buttons[x][y].setPieceRef(gameBoard[x][y]);
			}
		}
	}
	
	/**
	 * Updates the board according to a given group of coordinates.
	 * @param init The initial coordinates.
	 * @param fin The final coordinates.
	 * @param p1 The game's player 1.
	 * @param p2 The game's player 2.
	 */
	public void updateBoard(Coordinates init, Coordinates fin, Player p1, Player p2) {
		if (castleNow) {
			makeMove(new Coordinates(fin.getX() + 1, fin.getY()), new Coordinates(fin.getX() - 1, fin.getY()));
			if (castle[0] != -1) {
				castle[1] = moves.size();
			} 
			else {
				castle[0] = moves.size();
			}
			castleNow = false;
		}
		
		makeMove(init, fin);
		//updateCheckMate(p1, p2);
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
		System.out.println("Selected Piece:" + gameBoard[init.getX()][init.getY()].getType());
		System.out.println("Piece Color: " + gameBoard[init.getX()][init.getY()].getColor());
		
		//move the specified piece to the specified location
		//add move to the moves Stack
		Piece piece = gameBoard[init.getX()][init.getY()];
		piece.move(fin);
		
		//undo moves
		int[][] currentMove = {{init.getX(), init.getY()}, {fin.getX(), fin.getY()}};
		moves.add(currentMove);
		deadPiece.add(gameBoard[fin.getX()][fin.getY()]);
		
		gameBoard[init.getX()][init.getY()] = null;
		gameBoard[fin.getX()][fin.getY()] = piece;
	}
	
	/**
	 * Returns the type of the piece at a given set of coordinates.
	 * @param coor The set of coordinates to check.
	 * @return The type of the piece.
	 */
	public PieceType getType(Coordinates coor) {
		if (gameBoard[coor.getX()][coor.getY()] != null)
			return gameBoard[coor.getX()][coor.getY()].getType();
		else
			return null;
	}
	
	/**
	 * Checks whether or not a given group of coordinates creates a legal move for a given player.
	 * @param init The initial coordinates.
	 * @param fin The final coordinates.
	 * @param player The player in question.
	 * @return True if the move is valid, false otherwise.
	 */
	public boolean validMove(Coordinates init, Coordinates fin, Player player) {
		if (!basicValid(init, fin, player.getColor())) {
			return false;
		}
		
		if (!pawnValid(init, fin, player.getColor())) {
			return false;
		}
		
		int temp = castleValid(init, fin, player.getColor());
		if (temp == -1) {
			return false;
		}
		
		if (!collisionDetect(init, fin)) {
			return false;
		}
		
	//	if (!checkNextMoveCheck(init, fin, player)) {
		//	return false;
	//	}
	
		if (temp==1) {//MUST BE LAST IF STATEMENT!!!!!
			castleNow = true;
		}
		return true;
	}
	
	/**
	 * Checks through entire board to see if an enemy piece 
	 * can take the King piece.
	 * @param king The coordinates of the King piece.
	 * @param color The color of friendly pieces; can be left null if unknown.
	 * @return true if piece at king location can be taken next move, false otherwise
	 */
	private boolean checkCheck(Coordinates king, PieceColor color) {
		if (color == null)
			color = gameBoard[king.getX()][king.getY()].getColor();

		for (int x = 0; x < ChessGame.SIZE; x++) {
			for (int y = 0; y < ChessGame.SIZE; y++) {

				// returns true when enemy piece can take the king
				if (gameBoard[x][y] != null) {
					if (gameBoard[x][y].getColor() != color) {
						if (gameBoard[x][y].validMove(king)) {	
							if (pawnCheckValid(new Coordinates(x, y), king)) {
								if (collisionDetect(new Coordinates(x, y), king)) {	//only checks for collision if piece can move there
									Game.debugMsg("checkCheck() returns true-- KingCoordinates: " + king + " ||colorPram: "
									+ color + " ||Piece info: " + gameBoard[x][y] + " "
									+ gameBoard[x][y].getCoordinates());
								
									return true;
								}
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Checks all places around a given King piece to see whether an enemy piece
	 * could move to that location. Should only be run if King is already in check.
	 * @param king The coordinates of the King piece.
	 * @return true If all positions King piece can move to are in enemy sights, false otherwise.
	 */
	private boolean checkCheckMate(Player player) {
		Coordinates king = player.getKingCoor();
		PieceColor color = gameBoard[king.getX()][king.getY()].getColor();
		Coordinates check = new Coordinates(king.getX(), king.getY());
		
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				check.incrementX(x); // changes coords of check based on loop
										// variables
				check.incrementY(y);

				// in order for check mate to happen king should not be able to
				// move anywhere without dying
				if (Coordinates.inBound(check.getX(), check.getY()))
					if (!checkNextMoveCheck(king, check, player)) {
						return false;
					}

				check.incrementX(x * (-1));// puts check coords back the way
											// they were
				check.incrementY(y * (-1));
			}
		}
		
		return true;
	}
	
	/**
	 * Checks whether a given move (in terms of a group of coordinates) would put
	 * another player in check.
	 * @param init The initial set of coordinates.
	 * @param fin The final set of coordinates.
	 * @param player The player making the move.
	 * @return True if the move would put the other player in check, false otherwise.
	 */
	private boolean checkNextMoveCheck(Coordinates init, Coordinates fin, Player player) {
		ChessLogic nextMove = new ChessLogic(this);
		Player testPlayer = new Player(player);

		if (nextMove.getType(init) == PieceType.King)
			testPlayer.setKingCoor(fin);

		nextMove.makeTestMove(init, fin);
		nextMove.updateCheck(testPlayer);

		if (testPlayer.isInCheck())
			return false;
		else
			return true;
	}
	
	/**
	 * Updates the checkmate status of both players in the game.
	 * @param p1 The game's player 1.
	 * @param p2 The game's player 2.
	 */
	private void updateCheckMate(Player p1, Player p2) {
		updateCheckMate(p1);
		updateCheckMate(p2);
	}
	
	private boolean checkAllMoves(Player p) {
		for (int x = 0; x < ChessGame.SIZE; x++)
			for (int y = 0; y < ChessGame.SIZE; y++) {
				if (gameBoard[x][y] != null) {
					if (gameBoard[x][y].getColor() == p.getColor()) {
						Coordinates piece = new Coordinates(x,y);
						ArrayList<Coordinates> moves = gameBoard[x][y].getPossibleMoves();
						for (Coordinates move : moves) {
							if (checkNextMoveCheck(piece, move, p)) {
								return true;
							}
						}
					}
				}
			}
		
		return false;
	}
	
	/**
	 * Checks whether a given player is in checkmate and adjusts the player's checkmate status accordingly.
	 * @param player The player to check.
	 */
	private void updateCheckMate(Player player) {
		if (checkCheck(player.getKingCoor(), null)) {
			player.setInCheck(true);

			if (checkCheckMate(player))
				if (!checkAllMoves(player)) {
					player.setLost(true);;
				}
				else
					player.setLost(false);
		}
		else {
			player.setInCheck(false);
			player.setLost(false);
		}
	}
	
	/**
	 * Checks whether a given player is in check and adjusts the player's check status accordingly.
	 * @param player The player to check.
	 */
	private void updateCheck(Player player) {
		if (checkCheck(player.getKingCoor(), player.getColor())) {
			player.setInCheck(true);
		}
		else {
			player.setInCheck(false);
			player.setLost(false);
		}
	}
	
	/**
	 * Tests a particular move (in terms of a group of coordinates) without directly affecting the chessboard.
	 * @param init The initial set of coordinates.
	 * @param fin The final set of coordinates.
	 */
	private void makeTestMove(Coordinates init, Coordinates fin) {
		Piece piece = gameBoard[init.getX()][init.getY()];
		piece.move(fin);
		gameBoard[init.getX()][init.getY()] = null;
		gameBoard[fin.getX()][fin.getY()] = piece;
	}

	/**
	 * Runs the basic tests on a given move (in terms of a group of coordinates).
	 * @param init The initial set of coordinates.
	 * @param fin The final set of coordinates.
	 * @param color The color of the moving piece.
	 * @return True if every test proves valid, false otherwise.
	 */
	private boolean basicValid(Coordinates init, Coordinates fin, PieceColor color) {
		if (gameBoard[init.getX()][init.getY()] == null) {
			System.out.println("Selecting empty space.");
			return false;
		}
		// if coordinates point to the other player's pieces then disallow them
		else if (gameBoard[init.getX()][init.getY()].getColor() != color) {
			System.out.println("Selecting opponent's piece. Your color is " + color + ".");
			return false;
		}
		// if coordinates point to a piece the current player owns then disallow
		// them
		else if (gameBoard[fin.getX()][fin.getY()] != null) {
			if (gameBoard[fin.getX()][fin.getY()].getColor() == gameBoard[init.getX()][init.getY()].getColor()) {
				System.out.println("Moving into your own piece.");
				return false;
			}
		}
		// if coordinates would cause an illegal move for the piece in question
		// then disallow them
		else if (!gameBoard[init.getX()][init.getY()].validMove(fin)) {
			System.out.println("The piece can't move there.");
			return false;
		}

		return true;
	}

	/**
	 * Runs pawn-specific tests on a given move (in terms of coordinates).
	 * @param init The initial set of coordinates.
	 * @param fin The final set of coordinates.
	 * @param color The color of the moving piece.
	 * @return True if every test proves valid, false otherwise.
	 */
	private boolean pawnValid(Coordinates init, Coordinates fin, PieceColor color) {
		if (gameBoard[init.getX()][init.getY()].getType() == PieceType.Pawn) {
			// diagonal movement for pawn,
			if (gameBoard[fin.getX()][fin.getY()] == null && Math.abs(fin.getX() - init.getX()) == 1) {
				System.out.println("The pawn can only kill like that.");
				return false;
			}
			else if (gameBoard[fin.getX()][fin.getY()] != null && Math.abs(fin.getX() - init.getX()) == 0) {
				System.out.println("The pawn can't kill like that.");
				return false;
			}
		}
		
		return true;
	}

	private boolean pawnCheckValid(Coordinates init, Coordinates fin) {
		if (Math.abs(fin.getX() - init.getX()) == 1)
			return true;
		return false;
	}

	/**
	 * Runs castling-specific tests on a given move (in terms of coordinates).
	 * @param init The initial set of coordinates.
	 * @param fin The final set of coordinates.
	 * @param color The color of the moving piece.
	 * @return True if every test proves valid, false otherwise.
	 */
	private int castleValid(Coordinates init, Coordinates fin, PieceColor color) {
		if (gameBoard[init.getX()][init.getY()].getType() == PieceType.King) {
			if (Coordinates.inBound(fin.getX() + 1) && gameBoard[fin.getX() + 1][fin.getY()] != null) {
				//checks for castling
				if (gameBoard[fin.getX() + 1][fin.getY()].getType() != PieceType.Rook && (fin.getX() - init.getX()) == 2) {
					System.out.println("Castling can only be done if there is a rook");
					return -1;
				}
				else if (gameBoard[fin.getX() + 1][fin.getY()].getType() == PieceType.Rook && (fin.getX() - init.getX()) == 2 && gameBoard[fin.getX() + 1][fin.getY()].isFirstMove()) {
					return 1;
				}
			}
		}
		
		return 0;
	}
	
	/**
	 * Checks whether or not the path from one set of coordinates to another is open.
	 * @param init The coordinates to start from.
	 * @param fin The coordinates to end on.
	 * @return True if the path is open, false otherwise.
	 */
	private boolean collisionDetect(Coordinates init, Coordinates fin) {
		boolean pathOpen = true;
		
		int xDifference = fin.getX() - init.getX();
		int yDifference = fin.getY() - init.getY();
		
		if (gameBoard[init.getX()][init.getY()].getType() == PieceType.Night) {
			pathOpen = true;
		}
		else if ((Math.abs(xDifference) == 1 || Math.abs(yDifference) == 1) && gameBoard[init.getX()][init.getY()].getType() != PieceType.Pawn) {
		}
		else { 
			if (xDifference < 0) {
				xDifference++;
			}
			else if (xDifference > 0) {
				xDifference--;
			}
			
			if (yDifference < 0) {
				yDifference++;
			}
			else if (yDifference > 0) {
				yDifference--;
			}
			
			if (xDifference == 0) {
				while (yDifference != 0) {
					if (gameBoard[init.getX()][init.getY() + yDifference] != null) {
						pathOpen = false;
					}
					if (yDifference < 0) {
						yDifference++;
					}
					else if (yDifference > 0) {
						yDifference--;
					}
				}
			}
			else if (yDifference == 0) {
				while (xDifference != 0) {
					if (gameBoard[init.getX() + xDifference][init.getY()] != null) {
						pathOpen = false;
					}
					if (xDifference < 0) {
						xDifference++;
					}
					else if (xDifference > 0) {
						xDifference--;
					}
				}
			}
			else {
				while (xDifference != 0 && yDifference != 0) {
					if (gameBoard[init.getX() + xDifference][init.getY() + yDifference] != null) {
						pathOpen = false;
					}
					
					if (xDifference < 0) {
						xDifference++;
					}
					else if (xDifference > 0) {
						xDifference--;
					}
					
					if (yDifference < 0) {
						yDifference++;
					}
					else if (yDifference > 0) {
						yDifference--;
					}
				}
			}
		}
		
		return pathOpen;
	}
}

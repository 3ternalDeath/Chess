package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceType;

/**
 * Handles all the internal logic of a chess game.
 * @author Group 36
 */
public class ChessLogic implements Serializable{
	private static final long serialVersionUID = 211L;
	private final String FILE_NAME = "standard.txt";
	
	private Stack<Coordinates[]> moves;
	private Stack<Piece> deadPieces;
	private Stack<Boolean> firstMoveMoved;
	private Stack<Boolean> promoted;
	
	Piece[][] gameBoard;
	private Player user, computer;
	
	//end game conditions
	private boolean stalemate = false;
	private boolean draw = false;
	
	/**
	 * Default constructor for the ChessLogic class.
	 * @throws FileNotFoundException if chessboard arrangement file is missing.
	 * ONLY USED FOR TESTING, OBJECT LOADED DIRECTLY FROM FILE
	 */
	public ChessLogic() throws FileNotFoundException {
		gameBoard = new Piece[ChessGame.SIZE][ChessGame.SIZE];
		moves = new Stack<Coordinates[]>();
		deadPieces = new Stack<Piece>();
		firstMoveMoved  = new Stack<Boolean>();
		promoted = new Stack<Boolean>();
		
		Scanner file = new Scanner(new File("src/engine/" + FILE_NAME));
		
		for (int y = ChessGame.SIZE - 1; y >= 0; y--) {
			for (int x = 0; x < ChessGame.SIZE; x++) {
				//New button
				Coordinates coor = new Coordinates(x, y);
				String piece = file.next();
				gameBoard[x][y] = Piece.createPiece(coor, piece);
				
				if (getPieceAt(coor) != null) {
					if (getType(coor) == PieceType.King) {
						if (getColor(coor) == PieceColor.White)
							setUser(new Player(PieceColor.White, PlayerType.User, true, coor));
						else if (getColor(coor) == PieceColor.Black)
							setComputer(new Player(PieceColor.Black, PlayerType.Computer, false, coor));
					}
				}
			}
		}
		file.close();
	}

	/**
	 * Copy constructor for the ChessLogic class.
	 * @param copy The ChessLogic object to copy.
	 */
	public ChessLogic(ChessLogic copy) {
		gameBoard = new Piece[ChessGame.SIZE][ChessGame.SIZE];
		for (int x = 0; x < ChessGame.SIZE; x++)
			for (int y = 0; y < ChessGame.SIZE; y++)
				gameBoard[x][y] = copy.getPieceAt(new Coordinates(x, y));
				
	}
	
	/**
	 * Returns a copy of the piece on the board at a given location.
	 * @param location The coordinates to copy.
	 * @return The copied piece (or null pointer) at the given location.
	 */
	public Piece getPieceAt(Coordinates location) {
		Piece piece = null;
		
		if (gameBoard[location.getX()][location.getY()] != null) {
			Piece newPiece = gameBoard[location.getX()][location.getY()];
			piece = Piece.createPiece(location, newPiece.getType(), newPiece.getColor(), newPiece.isFirstMove());
		}
		
		return piece;
	}
	
	/**
	 * Returns the type of the piece at a given set of coordinates.
	 * @param coor The set of coordinates to check.
	 * @return The type of the piece.
	 */
	public PieceType getType(Coordinates coor) {
		return getType(coor.getX(), coor.getY());
	}
	
	/**
	 * Returns the color of the piece at a given set of coordinates.
	 * @param coor The set of coordinates to check.
	 * @return The color of the piece.
	 */
	public PieceColor getColor(Coordinates coor) {
		return getColor(coor.getX(), coor.getY());
	}
	
	/**
	 * Returns the player user.
	 * @return The player object.
	 */
	public Player getUser() {
		return user;
	}
	
	/**
	 * Returns the player computer.
	 * @return The player object.
	 */
	public Player getComputer() {
		return computer;
	}
	
	/**
	 * Returns the state of draw.
	 * @return The state of draw.
	 */
	public boolean getDraw() {
		return draw;
	}
	
	/**
	 * Returns the state of stalemate.
	 * @return the state of stalemate.
	 */
	public boolean getStalemate() {
		return stalemate;
	}
	
	/**
	 * Puts a given piece at a given location on the board.
	 * @param location The location on which to place the piece.
	 * @param piece The piece in question.
	 */
	public void setPieceAt(Coordinates location, Piece piece) {
		Piece newPiece = piece;
		gameBoard[location.getX()][location.getY()] = newPiece;
	}
	
	/**
	 * Changes the user to the desired player.
	 * @param user player to be changed to.
	 */
	private void setUser(Player user) {
		this.user = user;
	}
	
	/**
	 * Changes the computer to the desired player.
	 * @param computer player to be changed to.
	 */
	private void setComputer(Player computer) {
		this.computer = computer;
	}
	
	/**
	 * Changes the state of draw.
	 * @param draw the state of draw to be changed to.
	 */
	public void setDraw(boolean draw){
		this.draw = draw;
	}
	
	/**
	 * Changes the state of stalemate.
	 * @param stalemate the state of stalemate to be changed to.
	 */
	public void setStalemate(boolean stalemate){
		this.stalemate = stalemate;
	}
	
	/**
	 * Returns the end state of the game.
	 * @return True if the game ended, false otherwise.
	 */
	public boolean gameOver(){
		
		return getUser().isLost() || getComputer().isLost() || getStalemate() || getDraw();
	}
	
	/**
	 * Makes a single move based on a given series of coordinates. Adjusts all logic attributes
	 * according to the move's effect on the game state.
	 * @param init The coordinates of the piece being moved.
	 * @param fin The coordinates to move to.
	 */
	public void movePiece(Coordinates init, Coordinates fin) {
		//Set king coordinates for current player
		if (getType(init) == PieceType.King) {
			if (getUser().isMyTurn()) user.setKingCoor(fin);
			else                  computer.setKingCoor(fin);
		}
		
		//Castling
		if (moveIsCastle(init, fin))
			makeCastleMove(init, fin);
		else {
			moves.add(new Coordinates[]{new Coordinates(init), new Coordinates(fin)});
			deadPieces.add(getPieceAt(fin));
		}
		//Moving piece
		Piece piece = getPieceRef(init);
		firstMoveMoved.add(piece.isFirstMove());
		piece.move(fin);
		setPieceAt(init, null);
		setPieceAt(fin, piece);
		
		//Promotion
		promoted.add(promotion(fin));
		
		//Checking endgame conditions
		updateCheckmate(getUser(), computer);
		updateStalemate();
		checkDraw();
		
		//Displaying game message
		StringBuffer msg = new StringBuffer();
		if (getUser().isInCheck()) msg.append("You are in check! ");
		if (getStalemate())        msg.append("Gameover. Stalemate! ");
		if (getDraw())             msg.append("Gameover. It's a draw! ");
		if (!getUser().isLost() && !getComputer().isLost()) msg.append("Make a move!");
		ChessGame.gameMsg(msg.toString(), true);
	}

	public boolean promotion(Coordinates fin){
		boolean promo = false;
		if (getType(fin) == PieceType.Pawn) {
			if (fin.getY() == 0 || fin.getY() == 7) {
				gameBoard[fin.getX()][fin.getY()] = Piece.createPiece(fin, PieceType.Queen, getColor(fin), false);
				promo = true;
			}
		}
		return promo;
	}
	
	/**
	 * Undoes one move. Must be called twice to undo an entire turn.
	 */
	public void undoMove() {
		//Start of game
		if (moves.size() == 0)
			ChessGame.gameMsg("No moves to undo!", true);
		else {
			Coordinates[] move = moves.pop();
		
			//Undo castling
			if (move.length == 4) {
				Coordinates init = move[3];
				Coordinates fin = move[2];
				Piece piece = getPieceRef(init);
				piece.move(fin);
			
				setPieceAt(init, null);
				setPieceAt(fin, piece);
			}
			
			//Undo move
			Coordinates init = move[1];
			Coordinates fin = move[0];
			Piece piece = getPieceRef(init);
			piece.move(fin);
		
			//Undo firstMove
			if (firstMoveMoved.pop())
				piece = Piece.createPiece(fin, piece.getType(), piece.getColor(), true);
			
			//Undo promotion
			if (promoted.pop())
				piece = Piece.createPiece(fin, PieceType.Pawn, piece.getColor(), false);
		
			if (move.length == 2)
				setPieceAt(init, deadPieces.pop());
			else
				setPieceAt(init, null);
		
			setPieceAt(fin, piece);
		}
	}
	
	/**
	 * Rolls game progression over to the next turn.
	 */
	public void nextTurn() {
		
		//Switch turns
		getUser().switchTurn();
		computer.switchTurn();

		//Computer turn
		if (computer.isMyTurn() && !computer.isLost()) {
			compMove(computer.getColor());
			nextTurn();
		}
	}
	
	/**
	 * Checks whether a set of coordinates function as the first half of a full move.
	 * If coordinates are invalid, prints a message to the screen explaining why they are invalid.
	 * Will always print to the screen, as validInit is never called for theoretical purposes.
	 * @param init The set of coordinates to check.
	 * @param color The color of the player making the half-move.
	 * @return True if the coordinates are a valid starting point, false otherwise.
	 */
	public boolean validInit(Coordinates init, PieceColor color) {
		boolean valid = true;
		
		if (gameBoard[init.getX()][init.getY()] == null) {
			ChessGame.gameMsg("Selecting empty space.", true);
			valid = false;
		}
		//initial coordinates point to other player's piece
		else if (getColor(init) != color) {
			ChessGame.gameMsg("Selecting opponent's piece. Your color is " + color + ".", true);
			valid = false;
		}
		
		if (valid) ChessGame.gameMsg("Selected Piece: " + getType(init) + ".", true);
		return valid;
	}
	
	/**
	 * Checks whether a series of coordinates create a legal move.
	 * If coordinates are invalid, correspond to a white move, and non-hypothetical,
	 * prints a message to the screen explaining why they are invalid.
	 * @param init The coordinates to move from.
	 * @param fin The coordinates to move to.
	 * @param player The player making the move.
	 * @param inReality Whether or not the move is actually occurring or is hypothetical.
	 * @return True if coordinates are valid, false otherwise.
	 */
	public boolean validFin(Coordinates init, Coordinates fin, Player player, boolean inReality) {
		boolean valid = validFinAux(init, fin, player.getColor(), inReality);
		
		if (valid) {
			if (!checkNextMoveCheck(init, fin, player)) {
				if (player.isInCheck()) {ChessGame.gameMsg("Get out of check!", inReality);}
				else {ChessGame.gameMsg("Don't get into check!", inReality);}
				valid = false;
			}
		}
		return valid;
	}
	
	/**
	 * Checks whether a series of coordinates create a legal move.
	 * If coordinates are invalid, correspond to a white move, and non-hypothetical,
	 * prints a message to the screen explaining why they are invalid.
	 * Differs from validFin to avoid infinite recursion--validFin calls checkNextMoveCheck,
	 * which calls validFinAux rather than validFin.
	 * @param init The coordinates to move from.
	 * @param fin The coordinates to move to.
	 * @param color The color of the player making the move.
	 * @param inReality Whether or not the move is actually occurring or is hypothetical.
	 * @return True if coordinates are valid, false otherwise.
	 */
	public boolean validFinAux(Coordinates init, Coordinates fin, PieceColor color, boolean inReality) {
		boolean valid = true;
		
		//final coordinates point to the player's own piece
		if (gameBoard[fin.getX()][fin.getY()] != null && getColor(fin) == getColor(init)) {
			ChessGame.gameMsg("Moving into your own piece.", inReality);
			valid = false;
		}

		// coordinates cause an illegal move
		else if (!gameBoard[init.getX()][init.getY()].validMove(fin)) {
			ChessGame.gameMsg("The "+ gameBoard[init.getX()][init.getY()].getType() +" at " + init + " can't move to " + fin + ".", inReality);
			valid = false;
		}
		
		else if (!collisionDetect(init, fin)) {
			ChessGame.gameMsg("There's something in the way.", inReality);
			valid = false;
		}
		
		if (getType(init) == PieceType.Pawn) {
			//attempting diagonal movement to an empty space
			if (gameBoard[fin.getX()][fin.getY()] == null && Math.abs(fin.getX() - init.getX()) == 1) {
				ChessGame.gameMsg("The pawn can only kill like that.", inReality);
				valid = false;
			}
			//attempting to kill another piece head-on
			else if (gameBoard[fin.getX()][fin.getY()] != null && (fin.getX() - init.getX()) == 0) {
				ChessGame.gameMsg("The pawn can't kill like that.", inReality);
				valid = false;
			}
		}
		
		if (valid && getType(init) == PieceType.King) {
			if (Math.abs(init.getX() - fin.getX()) > 1) {
				if (!moveIsCastle(init, fin)) {
					valid = false;
				}
			}
		}
		
		if (valid) ChessGame.gameMsg("Make a move, Player 1!", inReality);
		
		return valid;
	}
	
	/**
	 * Generates a move for the computer player. If there is a move which allows the computer
	 * to take one of the user's pieces, chooses that move. Otherwise, chooses a move at random.
	 * @param color The color of the computer player's pieces.
	 */
	public void compMove(PieceColor color) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		Coordinates init = new Coordinates();
		Coordinates fin = new Coordinates();

		// Finds all pieces
		for (int x = 0; x < ChessGame.SIZE; x++) {
			for (int y = 0; y < ChessGame.SIZE; y++) {
				if (gameBoard[x][y] != null) {
					if (gameBoard[x][y].getColor() == color) {
						pieces.add(gameBoard[x][y]);
					}
				}
			}
		}
		
		//Find a move in which it can kill a piece.
		//Piece that's farthest right then farthest to the top that can kill a piece.
		for (Piece piece : pieces) {
			for (Coordinates coor : piece.getPossibleMoves()) {
				if (gameBoard[coor.getX()][coor.getY()] != null) {
					if (gameBoard[coor.getX()][coor.getY()].getColor() != color &&
						gameBoard[coor.getX()][coor.getY()].getType() != PieceType.King) {
						if (validFin(piece.getCoordinates(), coor, getPlayerRef(color), false)) {
							init = piece.getCoordinates();
							fin = new Coordinates(coor);
						}
					}
				}
			}
		}
		
		//Randomly moves if no possible kill is found.
		//initial and final coordinates wouldn't be equal if valid kill is found.
		if (init.equals(fin)) {
			Coordinates[] movement = new Coordinates[2];
			movement = randomMove(pieces, color);
			init = movement[0];
			fin = movement[1];
		}
		
		//move piece
		movePiece(init, fin);
	}
	
	
	/**
	 * Randomly chooses a piece to move, then randomly chooses from possible moves.
	 * @param pieces The ArrayList of all pieces for that color.
	 * @param color The color of the moving piece.
	 * @return An array containing the initial and final coordinates.
	 */
	private Coordinates[] randomMove(ArrayList<Piece> pieces, PieceColor color) {
		boolean goodMove = false;
		
		Coordinates[] movement = new Coordinates[2];
		Coordinates init = new Coordinates();
		Coordinates fin = new Coordinates();
		Random num = new Random();
		
		while (!pieces.isEmpty() && !goodMove) {
				
			//Picks a random piece, then sees if movement is possible.
			Piece piece = pieces.get(num.nextInt(pieces.size()));
			init = piece.getCoordinates();

			ArrayList<Coordinates> coors = new ArrayList<Coordinates>();
			//Possible moves of that piece
			for (Coordinates coor : gameBoard[init.getX()][init.getY()].getPossibleMoves()) {
				if (validFin(init, coor, getPlayerRef(color), false)) {
					coors.add(coor);
					goodMove = true;
				}
			}

			//If there is a good move, pick a random one
			if(goodMove){
				Coordinates coor = coors.get(num.nextInt(coors.size()));
				fin = new Coordinates(coor);
			}

			//Removes piece from movable pieces;
			pieces.remove(piece);
		}
		
		if (!goodMove) stalemate = true;
		
		//coordinates into array
		movement[0] = init;
		movement[1] = fin;
		
		return movement;
	}
	
	/**
	 * Checks whether or not a king of a given color is in check.
	 * @param king The coordinates of the king.
	 * @param color The color of the king.
	 * @param aux Switches whether to call validFin or validFinAux.
	 * @return True if the king is in check, false otherwise.
	 */
	public boolean checkCheck(Coordinates king, PieceColor color, boolean aux) {
		boolean check = false;

		ArrayList<Piece> checking = new ArrayList<Piece>();
		
		//run through every element in the board
		for (int x = 0; x < ChessGame.SIZE; x++) {
			for (int y = 0; y < ChessGame.SIZE; y++) {
				//if there's a piece belonging to the other player at the index
				if (getColor(x, y) != null && getColor(x, y) != color) {
					if (!aux) {
						if (validFin(new Coordinates(x, y), new Coordinates(king), getPlayerRef(getColor(x, y)), false)) {
							check = true;
							checking.add(gameBoard[x][y]);
						}
					}
					else {
						if (validFinAux(new Coordinates(x, y), new Coordinates(king), getColor(x, y), false)) {
							check = true;
							checking.add(gameBoard[x][y]);
						}
					}
				}
				
			}
		}
		return check;
	}
	
	/**
	 * Tests a particular move (in terms of a group of coordinates) without directly affecting the chessboard.
	 * @param init The initial set of coordinates.
	 * @param fin The final set of coordinates.
	 */
	public void makeTestMove(Coordinates init, Coordinates fin) {
		Piece piece = gameBoard[init.getX()][init.getY()];
		piece.move(fin);
		gameBoard[init.getX()][init.getY()] = null;
		gameBoard[fin.getX()][fin.getY()] = piece;
	}
	
	/**
	 * Returns the color of the piece at a given set of coordinates.
	 * @param x The x coordinate to check.
	 * @param y The y coordinate to check.
	 * @return The color of the piece.
	 */
	private PieceColor getColor(int x, int y) {
		if (gameBoard[x][y] != null)
			return gameBoard[x][y].getColor();
		else
			return null;
	}
	
	/**
	 * Returns the type of the piece at a given set of coordinates.
	 * @param x The x coordinate to check.
	 * @param y The y coordinate to check.
	 * @return The type of the piece.
	 */
	private PieceType getType(int x, int y) {
		if (gameBoard[x][y] != null)
			return gameBoard[x][y].getType();
		else
			return null;
	}
	
	/**
	 * Returns the reference to a piece at a given location on the chessboard.
	 * @param x The x coordinates on the chessboard to return.
	 * @param y The y coordinates on the chessboard to return.
	 * @return The reference to the specific piece (or null pointer) at the given location.
	 */
	private Piece getPieceRef(int x, int y) {
		return gameBoard[x][y];
	}
	
	/**
	 * Returns the reference to a piece at a given location on the chessboard.
	 * @param location The coordinates on the chessboard to return.
	 * @return The reference to the specific piece (or null pointer) at the given location.
	 */
	private Piece getPieceRef(Coordinates location) {
		return getPieceRef(location.getX(), location.getY());
	}
	
	/**
	 * Checks whether or not a given series of coordinates creates a valid castling move.
	 * @param init The coordinates of the piece being moved.
	 * @param fin The coordinates to move to.
	 * @return True if the coordinates are a valid castling move, false otherwise.
	 */
	private boolean moveIsCastle(Coordinates init, Coordinates fin) {
		boolean castle = false;

		if (getType(init) == PieceType.King) {
			if (getPieceRef(init).isFirstMove()) {
				if ((fin.getX() - init.getX()) == 2) {
					// moving right
					if (getType(fin.getX() + 1, fin.getY()) == PieceType.Rook) {
						if (getPieceRef(fin.getX() + 1, fin.getY()).isFirstMove()) {
							castle = true;
						}
					}
				} 
				else if ((fin.getX() - init.getX()) == -2) {
					// moving left
					if (getType(fin.getX() - 2, fin.getY()) == PieceType.Rook
							&& getType(fin.getX() - 1, fin.getY()) == null) {
						if (getPieceRef(fin.getX() - 2, fin.getY()).isFirstMove()) {
							castle = true;
						}
					}
				}
			}
		}
		return castle;
	}
	
	/**
	 * Castles a rook piece and king piece.
	 * @param init The coordinates of the piece being moved.
	 * @param fin The coordinates to move to.
	 */
	private void makeCastleMove(Coordinates init, Coordinates fin) {
		if ((fin.getX() - init.getX()) == 2) {
			//moving right
			Coordinates rook = new Coordinates(fin.getX() + 1, fin.getY());
			Coordinates rookTo = new Coordinates(fin.getX() - 1, fin.getY());
			Piece piece = getPieceRef(rook);
			moves.add(new Coordinates[]{new Coordinates(init), new Coordinates(fin), rook, rookTo});
			
			piece.move(rookTo);
			setPieceAt(rook, null);
			setPieceAt(rookTo, piece);
		}
		else if ((fin.getX() - init.getX()) == -2) {
			//moving left
			Coordinates rook = new Coordinates(fin.getX() - 2, fin.getY());
			Coordinates rookTo = new Coordinates(fin.getX() + 1, fin.getY());
			Piece piece = getPieceRef(rook);
			moves.add(new Coordinates[]{new Coordinates(init), new Coordinates(fin), rook, rookTo});
			
			piece.move(rookTo);
			setPieceAt(rook, null);
			setPieceAt(rookTo, piece);
		}
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
		
		//shifts x and y towards zero 
		xDifference = shift(xDifference);
		yDifference = shift(yDifference);

		//if piece is a knight, then simply let it through
		if (gameBoard[init.getX()][init.getY()].getType() == PieceType.Night) {}
		else {
			//while x and y aren't both zero
			while (xDifference != 0 || yDifference != 0) {
				//if there is a piece in the way
				if (gameBoard[init.getX() + xDifference][init.getY() + yDifference] != null) {
					pathOpen = false;
				}
				
				//shifts x and y towards zero 
				xDifference = shift(xDifference);
				yDifference = shift(yDifference);
				
			}
		}
		return pathOpen;
	}
	
	/**
	 * Shifts a given number towards zero.
	 * @param num The number to shift.
	 * @return The number after shifting.
	 */
	private int shift(int num) {
		
		//Shifts number towards zero.
		if 		(num < 0) num++;
		else if (num > 0) num--;
		
		return num;
	}
	
	/**
	 * Returns the reference to a player of a given color.
	 * @param color The player's color.
	 * @return The reference to the player or null pointer.
	 */
	private Player getPlayerRef(PieceColor color) {
		
		Player player;
	
		if (getUser().getColor() == color)	    	player = getUser();
		else if (computer.getColor() == color)	player = computer;
		else									player = null;
		
		return player;
	}
	
	/**
	 * Updates the checkmate status of both players in the game.
	 * @param user The game's user.
	 * @param computer The game's computer.
	 */
	private void updateCheckmate(Player user, Player computer) {
		updateCheckmate(user);
		updateCheckmate(computer);
	}
	
	/**
	 * Checks whether a given player is in checkmate and adjusts the player's checkmate status accordingly.
	 * @param player The player to check.
	 */
	private void updateCheckmate(Player player) {
		updateCheck(player, false);
		
		//checks for checkmate
		if (player.isInCheck()) {
			if (!checkAllMoves(player)) {
				player.setLost(true);
				ChessGame.gameMsg(player.getColor() + " is in checkmate!", true);
			}
		}
	}
	
	/**
	 * Checks whether a given player is in check and adjusts the player's check status accordingly.
	 * @param player The player to check.
	 * @param aux Switch for whether to call validFin or validFinAux.
	 */
	private void updateCheck(Player player, boolean aux) {
		
		if (checkCheck(player.getKingCoor(), player.getColor(), aux)) {
			//player is in check
			player.setInCheck(true);
		}
		else {
			//player is not in check
			player.setInCheck(false);
			player.setLost(false);
		}
	}
	
	/**
	 * Checks whether or not a given player is able to leave check.
	 * @param player The player to check.
	 * @return True if the player can leave check, false otherwise.
	 */
	private boolean checkAllMoves(Player player) {
		boolean canLeave = false;
		
		//check every element in the board
		for (int x = 0; x < ChessGame.SIZE; x++)
			for (int y = 0; y < ChessGame.SIZE; y++) {
				//if there's a piece belonging to the player at the index
				if (getColor(x, y) == player.getColor()) {
					Coordinates piece = new Coordinates(x,y);
					ArrayList<Coordinates> moves = gameBoard[x][y].getPossibleMoves();
					//run through all its moves for next move check
					for (Coordinates move : moves) {
						if (checkNextMoveCheck(piece, move, player)) {
							canLeave = true;
						}
					}

				}
			}
		return canLeave;
	}
	
	/**
	 * Checks whether a given move (in terms of a group of coordinates) would allow
	 * a given player to leave check.
	 * @param init The initial set of coordinates.
	 * @param fin The final set of coordinates.
	 * @param player The player making the move.
	 * @return True if the move would put the player out of check, false otherwise.
	 */
	private boolean checkNextMoveCheck(Coordinates init, Coordinates fin, Player player) {
		boolean leaveCheck = true;
		
		ChessLogic nextMove = new ChessLogic(this);
		
		//creates fake player
		Player testPlayer = new Player(player);
		
		if (validFinAux(init, fin, testPlayer.getColor(), false)) {
			if (nextMove.getType(init) == PieceType.King) {
				testPlayer.setKingCoor(fin);
			}
			nextMove.makeTestMove(init, fin);
		}
		
		nextMove.updateCheck(testPlayer, true);

		if (testPlayer.isInCheck()) {
			leaveCheck = false;
		}
		
		return leaveCheck;
	}
	private void updateStalemate() {
		if (getUser().isMyTurn()) checkStalemate(getUser());
		else 	       			  checkStalemate(getComputer());
	}
	
	/**
	 * Checks whether or not a player is in stalemate.
	 * @param player The player to check.
	 */
	private void checkStalemate(Player player) {
		boolean stalemate = true;
		
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		Coordinates init = new Coordinates();
		PieceColor color = player.getColor();
		
		//Finds all pieces
		for (int x = 0; x < ChessGame.SIZE;x++) {
			for (int y = 0; y < ChessGame.SIZE; y++) {
				if (gameBoard[x][y] != null) {
					if (gameBoard[x][y].getColor() == color) {
						pieces.add(gameBoard[x][y]);
					}
				}
			}
		}
		
		//For each piece, find possible moves
		for (Piece piece : pieces) {
			if (!pieces.isEmpty()) {
				init = piece.getCoordinates();
				for (Coordinates coor : gameBoard[init.getX()][init.getY()].getPossibleMoves()) {
					if (validFin(init, coor, getPlayerRef(color), false)) {
						stalemate = false;
					}
				}
			}
		}
		setStalemate(stalemate);
	}
	
	/**
	 * Checks whether or not the game has resulted in a draw.
	 */
	private void checkDraw() {
		boolean draw = false;
		
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		
		//All pieces that are not kings
		for (int x = 0; x < ChessGame.SIZE;x++) {
			for (int y = 0; y < ChessGame.SIZE; y++) {
				if (gameBoard[x][y] != null && gameBoard[x][y].getType()!= PieceType.King) {
					pieces.add(gameBoard[x][y]);
				}
			}
		}
		
		//No other pieces
		if (pieces.isEmpty()) {
			draw = true;
		}
		
		//Only other piece is a bishop or a knight. Impossible to win.
		else if (pieces.size() == 1) {
			if (pieces.get(0).getType() == PieceType.Bishop) {
				draw = true;
			}
			if (pieces.get(0).getType() == PieceType.Night) {
				draw = true;
			}
		}
		
		//Only other pieces are two nights. Impossible to win.s
		else if (pieces.size() == 2) {
			if (pieces.get(0).getType() == PieceType.Night && pieces.get(1).getType() == PieceType.Night) {
				draw = true;
			}
		}
		setDraw(draw);
	}
	
	
	

}

package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceType;

/**
 * Handles all the internal logic of a chess game.
 * @author Group 36
 */
public class ChessLogic implements Serializable{
	private static final long serialVersionUID = 211L;
	private final String FILE_NAME = "standard1.txt";
	private Stack<Coordinates[]> moves;
	private Stack<Piece> deadPieces;
	private Stack<Boolean> firstMoveMoved;
	private Stack<Boolean> promoted;
	private boolean stalemate = false;
	Piece[][] gameBoard;
	private Player player1, player2;
	
	/**
	 * Default constructor for the ChessLogic class.
	 * @throws FileNotFoundException if chessboard arrangement file is missing.
	 */
	public ChessLogic(PlayerType p2) throws FileNotFoundException {
		gameBoard = new Piece[ChessGame.SIZE][ChessGame.SIZE];
		moves = new Stack<Coordinates[]>();
		deadPieces = new Stack<Piece>();
		firstMoveMoved  = new Stack<Boolean>();
		promoted = new Stack<Boolean>();
		
		Scanner file = new Scanner(new File("src/engine/" + FILE_NAME));
		
		for (int y = ChessGame.SIZE - 1; y >= 0; y--) {
			for (int x = 0; x < ChessGame.SIZE; x++) {
				// new Button
				Coordinates coor = new Coordinates(x, y);
				String piece = file.next();
				gameBoard[x][y] = Piece.createPiece(coor, piece);
				
				if (getPieceAt(coor) != null) {
					if (getType(coor) == PieceType.King) {
						if (getColor(coor) == PieceColor.White) {
							player1 = new Player(PieceColor.White, PlayerType.User, true, coor);
						} 
						else if (getColor(coor) == PieceColor.Black) {
							player2 = new Player(PieceColor.Black, p2, false, coor);
						}
					}
				}
			}
		}
		
		file.close();
	}
	
	/**
	 * Copy constructor for the ChessLogic class.
	 * @param logic The ChessLogic object to copy.
	 */
	public ChessLogic(ChessLogic copy) {
		gameBoard = new Piece[ChessGame.SIZE][ChessGame.SIZE];
		for(int x = 0; x < ChessGame.SIZE; x++) {
			for(int y = 0; y < ChessGame.SIZE; y++) {
				gameBoard[x][y] = copy.getPieceAt(new Coordinates(x, y));
			}
		}
	}
	
	/**
	 * Makes a single move based on a given series of coordinates. Adjusts all logic attributes
	 * according to the move's effect on the game state.
	 * @param init The coordinates of the piece being moved.
	 * @param fin The coordinates to move to.
	 */
	public void movePiece(Coordinates init, Coordinates fin) {
		if (getType(init) == PieceType.King) {
			if (player1.isMyTurn())
				player1.setKingCoor(fin);
			else if (player2.isMyTurn())
				player2.setKingCoor(fin);
		}
		
		if(moveIsCastle(init, fin)){
			makeCastleMove(init, fin);
		}
		else{
			moves.add(new Coordinates[]{new Coordinates(init), new Coordinates(fin)});
			deadPieces.add(getPieceAt(fin));
		}
		
		Piece piece = getPieceRef(init);
		firstMoveMoved.add(piece.isFirstMove());
		piece.move(fin);
		
		setPieceAt(init, null);
		setPieceAt(fin, piece);
		
		boolean promo = false;
		if (getType(fin) == PieceType.Pawn){
			if (fin.getY() == 0 || fin.getY() == 7){
				gameBoard[fin.getX()][fin.getY()] = Piece.createPiece(fin, PieceType.Queen, getColor(fin), false);
				promo = true;
			}
		}
		
		promoted.add(promo);
		
		updateCheckMate(player1, player2);
		
		StringBuffer msg = new StringBuffer();
		if (player1.isInCheck()) {
			msg.append(player1.getColor() + " is in check!");
		}

		if (player2.isInCheck()) {
			msg.append(player2.getColor() + " is in check!");
		} 
		
		if(player1.isMyTurn() || player2.getType() == PlayerType.Computer){
			msg.append("Make a move, Player 1!");
		}
		
		if(player2.isMyTurn() && player2.getType() != PlayerType.Computer){
			msg.append("Make a move, Player 2!");
		}
		
		ChessGame.gameMsg(msg.toString(), true);
		
		
		if (player1.isMyTurn())
			checkStalemate(player1);
		else if (player2.isMyTurn())
			checkStalemate(player2);
		
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
				} else if ((fin.getX() - init.getX()) == -2) {
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
	private void makeCastleMove(Coordinates init, Coordinates fin){
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
	 * Undoes one move. Must be called twice to undo an entire turn.
	 */
	public void undoMove() {
		if(moves.size() == 0) {
			ChessGame.gameMsg("No moves to undo!", true);
		}
		else {
			Coordinates[] move = moves.pop();
		
			if(move.length == 4){
				Coordinates init = move[3];
				Coordinates fin = move[2];
				Piece piece = getPieceRef(init);
				piece.move(fin);
			
				setPieceAt(init, null);
				setPieceAt(fin, piece);
			}
		
			Coordinates init = move[1];
			Coordinates fin = move[0];
			Piece piece = getPieceRef(init);
			piece.move(fin);
		
			if(firstMoveMoved.pop()){
				piece = Piece.createPiece(fin, piece.getType(), piece.getColor(), true);
			}
			if(promoted.pop()){
				piece = Piece.createPiece(fin, PieceType.Pawn, piece.getColor(), false);
			}
		
			if(move.length == 2)
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
		player1.switchTurn();
		player2.switchTurn();

		if (player1.getType() == PlayerType.Computer && player1.isMyTurn() && !player1.isLost()) {
			pause(250);
			compMove(player1.getColor());
			nextTurn();
		}
		else if (player2.getType() == PlayerType.Computer && player2.isMyTurn() && !player2.isLost()) {
			pause(250);
			compMove(player2.getColor());
			nextTurn();
		}
	}
	
	private void pause(int ms){

		try {
			TimeUnit.MILLISECONDS.sleep(ms);

		} catch (InterruptedException e) {

			e.printStackTrace();

		}

	}

	
	
	/**
	 * Returns the piece on the chessboard at a given location.
	 * @param location The coordinates on the chessboard to return.
	 * @return The piece (or null pointer) at the given location.
	 */
	public Piece getPieceAt(Coordinates location) {
		if (gameBoard[location.getX()][location.getY()] != null) {
			Piece newPiece = gameBoard[location.getX()][location.getY()];
			return Piece.createPiece(location, newPiece.getType(), newPiece.getColor(), newPiece.isFirstMove());
		}
		else {
			return null;
		}
	}
	
	/**
	 * Returns the reference to a piece at a given location on the chessboard.
	 * @param location The coordinates on the chessboard to return.
	 * @return The reference to the specific piece (or null pointer) at the given location.
	 */
	private Piece getPieceRef(Coordinates location){
		return getPieceRef(location.getX(), location.getY());
	}
	
	/**
	 * Returns the reference to a piece at a given location on the chessboard.
	 * @param x The x coordinates on the chessboard to return.
	 * @param y The y coordinates on the chessboard to return.
	 * @return The reference to the specific piece (or null pointer) at the given location.
	 */
	private Piece getPieceRef(int x, int y){
		return gameBoard[x][y];
	}
	
	/**
	 * Puts a given piece at a given location on the chessboard.
	 * @param location The location on which to place the piece.
	 * @param piece The piece in question.
	 */
	public void setPieceAt(Coordinates location, Piece piece) {
		if (piece != null) {
			Piece newPiece = piece;
			gameBoard[location.getX()][location.getY()] = newPiece;
		}
		else
			gameBoard[location.getX()][location.getY()] = null;
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
	 * Returns the type of the piece at a given set of coordinates.
	 * @param x The x coordinate to check.
	 * @param y The y coordinate to check.
	 * @return The type of the piece.
	 */
	private PieceType getType(int x, int y){
		if (gameBoard[x][y] != null)
			return gameBoard[x][y].getType();
		else
			return null;
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
	 * Returns the color of the piece at a given set of coordinates.
	 * @param x The x coordinate to check.
	 * @param y The y coordinate to check.
	 * @return The color of the piece.
	 */
	private PieceColor getColor(int x, int y){
		if (gameBoard[x][y] != null)
			return gameBoard[x][y].getColor();
		else
			return null;
	}
	
	/**
	 * Returns the losing state of player 1.
	 * @return True if player 1 has lost, false otherwise.
	 */
	public boolean p1Lost() {
		return player1.isLost();
	}
	
	/**
	 * Returns the losing state of player 2.
	 * @return True if player 2 has lost, false otherwise.
	 */
	public boolean p2Lost() {
		return player2.isLost();
	}
	
	/**
	 * Returns the stalemate state of the game.
	 * @return True if the game is in stalemate, false otherwise.
	 */
	public boolean stalemate() {
		return stalemate;
	}
	
	/**
	 * Returns a player object with the same attributes as player 1.
	 * @return The new player object.
	 */
	public Player getP1() {
		return new Player(player1);
	}
	
	/**
	 * Returns a player object with the same attributes as player 2.
	 * @return The new player object.
	 */
	public Player getP2() {
		return new Player(player2);
	}
	
	public Player getCurrentPlayer(){
		if(player1.isMyTurn())
			return getP1();
		if(player2.isMyTurn())
			return getP2();
		
		return null;
	}
	
	/**
	 * Checks whether a set of coordinates function as the first half of a full move.
	 * If coordinates are invalid and inputted by the user, prints a message to the screen
	 * explaining why they are invalid.
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
		
		if(valid) ChessGame.gameMsg("Selected Piece: " + getType(init) + ".", true);
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
		
		if(valid){
			if(!checkNextMoveCheck(init, fin, player)){
				if(player.isInCheck()) {ChessGame.gameMsg("Get out of check!", inReality);}
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
	 * TODO: explain how this differs from validFin
	 * @param init The coordinates to move from.
	 * @param fin The coordinates to move to.
	 * @param player The player making the move.
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
		
		else if(!collisionDetect(init, fin)) {
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
		
		if(valid && getType(init) == PieceType.King){
			if(Math.abs(init.getX() - fin.getX()) > 1){
				if(!moveIsCastle(init, fin)){
					valid = false;
				}
			}
		}
		
		if(valid) ChessGame.gameMsg("Make a move, Player 1!", inReality);
		
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
		for (Piece piece : pieces){
			for (Coordinates coor : piece.getPossibleMoves()){
				if(gameBoard[coor.getX()][coor.getY()] != null){
					if(gameBoard[coor.getX()][coor.getY()].getColor() != color &&
							gameBoard[coor.getX()][coor.getY()].getType() != PieceType.King){
						if(validFin(piece.getCoordinates(), coor, getPlayerRef(color), false)) {
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
	private Coordinates[] randomMove(ArrayList<Piece> pieces, PieceColor color){
		Coordinates[] movement = new Coordinates[2];
		Coordinates init = new Coordinates();
		Coordinates fin = new Coordinates();
		Random num = new Random();
		
		boolean goodMove = false;
		do {
			if (!pieces.isEmpty()){
				
				//Picks a random piece, then sees if movement is possible.
				Piece piece = pieces.get(num.nextInt(pieces.size()));
				init = piece.getCoordinates();
				
				//possible moves of that piece
				for (Coordinates coor : gameBoard[init.getX()][init.getY()].getPossibleMoves()) {
					if(validFin(init, coor, getPlayerRef(color), false)) {
						fin = new Coordinates(coor);
						goodMove = true;
					}
				}
				
				//removes piece from movable pieces;
				pieces.remove(piece);
			}
			//No piece can move
			else{
				stalemate = true;
				ChessGame.gameMsg("Stalemate!", true);
			}
		} while (!goodMove);
		
		//coordinates into array
		movement[0] = init;
		movement[1] = fin;
		
		return movement;
				
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
		if (gameBoard[init.getX()][init.getY()].getType() == PieceType.Night){}
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
	 * Shifts the number towards zero.
	 * @param num The number to shift.
	 * @return The number after shifting.
	 */
	private int shift(int num){
		//moves number towards zero
		if 		(num < 0) num++;
		else if (num > 0) num--;
		
		return num;
	}
	
	/**
	 * Returns the reference to a player of a given color.
	 * @param color The player's color.
	 * @return The reference to the player or null pointer.
	 */
	private Player getPlayerRef(PieceColor color){
		if(player1.getColor() == color){
			return player1;
		}
		else if(player2.getColor() == color){
			return player2;
		}
		else
			return null;
	}
	
	/**
	 * @param king the coordinates of the king
	 * @param color the color of the king
	 * TODO: explain what aux is
	 * @return true if king is in check, false otherwise
	 */
	public boolean checkCheck(Coordinates king, PieceColor color, boolean aux) {
		boolean check = false;

		ArrayList<Piece> checking = new ArrayList<Piece>();
		
		System.out.println("Start of checkCheck");
		System.out.println(color + "King coordinates: " + king);
		
		//run through every element in the board
		for (int x = 0; x < ChessGame.SIZE; x++) {
			for (int y = 0; y < ChessGame.SIZE; y++) {
				//if there's a piece belonging to the other player at the index
				if(getColor(x, y) != null && getColor(x, y) != color){
					if(!aux){
						if(validFin(new Coordinates(x, y), new Coordinates(king), getPlayerRef(getColor(x, y)), false)){
							check = true;
							checking.add(gameBoard[x][y]);
						}
					}
					else{
						if(validFinAux(new Coordinates(x, y), new Coordinates(king), getColor(x, y), false)){
							check = true;
							checking.add(gameBoard[x][y]);
						}
					}
				}
				
			}
		}
		if (check){
			System.out.print("Being checked by : ");
			for (Piece piece: checking){
				System.out.print(piece.getType() + " from " + piece.getCoordinates() + ", ");
			}
			System.out.println();
		}
		
		System.out.println("end of checkCheck");
		
		return check;
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
	
	/**
	 * Checks whether a given player is in checkmate and adjusts the player's checkmate status accordingly.
	 * @param player The player to check.
	 */
	private void updateCheckMate(Player player) {
		updateCheck(player, false);
		
		if(player.isInCheck()){
			System.out.println("Checking if player can leave check");
			if(!checkAllMoves(player)){
				player.setLost(true);
				ChessGame.gameMsg(player.getColor() + " is in checkmate!", true);
			}
		}
	}
	
	/**
	 * Checks whether a given player is in check and adjusts the player's check status accordingly.
	 * @param player The player to check.
	 * TODO: explain what aux is
	 */
	private void updateCheck(Player player, boolean aux) {
		System.out.println("start of updateCheck for " + player.getType());
		if (checkCheck(player.getKingCoor(), player.getColor(), aux)) {
			player.setInCheck(true);
		}
		else {
			player.setInCheck(false);
			player.setLost(false);
		}
		
		System.out.println("");
		System.out.println("end of updateCheck");
	}
	
	/**
	 * Checks whether or not a given player is able to leave check.
	 * @param p The player to check.
	 * @return True if the player can leave check, false otherwise.
	 */
	private boolean checkAllMoves(Player player) {
		System.out.println("start of checkAllMoves");
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
		
		
		System.out.println("end of checkAllMoves");
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
		System.out.println("start of checkNextMoveCheck");
		System.out.println("from" + init + "to" + fin);
		boolean leaveCheck = true;
		ChessLogic nextMove = new ChessLogic(this);
		Player testPlayer = new Player(player, false); //creates fake player
		
		if(validFinAux(init, fin, testPlayer.getColor(), false)) {
			System.out.println(nextMove.getType(init));
			if (nextMove.getType(init) == PieceType.King){
				System.out.println("hallos");
				testPlayer.setKingCoor(fin);
			}
			nextMove.makeTestMove(init, fin);
		}
		
		nextMove.updateCheck(testPlayer, true);

		if (testPlayer.isInCheck()) {
			leaveCheck = false;
		}
		System.out.println("leaveCheck is : " + leaveCheck);
		System.out.println("end of checkNextMoveCheck");
		return leaveCheck;
	}
	
	/**
	 * Tests a particular move (in terms of a group of coordinates) without directly affecting the chessboard.
	 * @param init The initial set of coordinates.
	 * @param fin The final set of coordinates.
	 */
	public void makeTestMove(Coordinates init, Coordinates fin) {
		System.out.println("start of makeTestMove");
		Piece piece = gameBoard[init.getX()][init.getY()];
		piece.move(fin);
		gameBoard[init.getX()][init.getY()] = null;
		gameBoard[fin.getX()][fin.getY()] = piece;
		System.out.println("end of makeTestMove");
	}
	
	/**
	 * Checks whether or not a player is in stalemate.
	 * @param player The player to check.
	 */
	private void checkStalemate(Player player) {
		
		System.out.println("start of checkStalemate");
		Player testPlayer = new Player(player);
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		player.getKingCoor();
		PieceColor color = testPlayer.getColor();
		boolean stalemate = true;
		
		//Finds all pieces
		for (int x = 0; x < ChessGame.SIZE;x++){
			for(int y = 0; y < ChessGame.SIZE; y++){
				if (gameBoard[x][y] != null) {
					if (gameBoard[x][y].getColor() == color) {
						pieces.add(gameBoard[x][y]);
					}
				}
			}
		}
		
		Coordinates init = new Coordinates();
		for (Piece piece : pieces){
			if (!pieces.isEmpty()){
				init = piece.getCoordinates();
				
				//possible moves of that piece
				for (Coordinates coor : gameBoard[init.getX()][init.getY()].getPossibleMoves()) {
					if(validFin(init, coor, getPlayerRef(color), false)) {
						stalemate = false;
					}
				}
			}
		}
		
		System.out.println("end of checkStalemate");
		this.stalemate = stalemate;
	}
}

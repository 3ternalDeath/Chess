package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import pieces.Empty;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceType;

/**
 * Handles all the internal logic of a chess game.
 * @author Group 36
 */
public class ChessLogic {
	private final String FILE_NAME = "standard1";
	Piece[][] gameBoard;
	private Player player1, player2;
	
	public ChessLogic() throws FileNotFoundException {
		gameBoard = new Piece[ChessGame.SIZE][ChessGame.SIZE];
		
		Scanner file = new Scanner(new File("src/engine/" + FILE_NAME + ".txt"));
		
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
							player2 = new Player(PieceColor.Black, PlayerType.Computer, false, coor);
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
	 * Checks whether or not a given group of coordinates creates a legal move for a given player.
	 * @param init The initial coordinates.
	 * @param fin The final coordinates.
	 * @param player The player in question.
	 * @return True if the move is valid, false otherwise.
	 */
	//DEPRECATED -- USE validInit() AND validFin() INSTEAD
	//maybe transform into validateSpecialMoves?
	public boolean validMove(Coordinates init, Coordinates fin, PieceColor color) {
		boolean valid = true;
		
		if (!basicValid(init, fin, color)) {
			valid = false;
		}
		if (!pawnValid(init, fin, color)) {
			valid = false;
		}
		int castling = castleValid(init, fin, color);
		if (castling == -1) {
			valid = false;
		}
		if (!collisionDetect(init, fin)) {
			System.out.println("There's a piece in the way.");
			valid = false;
		}
//		if (checkNextMoveCheck(init, fin, player)) {
//			return true;
//		}
		/*
		if (castling==1) {//MUST BE LAST IF STATEMENT!!!!!
			castleNow = true;
		}
		*/
		
		return valid;
	}
	
	public void movePiece(Coordinates init, Coordinates fin) {
		Piece piece = getPieceAt(init);
		piece.move(fin);
		
		if (getType(init) == PieceType.King) {
			if (player1.isMyTurn())
				player1.setKingCoor(fin);
			else if (player2.isMyTurn())
				player2.setKingCoor(fin);
		}
		
		setPieceAt(init, null);
		setPieceAt(fin, piece);
		
		nextTurn();
	}
	
	/**
	 * Rolls game progression over to the next turn.
	 */
	private void nextTurn() {
		player1.switchTurn();
		player2.switchTurn();

		if (player1.getType() == PlayerType.Computer && player1.isMyTurn() && !player1.isLost()) {
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			compMove(player1.getColor());
		}
		else if (player2.getType() == PlayerType.Computer && player2.isMyTurn() && !player2.isLost()) {
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			compMove(player2.getColor());
		}
	}
	
	public Piece getPieceAt(Coordinates location) {
		if (gameBoard[location.getX()][location.getY()] != null) {
			Piece newPiece = gameBoard[location.getX()][location.getY()];
			return Piece.createPiece(location, newPiece.getType(), newPiece.getColor());
		}
		else {
			return null;
		}
	}
	
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
		if (gameBoard[coor.getX()][coor.getY()] != null)
			return gameBoard[coor.getX()][coor.getY()].getType();
		else
			return null;
	}
	
	public PieceColor getColor(Coordinates coor) {
		if (gameBoard[coor.getX()][coor.getY()] != null)
			return gameBoard[coor.getX()][coor.getY()].getColor();
		else
			return null;
	}
	
	public boolean p1Lost() {
		return player1.isLost();
	}
	
	public boolean p2Lost() {
		return player2.isLost();
	}
	
	public Player getP1() {
		return new Player(player1);
	}
	
	public Player getP2() {
		return new Player(player2);
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
	 * Checks through entire board to see if an enemy piece 
	 * can take the King piece.
	 * @param king The coordinates of the King piece.
	 * @param color The color of friendly pieces; can be left null if unknown.
	 * @return True if piece at king location can be taken next move, false otherwise.
	 */
	/**
	 * @param king
	 * @param color
	 * @return
	 */
	private boolean checkCheck(Coordinates king, PieceColor color) {
		boolean check = false;
		if (color == null)
			color = getColor(king);

		//run through every element in the board
		for (int x = 0; x < ChessGame.SIZE; x++) {
			for (int y = 0; y < ChessGame.SIZE; y++) {
				//if there's a piece belonging to the other player at the index
				if (gameBoard[x][y] != null) {
					if (gameBoard[x][y].getColor() != color) {
						//if the piece could move to the king coordinates
						if (gameBoard[x][y].validMove(king)) {	
							if (pawnCheckValid(new Coordinates(x, y), king)) {
								if (collisionDetect(new Coordinates(x, y), king)) {	//only checks for collision if piece can move there
									ChessGame.debugMsg("checkCheck() returns true-- KingCoordinates: " + king + " ||colorPram: "
									+ color + " ||Piece info: " + gameBoard[x][y] + " "
									+ gameBoard[x][y].getCoordinates());
								
									check = true;
								}
							}
						}
					}
				}
			}
		}
		
		return check;
	}
	
	/**
	 * Checks all places around a given player's King piece to see whether an enemy piece
	 * could move to that location. Should only be run if King is already in check.
	 * @param player The player to check.
	 * @return true If all positions King piece can move to are in enemy sights, false otherwise.
	 */
	private boolean checkCheckMate(Player player) {
		boolean inCheck = true;
		Coordinates king = player.getKingCoor();
		PieceColor color = getColor(king);
		Coordinates check = new Coordinates(king.getX(), king.getY());
		
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				check.incrementX(x);
				check.incrementY(y);

				//in order for check mate to happen king should not be able to
				//move anywhere without dying
				if (Coordinates.inBound(check.getX(), check.getY()))
					if (!checkNextMoveCheck(king, check, player)) {
						inCheck = false;
					}

				//put check coordinates back to initial values
				check.incrementX(x * (-1));
				check.incrementY(y * (-1));
			}
		}
		
		return inCheck;
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
		Player testPlayer = new Player(player);

		if (nextMove.getType(init) == PieceType.King)
			testPlayer.setKingCoor(fin);

		nextMove.makeTestMove(init, fin);
		nextMove.updateCheck(testPlayer);

		if (testPlayer.isInCheck()) {
			leaveCheck = false;
		}
		
		return leaveCheck;
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
	 * Checks whether or not a given player is able to leave check.
	 * @param p The player to check.
	 * @return True if the player can leave check, false otherwise.
	 */
	private boolean checkAllMoves(Player p) {
		boolean canLeave = false;
		
		//check every element in the board
		for (int x = 0; x < ChessGame.SIZE; x++)
			for (int y = 0; y < ChessGame.SIZE; y++) {
				//if there's a piece belonging to the player at the index
				if (gameBoard[x][y] != null) {
					if (gameBoard[x][y].getColor() == p.getColor()) {
						Coordinates piece = new Coordinates(x,y);
						ArrayList<Coordinates> moves = gameBoard[x][y].getPossibleMoves();
						//run through all its moves for next move check
						for (Coordinates move : moves) {
							if (checkNextMoveCheck(piece, move, p)) {
								canLeave = true;
							}
						}
					}
				}
			}
		
		return canLeave;
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
					player.setLost(true);
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
	
	public boolean validInit(Coordinates init, PieceColor color) {
		boolean valid = true;
		
		if (gameBoard[init.getX()][init.getY()] == null) {
			System.out.println("Selecting empty space.");
			valid = false;
		}
		//initial coordinates point to other player's piece
		else if (getColor(init) != color) {
			System.out.println("Selecting opponent's piece. Your color is " + color + ".");
			valid = false;
		}
		
		return valid;
	}
	
	public boolean validFin(Coordinates init, Coordinates fin, PieceColor color) {
		boolean valid = true;
		
		//final coordinates point to the player's own piece
		if (gameBoard[fin.getX()][fin.getY()] != null) {
			if (getColor(fin) == getColor(init)) {
				System.out.println("Moving into your own piece.");
				valid = false;
			}
		}
		//coordinates cause an illegal move
		if (!gameBoard[init.getX()][init.getY()].validMove(fin)) {
			System.out.println("The piece can't move there.");
			valid = false;
		}
		
		if (getType(init) == PieceType.Pawn) {
			//attempting diagonal movement to an empty space
			if (gameBoard[fin.getX()][fin.getY()] == null && Math.abs(fin.getX() - init.getX()) == 1) {
				System.out.println("The pawn can only kill like that.");
				valid = false;
			}
			//attempting to kill another piece head-on
			else if (gameBoard[fin.getX()][fin.getY()] != null && (fin.getX() - init.getX()) == 0) {
				System.out.println("The pawn can't kill like that.");
				valid = false;
			}
		}
		
		if(!collisionDetect(init, fin)) {
			System.out.println("There's something in the way.");
			System.out.println("Piece: " + gameBoard[init.getX()][init.getY()].getType());
			System.out.println("From: " + init + " to " + fin);
			
			valid = false;
		}
		
		return valid;
	}
	
	/**
	 * Movement for the computer player
	 * @param comp The player that is moving
	 * @param color The color of the moving piece.
	 * @return An array containing the initial and final coordinates.
	 */
	public void compMove(PieceColor color) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		Coordinates init = new Coordinates();
		Coordinates fin = new Coordinates();
		
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
		
		//Find a move in which it can kill a piece.
		//Piece that's farthest right then farthest to the top that can kill a piece.
		for (Piece piece : pieces){
			for (Coordinates coor : piece.getPossibleMoves()){
				if(gameBoard[coor.getX()][coor.getY()] != null){
					if(gameBoard[coor.getX()][coor.getY()].getColor() != color){
						if(validFin(piece.getCoordinates(), coor, color)) {
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
					if(validFin(init, coor, color)) {
						fin = new Coordinates(coor);
						goodMove = true;
					}
				}
				
				//removes piece from movable pieces;
				pieces.remove(piece);
			}
			//No piece can move
			else{
				//TODO: STALEMATE
			}
		} while (!goodMove);
		
		//coordinates into array
		movement[0] = init;
		movement[1] = fin;
		
		return movement;
				
	}
	
	
	/**
	 * Runs the basic tests on a given move (in terms of a group of coordinates).
	 * @param init The initial set of coordinates.
	 * @param fin The final set of coordinates.
	 * @param color The color of the moving piece.
	 * @return True if every test proves valid, false otherwise.
	 */
	//DEPRECATED
	private boolean basicValid(Coordinates init, Coordinates fin, PieceColor color) {
		boolean valid = true;
		
		if (gameBoard[init.getX()][init.getY()] == null) {
			System.out.println("Selecting empty space.");
			valid = false;
		}
		//initial coordinates point to other player's piece
		else if (getColor(init) != color) {
			System.out.println("Selecting opponent's piece. Your color is " + color + ".");
			valid = false;
		}
		//final coordinates point to the player's own piece
		else if (gameBoard[fin.getX()][fin.getY()] != null) {
			if (getColor(fin) == getColor(init)) {
				System.out.println("Moving into your own piece.");
				valid = false;
			}
		}
		//coordinates cause an illegal move
		else if (!gameBoard[init.getX()][init.getY()].validMove(fin)) {
			System.out.println("The piece can't move there.");
			valid = false;
		}

		return valid;
	}

	/**
	 * Runs pawn-specific tests on a given move (in terms of coordinates).
	 * @param init The initial set of coordinates.
	 * @param fin The final set of coordinates.
	 * @param color The color of the moving piece.
	 * @return True if every test proves valid, false otherwise.
	 */
	//DEPRECATED
	private boolean pawnValid(Coordinates init, Coordinates fin, PieceColor color) {
		boolean valid = true;
		
		if (getType(init) == PieceType.Pawn) {
			//attempting diagonal movement to an empty space
			if (gameBoard[fin.getX()][fin.getY()] == null && Math.abs(fin.getX() - init.getX()) == 1) {
				System.out.println("The pawn can only kill like that.");
				valid = false;
			}
			//attempting to kill another piece head-on
			else if (gameBoard[fin.getX()][fin.getY()] != null && Math.abs(fin.getX() - init.getX()) == 0) {
				System.out.println("The pawn can't kill like that.");
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * Checks whether a pawn could move to a given location.
	 * @param init The initial set of coordinates.
	 * @param fin The final set of coordinates.
	 * @return True if a pawn could move to the location, false otherwise.
	 */
	private boolean pawnCheckValid(Coordinates init, Coordinates fin) {
		boolean valid = false;
		
		if (Math.abs(fin.getX() - init.getX()) == 1) {
			valid = true;
		}
		
		return valid;
	}

	/**
	 * Runs castling-specific tests on a given move (in terms of coordinates).
	 * @param init The initial set of coordinates.
	 * @param fin The final set of coordinates.
	 * @param color The color of the moving piece.
	 * @return True if every test proves valid, false otherwise.
	 */
	private int castleValid(Coordinates init, Coordinates fin, PieceColor color) {
		int castling = 0;
		
		if (gameBoard[init.getX()][init.getY()].getType() == PieceType.King) {
			if (Coordinates.inBound(fin.getX() + 1) && gameBoard[fin.getX() + 1][fin.getY()] != null) {
				//if the other piece is not a rook
				if (gameBoard[fin.getX() + 1][fin.getY()].getType() != PieceType.Rook && (fin.getX() - init.getX()) == 2) {
					System.out.println("Castling can only be done if there is a rook");
					castling = -1;
				}
				else if (gameBoard[fin.getX() + 1][fin.getY()].getType() == PieceType.Rook && (fin.getX() - init.getX()) == 2 && gameBoard[fin.getX() + 1][fin.getY()].isFirstMove()) {
					castling = 1;
				}
			}
		}
		return castling;
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
			
			/*if (xDifference == 0) {
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
					if		(xDifference < 0) xDifference++;
					else if (xDifference > 0) xDifference--;
				}
			}
			else {
				//loop while there is a difference in x and y
				while (xDifference != 0 || yDifference != 0) {
					
					//if there is a piece in the way
					if (gameBoard[init.getX() + xDifference][init.getY() + yDifference] != null) {
						pathOpen = false;
					}
					
					//move x difference towards 0
					if 		(xDifference < 0) xDifference++;
					else if (xDifference > 0) xDifference--;
					
					//move y difference towards 0
					if 		(yDifference < 0) yDifference++;
					else if (yDifference > 0) yDifference--;
				}
			}*/
		}
		
		
		return pathOpen;
	}
	
	/**
	 * Shifts the number towards zero
	 * @param num The number to shift
	 * @return the number after shifting.
	 */
	private int shift(int num){
		
		//moves number towards zero
		if 		(num < 0) num++;
		else if (num > 0) num--;
		
		return num;
	}
	
}

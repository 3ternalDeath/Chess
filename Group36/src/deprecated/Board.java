package deprecated;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

import engine.Coordinates;
import pieces.Bishop;
import pieces.Empty;
import pieces.King;
import pieces.Night;
import pieces.Pawn;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceType;
import pieces.Queen;
import pieces.Rook;

/**
 * Holds a chessboard-sized of array of pieces, and updates the
 * array's contents based on player input.
 * @author Group 36
 */
public class Board {
	private static final int SIZE = 8;
	private Piece[][] gameBoard;
	private Stack<int[][]> moves;
	private Stack<Piece> deadPiece;
	private int[] castle;
	private boolean whiteCheck, blackCheck, whiteMate, blackMate;
	private Coordinates whiteKing, blackKing;
	
	/**
	 * Constructor for the Board class.
	 * Initializes all instance variables to default values.
	 */
	public Board() {
		gameBoard = new Piece[SIZE][SIZE];
		moves = new Stack<int[][]>();
		deadPiece = new Stack<Piece>();
		castle = new int[2];
		castle[0] = -1;
		castle[1] = -1;
		whiteCheck = false;
		blackCheck = false;
		whiteMate = false;
		blackMate = false;
	}
	
	/**
	 * Copy constructor for Board class.
	 * Creates new Board object with same attributes as argument.
	 * @param board The Board object to copy.
	 */
	public Board(Board board) {
		this();
		
		for(int x = 0; x < SIZE; x++){
			for(int y = 0; y < SIZE; y++){
				gameBoard[x][y] = board.getPiece(new Coordinates(x, y));
			}
		}
		
		whiteKing = board.getWhiteKingLoco();
		blackKing = board.getBlackKingLoco();
	}
	
	/**
	 * Populates game board with all necessary pieces, 
	 * in the standard arrangement. Must be run in conjunction
	 * with constructor, or game board will be empty.
	 */
	public void populateBoard(){
		PieceColor color;
		//Black pieces at top of board
		color = PieceColor.Black;
		gameBoard[0][7] = 	new Rook   (new Coordinates(s0,7), color);
		gameBoard[1][7] = 	new Night  (new Coordinates(1,7), color);
		gameBoard[2][7] = 	new Bishop (new Coordinates(2,7), color);
		gameBoard[3][7] = 	new Queen  (new Coordinates(3,7), color);
		gameBoard[4][7] =	new King   (new Coordinates(4,7), color);
		blackKing = new Coordinates(4, 7);
		gameBoard[5][7] = 	new Bishop (new Coordinates(5,7), color);
		gameBoard[6][7] = 	new Night  (new Coordinates(6,7), color);
		gameBoard[7][7] = 	new Rook   (new Coordinates(7,7), color);
		gameBoard[0][6] = 	new Pawn   (new Coordinates(0,6), color);
		gameBoard[1][6] = 	new Pawn   (new Coordinates(1,6), color);
		gameBoard[2][6] = 	new Pawn   (new Coordinates(2,6), color);
		gameBoard[3][6] = 	new Pawn   (new Coordinates(3,6), color);
		gameBoard[4][6] = 	new Pawn   (new Coordinates(4,6), color);
		gameBoard[5][6] = 	new Pawn   (new Coordinates(5,6), color);
		gameBoard[6][6] = 	new Pawn   (new Coordinates(6,6), color);
		gameBoard[7][6] = 	new Pawn   (new Coordinates(7,6), color);
		
		//White pieces at bottom of board
		color = PieceColor.White;
		gameBoard[0][0] = 	new Rook   (new Coordinates(0,0), color);
		gameBoard[1][0] = 	new Night  (new Coordinates(1,0), color);
		gameBoard[2][0] = 	new Bishop (new Coordinates(2,0), color);
		gameBoard[3][0] = 	new Queen  (new Coordinates(3,0), color);
		gameBoard[4][0] = 	new King   (new Coordinates(4,0), color);
		whiteKing = new Coordinates(4, 0);
		gameBoard[5][0] = 	new Bishop (new Coordinates(5,0), color);
		gameBoard[6][0] = 	new Night  (new Coordinates(6,0), color);
		gameBoard[7][0] = 	new Rook   (new Coordinates(7,0), color);
		gameBoard[0][1] = 	new Pawn   (new Coordinates(0,1), color);
		gameBoard[1][1] = 	new Pawn   (new Coordinates(1,1), color);
		gameBoard[2][1] = 	new Pawn   (new Coordinates(2,1), color);
		gameBoard[3][1] = 	new Pawn   (new Coordinates(3,1), color);
		gameBoard[4][1] = 	new Pawn   (new Coordinates(4,1), color);
		gameBoard[5][1] = 	new Pawn   (new Coordinates(5,1), color);
		gameBoard[6][1] = 	new Pawn   (new Coordinates(6,1), color);
		gameBoard[7][1] = 	new Pawn   (new Coordinates(7,1), color);
		
		//Fill the rest of the board with blank pieces
		for (int y = 2; y < SIZE -2; y++) {
			for (int x = 0; x < SIZE; x++) {
				gameBoard[x][y] = new Empty(new Coordinates(x, y));
			}
		}
	}
	
	/**
	 * Prints a text-based representation of the entire game board.
	 */
	public void display() {
		//Labeling top horizontal axis
		System.out.println();
		System.out.print("   ");
		for (int y = 0; y < SIZE; y++) {
			//char x = (char) ((int) ('a') + y);
			//System.out.print("   " + x + "  ");
			System.out.print("   " + (y) + "  ");
		}
		System.out.println();

		//Top Boundary
		System.out.print("   ");
		for (int y = 0; y < SIZE; y++) {
			System.out.print(" _____");
		}
		System.out.println();
		
		for (int y = SIZE; y > 0; y--) {
			//blank Space
			System.out.print("   ");
			for (int x = 0; x < SIZE; x++) {
				System.out.print("|     ");
			}
			System.out.println("|");

			//Labeling left vertical axis
			System.out.print(y-1);
			System.out.print("  ");
			
			//Content of Array
			for (int x = 0; x < SIZE; x++) {
				System.out.print("| " + gameBoard[x][y-1].toString() + "  ");
			}
			System.out.print("|  ");
			
			//Labeling right vertical axis
			System.out.println(y-1);
			
			//Bottom of each boy
			System.out.print("   ");
			for (int x = 0; x < SIZE; x++) {
				System.out.print("|_____");
			}
			System.out.println("|");
		}
		System.out.println();
		
		//Labeling bottom horizontal axis
		System.out.print("   ");
		for (int y = 0; y < SIZE; y++) {
			//char x = (char) ((int) ('a') + y);
			//System.out.print("   " + x + "  ");
			System.out.print("   " + (y) + "  ");
		}
		System.out.println();
	}
	
	/**
	 * Updates the entire board based on user input.
	 * Takes the piece at initial coordinates, and moves it to final coordinates.
	 * Does not check for valid movement;
	 * user must only call update after movement has been error-checked.
	 * @param init The coordinates of the piece that is moving.
	 * @param fin The coordinates that the piece is moving to.
	 */
	public void update(Coordinates init, Coordinates fin, PieceColor color) {
		System.out.println("Selected Piece:" + gameBoard[init.getX()][init.getY()].getType());
		System.out.println("Piece Color: " + gameBoard[init.getX()][init.getY()].getColor());
		
		if(gameBoard[init.getX()][init.getY()].getType() == PieceType.King){
			if(gameBoard[init.getX()][init.getY()].getColor() == PieceColor.White){
				whiteKing = new Coordinates(fin.getX(), fin.getY());
			}
			else{
				blackKing = new Coordinates(fin.getX(), fin.getY());
			}
		}

		//move the specified piece to the specified location
		//add move to the moves Stack
		Piece piece = getPiece(init);
		piece.move(fin);
		
		//undo moves
		int[][] currentMove = {{init.getX(), init.getY()}, {fin.getX(), fin.getY()}};
		moves.add(currentMove);
		deadPiece.add(gameBoard[fin.getX()][fin.getY()]);
		
		gameBoard[init.getX()][init.getY()] = new Empty(init);
		gameBoard[fin.getX()][fin.getY()] = piece;
		
		updateCheckMate();
	}
	
	/**
	 * Checks whether a given combination of coordinates will create a legal move.
	 * If not, prints a message informing player of why this move is illegal.
	 * @param init The player's initial coordinates.
	 * @param fin The player's final coordinates.
	 * @param color The color of the current player.
	 * @return True if the coordinates creates a legal move, false otherwise.
	 */
	public boolean validMovement(Coordinates init, Coordinates fin, PieceColor color) {
		// Default return
		boolean valid = true;
		
		//if coordinates point to a blank space then disallow them
		if(gameBoard[init.getX()][init.getY()].getType() == null) {
			System.out.println("Selecting empty space.");
			valid = false;
		}
		//if coordinates point to the other player's pieces then disallow them
		else if(gameBoard[init.getX()][init.getY()].getColor() != color) {
			System.out.println("Selecting opponent's piece. Your color is " + color + ".");
			valid = false;
		}
		//if coordinates point to a piece the current player owns then disallow them
		else if(gameBoard[fin.getX()][fin.getY()].getColor() == gameBoard[init.getX()][init.getY()].getColor()) {
			System.out.println("Moving into your own piece.");
			valid = false;
		}
		//if coordinates would cause an illegal move for the piece in question then disallow them
		else if(!gameBoard[init.getX()][init.getY()].validMove(fin)) {
			System.out.println("The piece can't move there.");
			valid = false;
		
		}
		//if piece is pawn
		else if (gameBoard[init.getX()][init.getY()].getType() == PieceType.Pawn) {
			//diagonal movement for pawn, 
			if (gameBoard[fin.getX()][fin.getY()].getType() == null && Math.abs(fin.getX() - init.getX()) == 1) {
				System.out.println("The pawn can only kill like that.");
				valid = false;
			}
		}


		//king must be moved out of check
		else if(color == PieceColor.Black) {
			if(blackCheck && gameBoard[init.getX()][init.getY()].getType() != PieceType.King) {
				if(!checkNextMoveCheck(init, fin, color)) {
					System.out.println("You are in Check, please move King out of check");
					valid = false;
				}
			}
		}
		else if(color == PieceColor.White) {
			if(whiteCheck && gameBoard[init.getX()][init.getY()].getType() != PieceType.King) {
				if(!checkNextMoveCheck(init, fin, color)) {
					System.out.println("You are in Check, please move King out of check");
					valid = false;
				}
			}
		}
		//if piece is king
		else if (gameBoard[init.getX()][init.getY()].getType() == PieceType.King) {
			if(Coordinates.inBound(fin.getX() + 1)) {
				//checks for castling
				if(checkCheck(fin, gameBoard[init.getX()][init.getY()].getColor())) {
					System.out.println("Moving into check not posible");
					valid = false;
				}
				else if (gameBoard[fin.getX() + 1][fin.getY()].getType() != PieceType.Rook && (fin.getX() - init.getX()) == 2) {
					System.out.println("Castling can only be done if there is a rook");
					valid = false;
				}
				else if (gameBoard[fin.getX() + 1][fin.getY()].getType() == PieceType.Rook && (fin.getX() - init.getX()) == 2 && gameBoard[fin.getX() + 1][fin.getY()].isFirstMove()) {
					update(new Coordinates(fin.getX() + 1, fin.getY()), new Coordinates(fin.getX() - 1, fin.getY()), color);
					if(castle[0] != -1) {
						castle[1] = moves.size();
					}
					else {
						castle[0] = moves.size();
					}
				}
				
				else if(!checkNextMoveCheck(init, fin, color)) {
					System.out.println("You cannot move King into check");
					valid = false;
				}
			}
		}
		//if coordinates would cause a piece to skip over another piece then disallow them
		else if(!collisionDetect(init, fin)) {
			System.out.println("There's something in the way.");
			valid = false;
		}
		
		return valid;
	}
	
	private boolean collisionDetect(Coordinates startPos, Coordinates endPos) {
		boolean pathOpen = true;
		
		int xDifference = endPos.getX() - startPos.getX();
		int yDifference = endPos.getY() - startPos.getY();
		
		
		if (gameBoard[startPos.getX()][startPos.getY()].getType() == PieceType.Night) {
			pathOpen = true;
		}
		else if((Math.abs(xDifference) == 1 || Math.abs(yDifference) == 1) && gameBoard[startPos.getX()][startPos.getY()].getType() != PieceType.Pawn){
			
		}
		else{ 
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
					if (gameBoard[startPos.getX()][startPos.getY() + yDifference].getType() != null) {
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
					if (gameBoard[startPos.getX() + xDifference][startPos.getY()].getType() != null) {
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
					if (gameBoard[startPos.getX() + xDifference][startPos.getY() + yDifference].getType() != null) {
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
	
	/**
	 * Undoes the last move the player made.
	 */
	public void undoMove() {
		if(moves.size() >0) {//moves not empty
			int[][] move = moves.pop();
			Piece piece = gameBoard[move[1][0]][move[1][1]];
			
			//the undoing of the move
			piece.move(new Coordinates(move[0][0], move[0][1]));
			gameBoard[move[1][0]][move[1][1]] = deadPiece.pop();
			gameBoard[move[0][0]][move[0][1]] = piece;
			
			if(castle[0] == moves.size()) {
				castle[0] = -1;
				undoMove();
			}
			else if(castle[1] == moves.size()) {
				castle[1] = -1;
				undoMove();
			}
		}
	}
	
	/**
	 * Checks through entire board to see if an enemy piece 
	 * can take the King piece.
	 * @param king The coordinates of the King piece.
	 * @param color The color of friendly pieces; can be left null if unknown.
	 * @return true if piece at king location can be taken next move, false otherwise
	 */
	public boolean checkCheck(Coordinates king, PieceColor color) {
		if(color == null)
			color = gameBoard[king.getX()][king.getY()].getColor();

		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++) {
				
				//returns true when enemy piece can take the king
				if(gameBoard[x][y].getColor() != color) {
					if(gameBoard[x][y].validMove(king)) {						//Separate if statements to save processing power
						if(collisionDetect(new Coordinates(x, y), king)) {	//only checks for collision if piece can move there
							Game.debugMsg("checkCheck() returns true-- KingCoordinates: " + king + " ||colorPram: " + color + " ||Piece info: " + gameBoard[x][y] + " " + gameBoard[x][y].getCoordinates());
							return true;
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
	public boolean checkCheckMate(Coordinates king) {
		PieceColor color = gameBoard[king.getX()][king.getY()].getColor();
		Coordinates check = new Coordinates(king.getX(), king.getY());
		
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				check.incrementX(x); //changes coords of check based on loop variables
				check.incrementY(y);
				
				//in order for check mate to happen king should not be able to move anywhere without dying 
				if( x!= 0 || y != 0) {
					if(Coordinates.inBound(check.getX(), check.getY())) {
						if(gameBoard[check.getX()][check.getY()].getColor() != color) {
							if(!checkCheck(check, color)) {
								if(checkNextMoveCheck(king, check, color))
									return false;
							}
						}
					}
				}
				
				check.incrementX(x*(-1));//puts check coords back the way they were
				check.incrementY(y*(-1));
			}
		}
		
		return true;
	}
	
	private void updateCheckMate() {
		if(checkCheck(blackKing, null)) {
			blackCheck = true;

			if(checkCheckMate(blackKing)){
				blackMate = true;
			}
			else
				blackMate = false;
		}
		else{
			blackCheck = false;
			blackMate = false;
		}
			
		if(checkCheck(whiteKing, null)){
			whiteCheck = true;
				
			if(checkCheckMate(whiteKing))
				whiteMate = true;
			else
				whiteMate = false;
		}
		else{
			whiteCheck = false;
			whiteMate = false;
		}
	}
	
	private boolean checkNextMoveCheck(Coordinates init, Coordinates fin, PieceColor color){
		Board board = new Board(this);
		
		if(color == PieceColor.Black){
			board.blackKing = fin;
			board.update(init, fin, color);
			if(board.getBlackCheck()){
				return false;
			}
		}
		else if(color == PieceColor.White){
			board.whiteKing = fin;
			board.update(init, fin, color);
			if(board.getWhiteCheck()){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns the current check state of the white King piece.
	 * @return True if the white King piece is currently in check, false otherwise.
	 */
	public boolean getWhiteCheck(){
		return whiteCheck;
	}
	
	/**
	 * Returns the current check state of the black King piece.
	 * @return True if the black King piece is currently in check, false otherwise.
	 */
	public boolean getBlackCheck(){
		return blackCheck;
	}
	
	/**
	 * Returns whether the white King piece is in checkmate.
	 * @return True if the white King piece is in checkmate, false otherwise.
	 */
	public boolean whiteLose(){
		return whiteMate;
	}
	
	/**
	 * Returns whether the black King piece is in checkmate.
	 * @return True if the black King piece is in checkmate, false otherwise.
	 */
	public boolean blackLose(){
		return blackMate;
	}
	
	/**
	 * Returns current location of the white King piece.
	 * @return The coordinates of the white King piece.
	 */
	public Coordinates getWhiteKingLoco(){
		return new Coordinates(whiteKing.getX(), whiteKing.getY());
	}
	
	/**
	 * Returns cuurent location of the black King piece.
	 * @return The coordinates of the black King piece.
	 */
	public Coordinates getBlackKingLoco(){
		return new Coordinates(blackKing.getX(), blackKing.getY());
	}
		
	/**
	 * Returns the piece at a given location on the game board,
	 * or null if no piece is at that location.
	 * @param x The x position of the piece.
	 * @param y The y position of the piece.
	 * @return The piece if there is one at the user's location, or null if there is not.
	 */
	public Piece getPiece(Coordinates coor){
		//the following code pertaining to Class and Constructor was acquired from: http://stackoverflow.com/questions/6094575/creating-an-instance-using-the-class-name-and-calling-constructor
		//and edited to meet the needs of this
		
		Class<? extends Piece> clazz = gameBoard[coor.getX()][coor.getY()].getClass();
		Constructor<? extends Piece> ctor;
		try {
			ctor = clazz.getConstructor(Coordinates.class, PieceColor.class);
			Piece object = ctor.newInstance(coor, gameBoard[coor.getX()][coor.getY()].getColor());
			return object;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			System.err.println("Something went wrong");
			e.printStackTrace();
			System.err.println("It is done going wrong");
		}
		
		return null;
	}
}

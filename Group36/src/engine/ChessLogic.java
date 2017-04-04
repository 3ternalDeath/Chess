package engine;

import java.io.File;
import java.io.FileNotFoundException;
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
public class ChessLogic {
	private final String FILE_NAME = "standard1";
	private Stack<Coordinates[]> moves;
	private Stack<Piece> deadPieces;
	Piece[][] gameBoard;
	private Player player1, player2;
	
	public ChessLogic() throws FileNotFoundException {
		gameBoard = new Piece[ChessGame.SIZE][ChessGame.SIZE];
		moves = new Stack<Coordinates[]>();
		deadPieces = new Stack<Piece>();
		
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
		piece.move(fin);
		
		setPieceAt(init, null);
		setPieceAt(fin, piece);
		
		nextTurn();
	}
	
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
	
	private void makeCastleMove(Coordinates init, Coordinates fin){
		if ((fin.getX() - init.getX()) == 2) {
			//moving right
			Coordinates rook = new Coordinates(fin.getX() + 1, fin.getY());
			Coordinates rookTo = new Coordinates(fin.getX() - 1, fin.getY());
			Piece piece = getPieceRef(rook);
			moves.add(new Coordinates[]{new Coordinates(init), new Coordinates(fin), rook, rookTo});
			
			setPieceAt(rook, null);
			setPieceAt(rookTo, piece);
		}
		else if ((fin.getX() - init.getX()) == -2) {
			//moving left
			Coordinates rook = new Coordinates(fin.getX() - 2, fin.getY());
			Coordinates rookTo = new Coordinates(fin.getX() + 1, fin.getY());
			Piece piece = getPieceRef(rook);
			moves.add(new Coordinates[]{new Coordinates(init), new Coordinates(fin), rook, rookTo});
			
			setPieceAt(rook, null);
			setPieceAt(rookTo, piece);
		}
	}

	public void undoMove() {
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
		
		if(move.length == 2)
			setPieceAt(init, deadPieces.pop());
		else
			setPieceAt(init, null);
		
		setPieceAt(fin, piece);
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
	
	private Piece getPieceRef(Coordinates location){
		return getPieceRef(location.getX(), location.getY());
	}
	
	private Piece getPieceRef(int x, int y){
		return gameBoard[x][y];
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
		return getType(coor.getX(), coor.getY());
	}
	
	private PieceType getType(int x, int y){
		if (gameBoard[x][y] != null)
			return gameBoard[x][y].getType();
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
		if (gameBoard[fin.getX()][fin.getY()] != null && getColor(fin) == getColor(init)) {
			System.out.println("Moving into your own piece.");
			valid = false;
		}

		// coordinates cause an illegal move
		else if (!gameBoard[init.getX()][init.getY()].validMove(fin)) {
			System.out.println("The piece can't move there.");
			valid = false;
		}
		
		else if(!collisionDetect(init, fin)) {
			System.out.println("There's something in the way.");
			System.out.println("Piece: " + gameBoard[init.getX()][init.getY()].getType());
			System.out.println("From: " + init + " to " + fin);
			
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
		
		if(getType(init) == PieceType.King){
			if(Math.abs(init.getX() - fin.getX()) > 1){
				if(!moveIsCastle(init, fin)){
					valid = false;
				}
			}
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

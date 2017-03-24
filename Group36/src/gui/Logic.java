package gui;

import java.util.Stack;
import non_gui.Coordinates;
import pieces.Empty;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceType;

public class Logic {

	Button[][] buttons;
	Piece[][] gameBoard;
	private Stack<int[][]> moves;
	private Stack<Piece> deadPiece;
	private int[] castle;
	private boolean castleNow;
	
	public Logic(){
		moves = new Stack<int[][]>();
		deadPiece = new Stack<Piece>();
		castle = new int[2];
		castle[0] = -1;
		castle[1] = -1;
		gameBoard = new Piece[Test.SIZE][Test.SIZE];
	}

	public Logic(Button[][] grid) {
		this();
		buttons = grid;

		for (int i = 0; i < Test.SIZE; i++)
			for (int j = 0; j < Test.SIZE; j++)
				gameBoard[i][j] = buttons[i][j].getPieceRef();

	}
	
	public void updateButton(){
		for(int x = 0; x < Test.SIZE; x++){
			for(int y = 0; y < Test.SIZE; y++){
				buttons[x][y].setPieceRef(gameBoard[x][y]);
			}
		}
	}
	
	public void updateBoard(Coordinates init, Coordinates fin) {
		if (castleNow) {
			makeMove(new Coordinates(fin.getX() + 1, fin.getY()), new Coordinates(fin.getX() - 1, fin.getY()));
			if (castle[0] != -1) {
				castle[1] = moves.size();
			} else {
				castle[0] = moves.size();
			}
			castleNow = false;
		}
		makeMove(init, fin);
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
		
		/*if(gameBoard[init.getX()][init.getY()].getType() == PieceType.King){
			if(gameBoard[init.getX()][init.getY()].getColor() == PieceColor.White){
				whiteKing = new Coordinates(fin.getX(), fin.getY());
			}
			else{
				blackKing = new Coordinates(fin.getX(), fin.getY());
			}
		}
*/
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
		
		//updateCheckMate();
	}
	
	public boolean validMove(Coordinates init, Coordinates fin, PieceColor color){
		if(!basicValid(init, fin, color)){
			return false;
		}
		if(!pawnValid(init, fin, color)){
			return false;
		}
		int temp = castleValid(init, fin, color);
		if(temp == -1){
			return false;
		}
		if(!collisionDetect(init, fin)) {
			return false;
		}
		
		//TODO: check for check and next turn
		if(temp==1){//MUST BE LAST IF STATEMENT!!!!!
			castleNow = true;
		}
		return true;
	}

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

	private boolean pawnValid(Coordinates init, Coordinates fin, PieceColor color) {
		if (gameBoard[init.getX()][init.getY()].getType() == PieceType.Pawn) {
			// diagonal movement for pawn,
			if (gameBoard[fin.getX()][fin.getY()] == null && Math.abs(fin.getX() - init.getX()) == 1) {
				System.out.println("The pawn can only kill like that.");
				return false;
			}
			else if(gameBoard[fin.getX()][fin.getY()] != null && Math.abs(fin.getX() - init.getX()) == 0){
				System.out.println("The pawn can't kill like that.");
				return false;
			}
		}
		return true;
	}

	private int castleValid(Coordinates init, Coordinates fin, PieceColor color){
		if (gameBoard[init.getX()][init.getY()].getType() == PieceType.King) {
			if(Coordinates.inBound(fin.getX() + 1)) {
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
	
	private boolean collisionDetect(Coordinates init, Coordinates fin) {
		boolean pathOpen = true;
		
		int xDifference = fin.getX() - init.getX();
		int yDifference = fin.getY() - init.getY();
		
		
		if (gameBoard[init.getX()][init.getY()].getType() == PieceType.Night) {
			pathOpen = true;
		}
		else if((Math.abs(xDifference) == 1 || Math.abs(yDifference) == 1) && gameBoard[init.getX()][init.getY()].getType() != PieceType.Pawn){
			
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

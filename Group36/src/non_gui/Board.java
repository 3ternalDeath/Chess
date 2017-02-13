package non_gui;

import java.util.Stack;

public class Board {
	
	private static final int SIZE = 8;
	private int scoreChange;
	private Piece[][] gameBoard;
	private Stack<int[][]> moves;
	private Stack<Piece> deadPiece;
	
	//constructor for the Board object
	public Board(){
		gameBoard = new Piece[SIZE][SIZE];
		scoreChange = 0;
		moves = new Stack<int[][]>();
		deadPiece = new Stack<Piece>();
	};
	
	/*
	 * populates game board with all necessary pieces, in the standard arrangement
	 */
	public void populateBoard(){
		//Black pieces at top of board
		gameBoard[0][7] = 	new Rook( 	PieceColour.Black, 0, 7);
		gameBoard[1][7] = 	new Knight( PieceColour.Black, 1, 7);
		gameBoard[2][7] = 	new Bishop(	PieceColour.Black, 2, 7);
		gameBoard[3][7] = 	new Queen( 	PieceColour.Black, 3, 7);
		gameBoard[4][7] =	new King( 	PieceColour.Black, 4, 7);
		gameBoard[5][7] = 	new Bishop(	PieceColour.Black, 5, 7);
		gameBoard[6][7] = 	new Knight( PieceColour.Black, 6, 7);
		gameBoard[7][7] = 	new Rook( 	PieceColour.Black, 7, 7);
		gameBoard[0][6] = 	new Pawn( 	PieceColour.Black, 0, 6);
		gameBoard[1][6] = 	new Pawn( 	PieceColour.Black, 1, 6);
		gameBoard[2][6] = 	new Pawn( 	PieceColour.Black, 2, 6);
		gameBoard[3][6] = 	new Pawn( 	PieceColour.Black, 3, 6);
		gameBoard[4][6] = 	new Pawn( 	PieceColour.Black, 4, 6);
		gameBoard[5][6] = 	new Pawn( 	PieceColour.Black, 5, 6);
		gameBoard[6][6] = 	new Pawn( 	PieceColour.Black, 6, 6);
		gameBoard[7][6] = 	new Pawn( 	PieceColour.Black, 7, 6);
		
		//White pieces at bottom of board
		gameBoard[0][0] = 	new Rook( 	PieceColour.White, 0, 0);
		gameBoard[1][0] = 	new Knight( PieceColour.White, 1, 0);
		gameBoard[2][0] = 	new Bishop( PieceColour.White, 2, 0);
		gameBoard[3][0] = 	new Queen( 	PieceColour.White, 3, 0);
		gameBoard[4][0] = 	new King( 	PieceColour.White, 4, 0);
		gameBoard[5][0] = 	new Bishop( PieceColour.White, 5, 0);
		gameBoard[6][0] = 	new Knight( PieceColour.White, 6, 0);
		gameBoard[7][0] = 	new Rook( 	PieceColour.White, 7, 0);
		gameBoard[0][1] = 	new Pawn( 	PieceColour.White, 0, 1);
		gameBoard[1][1] = 	new Pawn( 	PieceColour.White, 1, 1);
		gameBoard[2][1] = 	new Pawn( 	PieceColour.White, 2, 1);
		gameBoard[3][1] = 	new Pawn( 	PieceColour.White, 3, 1);
		gameBoard[4][1] = 	new Pawn( 	PieceColour.White, 4, 1);
		gameBoard[5][1] = 	new Pawn( 	PieceColour.White, 5, 1);
		gameBoard[6][1] = 	new Pawn( 	PieceColour.White, 6, 1);
		gameBoard[7][1] = 	new Pawn( 	PieceColour.White, 7, 1);
		
		//Fill the rest of the board with blank pieces
		for (int y = SIZE - 3; y > 1; y--) {
			for (int x = 0; x < SIZE; x++) {
				gameBoard[x][y] = new Empty(x, y);
			}
		}
	}
	
	public void display() {
		//Labeling top horizontal axis
		System.out.println();
		System.out.print("   ");
		for (int y = 0; y < SIZE; y++) {
			char x = (char) ((int) ('a') + y);
			System.out.print("   " + x + "  ");
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
			System.out.print(y);
			System.out.print("  ");
			
			//Content of Array
			for (int x = 0; x < SIZE; x++) {
				System.out.print("| " + gameBoard[x][y-1].toString() + "  ");
			}
			System.out.print("|  ");
			
			//Labeling right vertical axis
			System.out.println(y);
			
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
			char x = (char) ((int) ('a') + y);
			System.out.print("   " + x + "  ");
		}
		System.out.println();
	}
	
	public int pushScoreChange() {
		int tempScoreChange = scoreChange;
		scoreChange = 0;
		return tempScoreChange;
	}
	
	public void update(Coordinates init, Coordinates fin, Player currentPlayer) {
		System.out.println("Selected Piece:" + gameBoard[init.getX()][init.getY()].getType());
		System.out.println("Piece Colour: " + gameBoard[init.getX()][init.getY()].getColour());

		//move the specified piece to the specified location
		//add move to the moves Stack
		Piece piece = gameBoard[init.getX()][init.getY()];
		piece.move(fin);
		int[][] curentMove = {{init.getX(), init.getY()}, {fin.getX(), fin.getY()}};
		moves.add(curentMove);
		deadPiece.add(gameBoard[fin.getX()][fin.getY()]);
		gameBoard[init.getX()][init.getY()] = new Empty(init.getX(),init.getY());
		gameBoard[fin.getX()][fin.getY()] = piece;
		
		//collision detection needs to adjust scoreChange
	}
	
	public boolean validMovement(Coordinates init, Coordinates fin, Player currentPlayer) {
		// Default return
		boolean valid = true;
		
		//if coordinates point to a blank space then disallow them
		if(gameBoard[init.getX()][init.getY()].getType() == null){
			System.out.println("Selecting empty space.");
			valid = false;
		}
		//if coordinates point to the other player's pieces then disallow them
		else if(gameBoard[init.getX()][init.getY()].getColour() != currentPlayer.getColour()) {
			System.out.println("Selecting opponent's piece. Your color is " + currentPlayer.getColour() + ".");
			valid = false;
		}
		//if coordinates point to a piece the current player owns then disallow them
		else if(gameBoard[fin.getX()][fin.getY()].getColour() == gameBoard[init.getX()][init.getY()].getColour()){
			System.out.println("Moving into your own piece.");
			valid = false;
		}
		//if coordinates would cause an illegal move for the piece in question then disallow them
		else if(!gameBoard[init.getX()][init.getY()].validMove(fin)){
			System.out.println("The piece can't move there.");
			valid = false;
		
		}
		else if (gameBoard[init.getX()][init.getY()].getType() == PieceType.Pawn) {
			if (gameBoard[fin.getX()][fin.getY()].getType() == null && Math.abs(fin.getX() - init.getX()) == 1) {
				System.out.println("The pawn can only kill like that.");
				valid = false;
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
		else if (xDifference == 0) {
			if (yDifference < 0) {
				yDifference++;
			}
			else if (yDifference > 0) {
				yDifference--;
			}
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
			if (xDifference < 0) {
				xDifference++;
			}
			else if (xDifference > 0) {
				xDifference--;
			}
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
		
		
		
		return pathOpen;
	}
	
	//un-does the latest move
	private void undoMove(){
		int[][] move = moves.pop();
		Piece piece = gameBoard[move[1][0]][move[1][1]];
		piece.move(new Coordinates(move[0][0], move[0][1]));
		gameBoard[move[1][0]][move[1][1]] = deadPiece.pop();
		gameBoard[move[0][0]][move[0][1]] = piece;
	}
			
}

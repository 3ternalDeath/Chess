package non_gui;

import java.util.Stack;


public class Board {
	
	private static final int SIZE = 8;
	private int scoreChange;
	private Piece[][] gameBoard;
	private Stack<int[][]> moves;
	private Stack<Piece> deadPiece;
	private int[] castle;
	
	//constructor for the Board object
	public Board(){
		gameBoard = new Piece[SIZE][SIZE];
		scoreChange = 0;
		moves = new Stack<int[][]>();
		deadPiece = new Stack<Piece>();
		castle = new int[2];
		castle[0] = -1;
		castle[1] = -1;
	};
	
	/**
	 * populates game board with all necessary pieces, in the standard arrangement
	 */
	public void populateBoard(){
		//Black pieces at top of board
		gameBoard[0][7] = 	new Rook( 	PieceColor.Black, 0, 7);
		gameBoard[1][7] = 	new Knight( PieceColor.Black, 1, 7);
		gameBoard[2][7] = 	new Bishop(	PieceColor.Black, 2, 7);
		gameBoard[3][7] = 	new Queen( 	PieceColor.Black, 3, 7);
		gameBoard[4][7] =	new King( 	PieceColor.Black, 4, 7);
		gameBoard[5][7] = 	new Bishop(	PieceColor.Black, 5, 7);
		gameBoard[6][7] = 	new Knight( PieceColor.Black, 6, 7);
		gameBoard[7][7] = 	new Rook( 	PieceColor.Black, 7, 7);
		gameBoard[0][6] = 	new Pawn( 	PieceColor.Black, 0, 6);
		gameBoard[1][6] = 	new Pawn( 	PieceColor.Black, 1, 6);
		gameBoard[2][6] = 	new Pawn( 	PieceColor.Black, 2, 6);
		gameBoard[3][6] = 	new Pawn( 	PieceColor.Black, 3, 6);
		gameBoard[4][6] = 	new Pawn( 	PieceColor.Black, 4, 6);
		gameBoard[5][6] = 	new Pawn( 	PieceColor.Black, 5, 6);
		gameBoard[6][6] = 	new Pawn( 	PieceColor.Black, 6, 6);
		gameBoard[7][6] = 	new Pawn( 	PieceColor.Black, 7, 6);
		
		//White pieces at bottom of board
		gameBoard[0][0] = 	new Rook( 	PieceColor.White, 0, 0);
		gameBoard[1][0] = 	new Knight( PieceColor.White, 1, 0);
		gameBoard[2][0] = 	new Bishop( PieceColor.White, 2, 0);
		gameBoard[3][0] = 	new Queen( 	PieceColor.White, 3, 0);
		gameBoard[4][0] = 	new King( 	PieceColor.White, 4, 0);
		gameBoard[5][0] = 	new Bishop( PieceColor.White, 5, 0);
		gameBoard[6][0] = 	new Knight( PieceColor.White, 6, 0);
		gameBoard[7][0] = 	new Rook( 	PieceColor.White, 7, 0);
		gameBoard[0][1] = 	new Pawn( 	PieceColor.White, 0, 1);
		gameBoard[1][1] = 	new Pawn( 	PieceColor.White, 1, 1);
		gameBoard[2][1] = 	new Pawn( 	PieceColor.White, 2, 1);
		gameBoard[3][1] = 	new Pawn( 	PieceColor.White, 3, 1);
		gameBoard[4][1] = 	new Pawn( 	PieceColor.White, 4, 1);
		gameBoard[5][1] = 	new Pawn( 	PieceColor.White, 5, 1);
		gameBoard[6][1] = 	new Pawn( 	PieceColor.White, 6, 1);
		gameBoard[7][1] = 	new Pawn( 	PieceColor.White, 7, 1);
		
		//Fill the rest of the board with blank pieces
		for (int y = SIZE - 3; y > 1; y--) {
			for (int x = 0; x < SIZE; x++) {
				gameBoard[x][y] = new Empty(x, y);
			}
		}
	}
	
	/**
	 * prints out the current state of the board on the console
	 */
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
	
	
	
	/**
	 * moves piece from init to fin
	 * does not check for valid movement
	 * only call update after movement has been error-checked
	 * @param init the coordinates of the piece that is moving
	 * @param fin  the coordinates of the place piece moving to
	 */
	public void update(Coordinates init, Coordinates fin) {
		System.out.println("Selected Piece:" + gameBoard[init.getX()][init.getY()].getType());
		System.out.println("Piece Color: " + gameBoard[init.getX()][init.getY()].getColor());

		//move the specified piece to the specified location
		//add move to the moves Stack
		Piece piece = gameBoard[init.getX()][init.getY()];
		piece.move(fin);
		int[][] curentMove = {{init.getX(), init.getY()}, {fin.getX(), fin.getY()}};
		moves.add(curentMove);
		deadPiece.add(gameBoard[fin.getX()][fin.getY()]);
		gameBoard[init.getX()][init.getY()] = new Empty(init.getX(), init.getY());
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
		else if(gameBoard[init.getX()][init.getY()].getColor() != currentPlayer.getColor()) {
			System.out.println("Selecting opponent's piece. Your color is " + currentPlayer.getColor() + ".");
			valid = false;
		}
		//if coordinates point to a piece the current player owns then disallow them
		else if(gameBoard[fin.getX()][fin.getY()].getColor() == gameBoard[init.getX()][init.getY()].getColor()){
			System.out.println("Moving into your own piece.");
			valid = false;
		}
		//if coordinates would cause an illegal move for the piece in question then disallow them
		else if(!gameBoard[init.getX()][init.getY()].validMove(fin)){
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
		//if piece is king
		else if (gameBoard[init.getX()][init.getY()].getType() == PieceType.King) {
			//checks for castling
			if(Coordinates.inBound(fin.getX() + 1)){
				if (gameBoard[fin.getX() + 1][fin.getY()].getType() != PieceType.Rook && (fin.getX() - init.getX()) == 2) {
					System.out.println("Castling can only be done if there is a rook");
					valid = false;
				}
				else if (gameBoard[fin.getX() + 1][fin.getY()].getType() == PieceType.Rook && (fin.getX() - init.getX()) == 2 && gameBoard[fin.getX() + 1][fin.getY()].isFirstMove()){
					update(new Coordinates(fin.getX() + 1, fin.getY()), new Coordinates(fin.getX() - 1, fin.getY()));
					if(castle[0] != -1){
						castle[1] = moves.size();
					}
					else{
						castle[0] = moves.size();
					}
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
	 * undoes the latest move
	 */
	public void undoMove(){
		if(moves.size() >0){//moves not empty
			int[][] move = moves.pop();
			Piece piece = gameBoard[move[1][0]][move[1][1]];
			
			//the undoing of the move
			piece.move(new Coordinates(move[0][0], move[0][1]));
			gameBoard[move[1][0]][move[1][1]] = deadPiece.pop();
			gameBoard[move[0][0]][move[0][1]] = piece;
			
			if(castle[0] == moves.size()){
				castle[0] = -1;
				undoMove();
			}
			else if(castle[1] == moves.size()){
				castle[1] = -1;
				undoMove();
			}
		}
	}
	
	/**
	 * searches through the board to see if an enemy piece can take the piece at the king location
	 * @param king is the location being checked for safety
	 * @param color if the color of friendly pieces, null if unknown
	 * @return true if piece at king location can be taken next move, false otherwise
	 */
	public boolean checkCheck(Coordinates king, PieceColor color){
		if(color == null)
			color = gameBoard[king.getX()][king.getY()].getColor();

		for(int x = 0; x < SIZE; x++){
			for(int y = 0; y < SIZE; y++){
				
				//returns true when enemy piece can take the king
				if(gameBoard[x][y].getColor() != color){
					if(gameBoard[x][y].validMove(king))						//Separate if statements to save processing power
						if(collisionDetect(new Coordinates(x, y), king))	//only checks for collision if piece can move there
							return true;
				}	
			}
		}
		
		return false;
	}
	
	/**
	 * checks all places AROUND the king, the king it self still must be checked with checkCheck()
	 * @param king the position of the king
	 * @return true if ALL positions king can move to are in enemy sights, false otherwise
	 */
	public boolean checkCheckMate(Coordinates king){
		PieceColor color = gameBoard[king.getX()][king.getY()].getColor();
		
		for(int x = -1; x <= 1; x++){
			for(int y = -1; y <= 1; y++){
				
				king.incrementX(x);//changes coords of king based on loop variables
				king.incrementY(y);
				
				//in order for check mate to happen king should not be able to move anywhere without dying 
				if(Coordinates.inBound(king.getX(), king.getY())){
					if(!checkCheck(king, color)){
						return false;
					}
				}
				
				king.incrementX(x*(-1));//puts king coords back the way they were
				king.incrementY(y*(-1));
			}
		}
		
		return true;
	}
			
}

package non_gui;

import java.util.Scanner;

public class Board {
	
	private static final int SIZE = 8;
	
	private Coordinates init;
	private Coordinates fin;
	private int scoreChange;
	private Piece[][] gameBoard;
	
	//constructor for the Board object
	public Board(){
		gameBoard = new Piece[SIZE][SIZE];
		init = new Coordinates(0,0);
		fin = new Coordinates(0,0);
		scoreChange = 0;
	};
	
	/*
	 * populates game board with all necessary pieces, in the standard arrangement
	 */
	public void populateBoard(){
		//Black pieces at top of board
		gameBoard[0][7] = 	new Piece(PieceType.Rook, 	PieceColour.Black, 0, 7);
		gameBoard[1][7] = 	new Piece(PieceType.Night, 	PieceColour.Black, 1, 7);
		gameBoard[2][7] = 	new Piece(PieceType.Bishop,	PieceColour.Black, 2, 7);
		gameBoard[3][7] = 	new Piece(PieceType.Queen, 	PieceColour.Black, 3, 7);
		gameBoard[4][7] =	new Piece(PieceType.King, 	PieceColour.Black, 4, 7);
		gameBoard[5][7] = 	new Piece(PieceType.Bishop,	PieceColour.Black, 5, 7);
		gameBoard[6][7] = 	new Piece(PieceType.Night, 	PieceColour.Black, 6, 7);
		gameBoard[7][7] = 	new Piece(PieceType.Rook, 	PieceColour.Black, 7, 7);
		gameBoard[0][6] = 	new Piece(PieceType.Pawn, 	PieceColour.Black, 0, 6);
		gameBoard[1][6] = 	new Piece(PieceType.Pawn, 	PieceColour.Black, 1, 6);
		gameBoard[2][6] = 	new Piece(PieceType.Pawn, 	PieceColour.Black, 2, 6);
		gameBoard[3][6] = 	new Piece(PieceType.Pawn, 	PieceColour.Black, 3, 6);
		gameBoard[4][6] = 	new Piece(PieceType.Pawn, 	PieceColour.Black, 4, 6);
		gameBoard[5][6] = 	new Piece(PieceType.Pawn, 	PieceColour.Black, 5, 6);
		gameBoard[6][6] = 	new Piece(PieceType.Pawn, 	PieceColour.Black, 6, 6);
		gameBoard[7][6] = 	new Piece(PieceType.Pawn, 	PieceColour.Black, 7, 6);
		
		//White pieces at bottom of board
		gameBoard[0][0] = 	new Piece(PieceType.Rook, 	PieceColour.White, 0, 0);
		gameBoard[1][0] = 	new Piece(PieceType.Night, 	PieceColour.White, 1, 0);
		gameBoard[2][0] = 	new Piece(PieceType.Bishop, PieceColour.White, 2, 0);
		gameBoard[3][0] = 	new Piece(PieceType.Queen, 	PieceColour.White, 3, 0);
		gameBoard[4][0] = 	new Piece(PieceType.King, 	PieceColour.White, 4, 0);
		gameBoard[5][0] = 	new Piece(PieceType.Bishop, PieceColour.White, 5, 0);
		gameBoard[6][0] = 	new Piece(PieceType.Night, 	PieceColour.White, 6, 0);
		gameBoard[7][0] = 	new Piece(PieceType.Rook, 	PieceColour.White, 7, 0);
		gameBoard[0][1] = 	new Piece(PieceType.Pawn, 	PieceColour.White, 0, 1);
		gameBoard[1][1] = 	new Piece(PieceType.Pawn, 	PieceColour.White, 1, 1);
		gameBoard[2][1] = 	new Piece(PieceType.Pawn, 	PieceColour.White, 2, 1);
		gameBoard[3][1] = 	new Piece(PieceType.Pawn, 	PieceColour.White, 3, 1);
		gameBoard[4][1] = 	new Piece(PieceType.Pawn, 	PieceColour.White, 4, 1);
		gameBoard[5][1] = 	new Piece(PieceType.Pawn, 	PieceColour.White, 5, 1);
		gameBoard[6][1] = 	new Piece(PieceType.Pawn, 	PieceColour.White, 6, 1);
		gameBoard[7][1] = 	new Piece(PieceType.Pawn, 	PieceColour.White, 7, 1);
		
		//Fill the rest of the board with blank pieces
		for (int y = SIZE - 3; y > 1; y--) {
			for (int x = 0; x < SIZE; x++) {
				gameBoard[x][y] = new Piece(null, null, x, y);
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
	
	public void update(Player currentPlayer) {
		//get validated input from the user on what move should be made
		getInput("Initial", currentPlayer);
		System.out.println("Selected Piece:" + gameBoard[init.getX()][init.getY()].getType());
		System.out.println("Piece Colour: " + gameBoard[init.getX()][init.getY()].getColour());
		getInput("Final", currentPlayer);

		//move the specified piece to the specified location
		Piece piece = gameBoard[init.getX()][init.getY()];
		piece.move(fin);
		gameBoard[init.getX()][init.getY()] = new Piece(null,null,init.getX(),init.getY());
		gameBoard[fin.getX()][fin.getY()] = piece;
		
		//collision detection needs to adjust scoreChange
	}
	
	private void getInput(String type, Player currentPlayer) {
		Scanner input = new Scanner(System.in);

		char inputLetter = ' ';
		char inputNumber = ' ';

		Coordinates newCoor = new Coordinates(0,0);
		
		boolean valid = true;
		do {
			// UserInput
			System.out.print(type + " Coordinates (Ex. a1):");
			String coordinates = input.nextLine();

			// Coordinates must only have length 2
			if (coordinates.length() != 2) {
				System.out.println("Coordinates must be exactly two characters.");
				valid = false;
			} else {
				// Isolates letter and number components in whatever order the user gives it
				if(Character.isAlphabetic(coordinates.charAt(0)) && Character.isDigit(coordinates.charAt(1))){
					inputLetter = coordinates.toLowerCase().charAt(0);
					inputNumber = coordinates.charAt(1);
				}
				else{
					inputNumber = coordinates.toLowerCase().charAt(0);
					inputLetter = coordinates.charAt(1);
				}

				// Checks in bound
				valid = inBound(inputLetter, inputNumber);
				if (valid == true) {

					// Changes coordinate char to int(0 to 7) for array
					newCoor.setX(inputLetter - 'a');
					newCoor.setY(inputNumber - '1');

					// Checks valid movement
					valid = validMovement(type, newCoor, currentPlayer);
				}
			}

			// Invalid Input: Loop
			if (!valid) {
				System.out.println("Please reenter coordinates.");
			}

		} while (!valid);
		
		//Stores coordinates in an array.
		if (type.equals("Initial"))
			init = newCoor;
		else if (type.equals("Final"))
			fin = newCoor;
		else
			System.out.println("Something went wrong with the storing of coordinates...");
	}
	
	private boolean validMovement(String type, Coordinates newCoor, Player currentPlayer) {
		/*
		 * TODO are pieces in the way? need a check
		 */

		// Default return
		boolean valid = true;

		//checks concerning initial coordinates
		if(type.equals("Initial")){
			//if coordinates point to a blank space then disallow them
			if(gameBoard[newCoor.getX()][newCoor.getY()].getType() == null){
				System.out.println("Selecting empty space.");
				valid = false;
			}
			//if coordinates point to the other player's pieces then disallow them
			else if(gameBoard[newCoor.getX()][newCoor.getY()].getColour() != currentPlayer.getColour()) {
				System.out.println("Selecting opponent's piece. Your color is " + currentPlayer.getColour() + ".");
				valid = false;
			}
		}
		
		//checks concerning final coordinates
		else if(type.equals("Final")){
			//if coordinates point to a piece the current player owns then disallow them
			if(gameBoard[newCoor.getX()][newCoor.getY()].getColour() == gameBoard[init.getX()][init.getY()].getColour()){
				System.out.println("Moving into your own piece.");
				valid = false;
			}
			//if coordinates would cause an illegal move for the piece in question then disallow them
			else if(!gameBoard[init.getX()][init.getY()].validMove(newCoor)){
				valid = false;
			}
			//if coordinates would cause a piece to skip over another piece then disallow them
			else if(!collisionDetect(init, newCoor)) {
				System.out.println("There's something in the way.");
				valid = false;
			}
			else{
				System.out.println("idk what's happening :'(");
			}
		}
		return valid;
	}
	
	private boolean collisionDetect(Coordinates startPos, Coordinates endPos) {
		boolean pathOpen = true;
		
		int xDifference = endPos.getX() - startPos.getX();
		int yDifference = endPos.getY() - startPos.getY();
		
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
		
		if (gameBoard[startPos.getX()][startPos.getY()].getType() == PieceType.Night) {
			pathOpen = true;
		}
		
		return pathOpen;
	}
	
	private static boolean inBound(char letter, char number) {
		boolean valid = true;

		// Letter is between 'a' and 'h'
		if (letter < 'a' || letter > 'h') {
			System.out.println("Letter coordinate is out of range(a-h).");
			valid = false;
		}
		// Number is between '1' and '8'
		if (number < '1' || number > '8') {
			System.out.println("Number coordinate is out of range(1-8).");
			valid = false;
		}
		return valid;
	}
			
}

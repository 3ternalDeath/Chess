package non_gui;

import java.util.Scanner;

public class Main {

	public static final int SIZE = 8;

	static int NUMBER = 0;
	static int LETTER = 1;

	public static void display() {
		
		//Labeling top horizontal axis
		System.out.println();
		System.out.print("   ");
		for (int y = 0; y < SIZE; y++) {
			char x = (char) ((int) ('a') + y);
			System.out.print("   " + x + "  ");
		}
		System.out.println();

		//Top Boundarx
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
				System.out.print("|  " + gameboard[x][y-1].toString() + "  ");
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

	public static boolean inBound(char letter, char number) {

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

	public static int[] errorTrap(String type) {
		Scanner input = new Scanner(System.in);

		int validCoordinates[] = new int[2];

		char inputLetter = ' ';
		char inputNumber = ' ';
		int letterCoordinate = 0;
		int numberCoordinate = 0;

		boolean valid = true;
		do {
			// UserInput
			System.out.print(type + " Coordinates (Ex. a1):");
			String coordinates = input.next();

			// Coordinates must only have length 2
			if (coordinates.length() != 2) {
				System.out.println("Coordinates must be exactly two characters.");
				valid = false;
			} else {
				// Isolates letter and number components
				// in whatever order the user gives it
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
					numberCoordinate = inputNumber - '1';
					letterCoordinate = inputLetter - 'a';

					// Checks valid movement
					valid = validMovement(type, numberCoordinate, letterCoordinate);
				}
			}

			// Invalid Input: Loop
			if (valid == false) {
				System.out.println("Please reenter coordinates.");
			}

		} while (valid == false);

		
		//Stores coordinates in an array.
		validCoordinates[NUMBER] = numberCoordinate;
		validCoordinates[LETTER] = letterCoordinate;
		
		
		return validCoordinates;

	}

	public static boolean validMovement(String type, int numCoordinate, int letCoordinate) {

		/*
		 * TODO 1. interaction with other pieces.
		 * 2. moving your own pieces.
		 * 3. valid moves.
		 * 
		 * 
		 */

		// Default return
		boolean valid = true;

		// There's no piece
		if (gameboard[letCoordinate][numCoordinate] == blank) {
			if (type.equals("Initial")) {
				System.out.println("Coordinates do not match to a piece.");
				valid = false;
			} else {

			}
		}
		// There's a piece
		else {
			if (type.equals("Final")) {
				System.out.println("Coordinates are taken.");
				valid = false;
			} else {

			}
		}

		return valid;
	}
	
	
	
	static public void mkBoard(){
		Piece BKing = 		new Piece(PieceType.K, 	PieceColour.BLACK, 4, 	7);
		Piece BQueen = 		new Piece(PieceType.Q, 	PieceColour.BLACK, 3, 	7);
		Piece BBishop1 = 	new Piece(PieceType.B, 	PieceColour.BLACK, 2, 	7);
		Piece BBishop2 = 	new Piece(PieceType.B, 	PieceColour.BLACK, 5, 	7);
		Piece BNight1 = 	new Piece(PieceType.N, 	PieceColour.BLACK, 1, 	7);
		Piece BNight2 = 	new Piece(PieceType.N, 	PieceColour.BLACK, 6, 	7);
		Piece BRook1 = 		new Piece(PieceType.R, 	PieceColour.BLACK, 0, 	7);
		Piece BRook2 = 		new Piece(PieceType.R, 	PieceColour.BLACK, 7, 	7);
		Piece BPawn0 = 		new Piece(PieceType.N, 	PieceColour.BLACK, 0,	6);
		Piece BPawn1 = 		new Piece(PieceType.N, 	PieceColour.BLACK, 1,	6);
		Piece BPawn2 = 		new Piece(PieceType.N, 	PieceColour.BLACK, 2,	6);
		Piece BPawn3 = 		new Piece(PieceType.N, 	PieceColour.BLACK, 3,	6);
		Piece BPawn4 = 		new Piece(PieceType.N, 	PieceColour.BLACK, 4,	6);
		Piece BPawn5 = 		new Piece(PieceType.N, 	PieceColour.BLACK, 5,	6);
		Piece BPawn6 = 		new Piece(PieceType.N, 	PieceColour.BLACK, 6,	6);
		Piece BPawn7 = 		new Piece(PieceType.N, 	PieceColour.BLACK, 7,	6);
		
		Piece WKing = 		new Piece(PieceType.K, 	PieceColour.WHITE, 4, 	0);
		Piece WQueen = 		new Piece(PieceType.Q, 	PieceColour.WHITE, 3, 	0);
		Piece WBishop1 = 	new Piece(PieceType.B, 	PieceColour.WHITE, 2, 	0);
		Piece WBishop2 = 	new Piece(PieceType.B, 	PieceColour.WHITE, 5, 	0);
		Piece WNight1 = 	new Piece(PieceType.N, 	PieceColour.WHITE, 1, 	0);
		Piece WNight2 = 	new Piece(PieceType.N, 	PieceColour.WHITE, 6, 	0);
		Piece WRook1 = 		new Piece(PieceType.R, 	PieceColour.WHITE, 0, 	0);
		Piece WRook2 = 		new Piece(PieceType.R, 	PieceColour.WHITE, 7, 	0);
		Piece WPawn0 = 		new Piece(PieceType.N, 	PieceColour.WHITE, 0,	1);
		Piece WPawn1 = 		new Piece(PieceType.N, 	PieceColour.WHITE, 1,	1);
		Piece WPawn2 = 		new Piece(PieceType.N, 	PieceColour.WHITE, 2,	1);
		Piece WPawn3 = 		new Piece(PieceType.N, 	PieceColour.WHITE, 3,	1);
		Piece WPawn4 = 		new Piece(PieceType.N, 	PieceColour.WHITE, 4,	1);
		Piece WPawn5 = 		new Piece(PieceType.N, 	PieceColour.WHITE, 5,	1);
		Piece WPawn6 = 		new Piece(PieceType.N, 	PieceColour.WHITE, 6,	1);
		Piece WPawn7 = 		new Piece(PieceType.N, 	PieceColour.WHITE, 7,	1);
		
		gameboard = new Piece[][]{
			{ WRook1, 	WPawn0, blank, blank, blank, blank, BPawn0, BRook1 		},
			{ WNight1, 	WPawn1, blank, blank, blank, blank, BPawn1, BNight1 	},
			{ WBishop1, WPawn2, blank, blank, blank, blank, BPawn2, BBishop1 	},
			{ WQueen, 	WPawn3, blank, blank, blank, blank, BPawn3, BQueen 		},
			{ WKing, 	WPawn4, blank, blank, blank, blank, BPawn4, BKing 		},
			{ WBishop2, WPawn5, blank, blank, blank, blank, BPawn5, BBishop2 	},
			{ WNight2, 	WPawn6, blank, blank, blank, blank, BPawn6, BNight2 	},
			{ WRook2, 	WPawn7, blank, blank, blank, blank, BPawn7, BRook2 		}
		};
	}

	static char king = 'K';
	static char queen = 'Q';
	static char rook = 'R';
	static char bishop = 'B';
	static char knight = 'N';
	static char pawn = 'P';
	static Piece blank = new Piece(PieceType.NONE, PieceColour.NONE, 0, 0);

	static Piece gameboard[][];

	public static void main(String[] args) {
		
		mkBoard();
		
		boolean valid = true;
		while (valid == true){
			display();
			int[] init = errorTrap("Initial");
			int[] fin = errorTrap("Final");

			Piece piece = gameboard[init[LETTER]][init[NUMBER]];			
			gameboard[init[LETTER]][init[NUMBER]] = blank;
			gameboard[fin[LETTER]][fin[NUMBER]] = piece;
		
		}
	}
}

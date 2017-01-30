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
		Piece BKing = 		{PieceType.K, 	PieceColour.BLACK, 4, 	7};
		Piece BQueen = 		{PieceType.Q, 	PieceColour.BLACK, 3, 	7};
		Piece BBishop1 = 	{PieceType.B, 	PieceColour.BLACK, 2, 	7};
		Piece BBishop2 = 	{PieceType.B, 	PieceColour.BLACK, 5, 	7};
		Piece BNight1 = 		{PieceType.N, 	PieceColour.BLACK, 1, 	7};
		Piece BNight2 = 		{PieceType.N, 	PieceColour.BLACK, 6, 	7};
		Piece BRook1 = 		{PieceType.R, 	PieceColour.BLACK, 0, 	7};
		Piece BRook2 = 		{PieceType.R, 	PieceColour.BLACK, 7, 	7};
		Piece BPawn = 		{PieceType.N, 	PieceColour.BLACK, 		6};
		
		Piece WKing = 		{PieceType.K, 	PieceColour.WHITE, 4, 	0};
		Piece WQueen = 		{PieceType.Q, 	PieceColour.WHITE, 3, 	0};
		Piece WBishop1 = 	{PieceType.B, 	PieceColour.WHITE, 2, 	0};
		Piece WBishop2 = 	{PieceType.B, 	PieceColour.WHITE, 5, 	0};
		Piece WNight1 = 		{PieceType.N, 	PieceColour.WHITE, 1, 	0};
		Piece WNight2 = 		{PieceType.N, 	PieceColour.WHITE, 6, 	0};
		Piece WRook1 = 		{PieceType.R, 	PieceColour.WHITE, 0, 	0};
		Piece WRook2 = 		{PieceType.R, 	PieceColour.WHITE, 7, 	0};
		Piece WPawn = 		{PieceType.N, 	PieceColour.WHITE, 		1};
		
		
		gameboard = new Piece[][]{
				{ new Piece(WRook1[0], WRook1[1], WRook1[2], WRook1[3]), new Piece(WPawn[0], WPawn[1], 0, WPawn[2]), blank, blank, blank, blank, new Piece(BPawn[0], BPawn[1], 0, BPawn[2]), new Piece(BRook1[0], BRook1[1], BRook1[2], BRook1[3]) },
				{ new Piece(WNight1[0], WNight1[1], WNight1[2], WNight1[3]), new Piece(WPawn[0], WPawn[1], 1, WPawn[2]), blank, blank, blank, blank, new Piece(BPawn[0], BPawn[1], 1, BPawn[2]), new Piece(BNight1[0], BNight1[1], BNight1[2], BNight1[3]) },
				{ new Piece(WBishop1[0], WBishop1[1], WBishop1[2], WBishop1[3]), new Piece(WPawn[0], WPawn[1], 2, WPawn[2]), blank, blank, blank, blank, new Piece(BPawn[0], BPawn[1], 2, BPawn[2]), new Piece(BBishop1[0], BBishop1[1], BBishop1[2], BBishop1[3]) },
				{ new Piece(WQueen[0], WQueen[1], WQueen[2], WQueen[3]), new Piece(WPawn[0], WPawn[1], 3, WPawn[2]), blank, blank, blank, blank, new Piece(BPawn[0], BPawn[1], 3, BPawn[2]), new Piece(BQueen[0], BQueen[1], BQueen[2], BQueen[3]) },
				{ new Piece(WKing[0], WKing[1], WKing[2], WKing[3]), new Piece(WPawn[0], WPawn[1], 4, WPawn[2]), blank, blank, blank, blank, new Piece(BPawn[0], BPawn[1], 4, BPawn[2]), new Piece(BKing[0], BKing[1], BKing[2], BKing[3]) },
				{ new Piece(WBishop2[0], WBishop2[1], WBishop2[2], WBishop2[3]), new Piece(WPawn[0], WPawn[1], 5, WPawn[2]), blank, blank, blank, blank, new Piece(BPawn[0], BPawn[1], 5, BPawn[2]), new Piece(BBishop2[0], BBishop2[1], BBishop2[2], BBishop2[3]) },
				{ new Piece(WNight2[0], WNight2[1], WNight2[2], WNight2[3]), new Piece(WPawn[0], WPawn[1], 6, WPawn[2]), blank, blank, blank, blank, new Piece(BPawn[0], BPawn[1], 6, BPawn[2]), new Piece(BNight2[0], BNight2[1], BNight2[2], BNight2[3]) },
				{ new Piece(WRook2[0], WRook2[1], WRook2[2], WRook2[3]), new Piece(WPawn[0], WPawn[1], 7, WPawn[2]), blank, blank, blank, blank, new Piece(BPawn[0], BPawn[1], 7, BPawn[2]), new Piece(BRook2[0], BRook2[1], BRook2[2], BRook2[3]) }
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

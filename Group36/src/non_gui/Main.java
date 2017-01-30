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
			
			//Empty Space
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

	static char king = 'K';
	static char queen = 'Q';
	static char rook = 'R';
	static char bishop = 'B';
	static char knight = 'N';
	static char pawn = 'P';
	static char blank = ' ';

	static Object gameboard[][] = {
			{ 'R', 'P', ' ', ' ', ' ', ' ', 'P', 'R' },
			{ 'N', 'P', ' ', ' ', ' ', ' ', 'P', 'N' },
			{ 'B', 'P', ' ', ' ', ' ', ' ', 'P', 'B' },
			{ 'Q', 'P', ' ', ' ', ' ', ' ', 'P', 'Q' },
			{ 'K', 'P', ' ', ' ', ' ', ' ', 'P', 'K' },
			{ 'B', 'P', ' ', ' ', ' ', ' ', 'P', 'B' },
			{ 'N', 'P', ' ', ' ', ' ', ' ', 'P', 'N' },
			{ 'R', 'P', ' ', ' ', ' ', ' ', 'P', 'R' }
		};

	public static void main(String[] args) {

		boolean valid = true;
		while (valid == true){
			display();
			int[] init = errorTrap("Initial");
			int[] fin = errorTrap("Final");

			char piece = (char) gameboard[init[LETTER]][init[NUMBER]];			
			gameboard[init[LETTER]][init[NUMBER]] = blank;
			gameboard[fin[LETTER]][fin[NUMBER]] = piece;
		
		}
	}
}

package non_gui;

import java.util.Scanner;

public class Board {
	
	private static final int SIZE = 8;
	private static final int NUMBER = 0;
	private static final int LETTER = 1;
	private static final Piece blank = new Piece(PieceType.NONE, PieceColour.NONE, 0, 0);
	
	private int[] init;
	private int[] fin;
	private int scoreChange;
	private Piece[][] gameBoard;
	
	//constructor for the Board object
	public Board(){
		gameBoard = new Piece[SIZE][SIZE];
		init = new int[0];
		fin = new int[0];
		scoreChange = 0;
	};
	
	/*
	 * populates game board
	 * with all necessary pieces, in the standard arrangement
	 */
	public void populateBoard(){
		//create every existent piece and place it in the board
		gameBoard[4][7] =	new Piece(PieceType.K, 	PieceColour.BLACK, 4, 	7);
		gameBoard[3][7] = 	new Piece(PieceType.Q, 	PieceColour.BLACK, 3, 	7);
		gameBoard[2][7] = 	new Piece(PieceType.B, 	PieceColour.BLACK, 2, 	7);
		gameBoard[5][7] = 	new Piece(PieceType.B, 	PieceColour.BLACK, 5, 	7);
		gameBoard[1][7] = 	new Piece(PieceType.N, 	PieceColour.BLACK, 1, 	7);
		gameBoard[6][7] = 	new Piece(PieceType.N, 	PieceColour.BLACK, 6, 	7);
		gameBoard[0][7] = 	new Piece(PieceType.R, 	PieceColour.BLACK, 0, 	7);
		gameBoard[7][7] = 	new Piece(PieceType.R, 	PieceColour.BLACK, 7, 	7);
		gameBoard[0][6] = 	new Piece(PieceType.P, 	PieceColour.BLACK, 0,	6);
		gameBoard[1][6] = 	new Piece(PieceType.P, 	PieceColour.BLACK, 1,	6);
		gameBoard[2][6] = 	new Piece(PieceType.P, 	PieceColour.BLACK, 2,	6);
		gameBoard[3][6] = 	new Piece(PieceType.P, 	PieceColour.BLACK, 3,	6);
		gameBoard[4][6] = 	new Piece(PieceType.P, 	PieceColour.BLACK, 4,	6);
		gameBoard[5][6] = 	new Piece(PieceType.P, 	PieceColour.BLACK, 5,	6);
		gameBoard[6][6] = 	new Piece(PieceType.P, 	PieceColour.BLACK, 6,	6);
		gameBoard[7][6] = 	new Piece(PieceType.P, 	PieceColour.BLACK, 7,	6);
		
		gameBoard[4][0] = 	new Piece(PieceType.K, 	PieceColour.WHITE, 4, 	0);
		gameBoard[3][0] = 	new Piece(PieceType.Q, 	PieceColour.WHITE, 3, 	0);
		gameBoard[2][0] = 	new Piece(PieceType.B, 	PieceColour.WHITE, 2, 	0);
		gameBoard[5][0] = 	new Piece(PieceType.B, 	PieceColour.WHITE, 5, 	0);
		gameBoard[1][0] = 	new Piece(PieceType.N, 	PieceColour.WHITE, 1, 	0);
		gameBoard[6][0] = 	new Piece(PieceType.N, 	PieceColour.WHITE, 6, 	0);
		gameBoard[0][0] = 	new Piece(PieceType.R, 	PieceColour.WHITE, 0, 	0);
		gameBoard[7][0] = 	new Piece(PieceType.R, 	PieceColour.WHITE, 7, 	0);
		gameBoard[0][1] = 	new Piece(PieceType.P, 	PieceColour.WHITE, 0,	1);
		gameBoard[1][1] = 	new Piece(PieceType.P, 	PieceColour.WHITE, 1,	1);
		gameBoard[2][1] = 	new Piece(PieceType.P, 	PieceColour.WHITE, 2,	1);
		gameBoard[3][1] = 	new Piece(PieceType.P, 	PieceColour.WHITE, 3,	1);
		gameBoard[4][1] = 	new Piece(PieceType.P, 	PieceColour.WHITE, 4,	1);
		gameBoard[5][1] = 	new Piece(PieceType.P, 	PieceColour.WHITE, 5,	1);
		gameBoard[6][1] = 	new Piece(PieceType.P, 	PieceColour.WHITE, 6,	1);
		gameBoard[7][1] = 	new Piece(PieceType.P, 	PieceColour.WHITE, 7,	1);
		
		//fill the rest of the board with blank pieces
		for (int i = 0; i < SIZE; i++) {
			for (int j = SIZE - 3; j > 1; j--) {
				gameBoard[i][j] = blank;
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
		init = getInput("Initial", currentPlayer);
		fin = getInput("Final", currentPlayer);

		//move the specified piece to the specified location
		Piece piece = gameBoard[init[LETTER]][init[NUMBER]];
		piece.move(fin[LETTER],fin[NUMBER]);
		gameBoard[init[LETTER]][init[NUMBER]] = blank;
		gameBoard[fin[LETTER]][fin[NUMBER]] = piece;
		
		//collision detection needs to adjust scoreChange
	}
	
	private int[] getInput(String type, Player currentPlayer) {
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
			String coordinates = input.nextLine();

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
					valid = validMovement(type, letterCoordinate,  numberCoordinate, currentPlayer);
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
	
	private boolean validMovement(String type, int letCoordinate, int numCoordinate, Player currentPlayer) {
		/*
		 * TODO are pieces in the way? need a check
		 */

		// Default return
		boolean valid = true;

		//checks concerning initial coordinates
		if(type.equals("Initial")){
			//if coordinates point to a blank space then disallow them
			if(gameBoard[letCoordinate][numCoordinate] == blank){
				valid = false;
			}
			//if coordinates point to the other player's pieces then disallow them
			if(gameBoard[letCoordinate][numCoordinate].getColour() != currentPlayer.getColour()) {
				valid = false;
			}
		}
		
		//check if the final coordinate points to a space occupied by a piece
		//of the same color, or if this change in x and y coordinates is illegal
		//for the piece in question
		//if either is true, disallow move
		else if(type.equals("Final")){
			if(gameBoard[letCoordinate][numCoordinate].getColour() == gameBoard[init[LETTER]][init[NUMBER]].getColour()){
				valid = false;
			}
			if(!gameBoard[init[LETTER]][init[NUMBER]].validMove(letCoordinate, numCoordinate)){
				valid = false;
			}
		}
		return valid;
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

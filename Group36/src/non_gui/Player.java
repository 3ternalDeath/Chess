package non_gui;

import java.util.Scanner;

public class Player {
	private PieceColour colour;
	private int score;
	
	public Player(PieceColour colour, int score) {
		this.colour = colour;
		this.score = score;
	}
	
	public PieceColour getColour() {
		return colour;
	}
	
	public int getScore() {
		return score;
	}
	
	public void adjustScore(int adjustment) {
		score += adjustment;
	}
	
	public void makeMove(Board gameBoard) {
		Boolean validMove = false;
		do {
			Coordinates init = getInput("Initial", gameBoard);
			Coordinates fin = getInput("Final", gameBoard);
		
			// Checks valid movement
			validMove = gameBoard.validMovement(init, fin, this);
			if (validMove) {
				gameBoard.update(init, fin, this);
			}
		} while(!validMove);
	}

	private Coordinates getInput(String type, Board gameBoard) {
		Scanner input = new Scanner(System.in);

		char inputLetter = ' ';
		char inputNumber = ' ';

		Coordinates newCoor = new Coordinates(0,0);
		
		boolean validLocation = true;
		do {
			// UserInput
			System.out.print(type + " Coordinates (Ex. a1):");
			String coordinates = input.nextLine();

			// Coordinates must only have length 2
			if (coordinates.length() != 2) {
				System.out.println("Coordinates must be exactly two characters.");
				validLocation = false;
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
			}
			
			// Checks in bound
			validLocation = Board.inBound(inputLetter, inputNumber);
			if (validLocation == true) {

				// Changes coordinate char to int(0 to 7) for array
				newCoor.setX(inputLetter - 'a');
				newCoor.setY(inputNumber - '1');
			}

			// Invalid Input: Loop
			if (!validLocation) {
				System.out.println("Please reenter coordinates.");
			}

		} while (!validLocation);
		
		return newCoor;
	}
}

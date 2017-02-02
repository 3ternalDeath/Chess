

package non_gui;

import javax.swing.plaf.synth.SynthSplitPaneUI;

public class Piece {
	
	private final PieceType type;
	private final PieceColour colour;
	private int x;
	private int y;
	
	//Constructor
	public Piece(PieceType type, PieceColour colour, int x, int y) {
		this.type = type;
		this.colour = colour;
		this.x = x;
		this.y = y;
	}

	//changes x and y values according to parameters
	public void move(Coordinates newCoor) {
		//moves the piece
		this.x = newCoor.getX();
		this.y = newCoor.getY();
	}
	
	//generates and returns a string to represent a piece as long as it is not blank
	public String toString(){
		if(getColour() != null && getType() != null)
			return ((getColour()+"").charAt(0) + "") + ((getType()+"").charAt(0) + "") + "";
		else
			return ("  ");
	}
	
	//returns the x value
	public int getX() {
		return this.x;
	}
	
	//returns the y value
	public int getY() {
		return this.y;
	}
	
	//returns the type of piece
	public PieceType getType() {
		return this.type;
	}
	
	//returns the colour of piece
	public PieceColour getColour() {
		return this.colour;
	}
	
	//returns true if move is valid, false otherwise
	public boolean validMove(Coordinates newCoor){
		//checks based on piece type
		//if the move is allowed or not
		boolean validMove = true;
		int xDifference = newCoor.getX() - this.x;
		int yDifference = newCoor.getY() - this.y;
		int absXDifference = Math.abs(xDifference);
		int absYDifference = Math.abs(yDifference);
		System.out.println("I AM A " + this.type);
		System.out.println("XXXXXXXXXXXXXXX");
		System.out.println("this x: " + this.x);
		System.out.println("new x: " + newCoor.getX());
		System.out.println("xdiff: " + xDifference);
		System.out.println();
		System.out.println("YYYYYYYYYYYYYYY");
		System.out.println("this y: " + this.y);
		System.out.println("new y: " + newCoor.getY());
		System.out.println("ydiff: " + yDifference);
		System.out.println();
		System.out.println("AAAAABBBBBSSSSS");
		System.out.println("absX: " + absXDifference);
		System.out.println("absY: " + absYDifference);
		System.out.println("absdiffabsXY: "+ Math.abs(absXDifference - absYDifference));
		switch (this.type) {
		case King:
			System.out.println("KING SWITCH");
			//override this check if castling
			if (absXDifference > 1 || absYDifference > 1) {
				validMove = false;
			}
			break;
		case Queen:
			System.out.println("QUEEN SWITCH");
			//can move in a line and diagonally
			if (absXDifference != 0 && absYDifference != 0) {
				if (absYDifference != absXDifference) {
					validMove = false;
				}
			}
			break;
		case Rook:
			System.out.println("ROOK SWITCH");
			//Straight line only
			if (absXDifference != 0 && absYDifference != 0) {
				validMove = false;
			}
			break;
		case Night:
			System.out.println("LIGHT SWITCH");
			//2 in one direction and one perpendicular
			if (absXDifference < 1 || absXDifference > 2 ||
					absYDifference < 1 || absYDifference > 2) {
				if (Math.abs(absXDifference - absYDifference) != 1) {
					validMove = false;
				}
			}
			break;
		case Bishop:
			System.out.println("BISHOP SWITCH");
			//diagonal only
			if (absXDifference != absYDifference) {
				validMove = false;
			}
			break;
		case Pawn:
			System.out.println("PAWN SWITCH");
			//one space only
			//TODO: allow for 2-space move if pawn is being moved for first time
			if (absXDifference > 0 || absYDifference > 1) {
				validMove = false;
			}
			break;
		}
		return validMove;
	}
	
}

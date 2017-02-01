

package non_gui;
public class Piece {
	
	private final PieceType type;
	private final PieceColour colour;
	private int x;
	private int y;
	
	//Constructor
	public Piece(PieceType type, PieceColour colour, int y, int x) {
		this.type = type;
		this.colour = colour;
		this.x = x;
		this.y = y;
	}

	//changes x and y values according to parameters
	public void move(int newY, int newX) {
		//moves the piece
		this.x = newX;
		this.y = newY;
	}
	
	//generates and returns a string to represent a piece as long as it is not blank
	public String toString(){
		if(getColour() != PieceColour.NONE && getType() != PieceType.NONE)
			return ((getColour()+"").charAt(0) + "") + ((getType()+"").charAt(0) + "") + "";
		else
			return ("  ");
	}
	
	//returns the x value
	public int getX() {
		return x;
	}
	
	//returns the y value
	public int getY() {
		return y;
	}
	
	//returns the type of piece
	public PieceType getType() {
		return type;
	}
	
	//returns the colour of piece
	public PieceColour getColour() {
		return colour;
	}
	
	//returns true if move is valid, false otherwise
	public boolean validMove(int newY, int newX){
		//checks based on piece type
		//if the move is allowed or not
		boolean validMove = true;
		int xDifference = newX - this.x;
		int yDifference = newY - this.y;
		int absXDifference = Math.abs(xDifference);
		int absYDifference = Math.abs(yDifference);
		switch (type) {
		case K:
			//override this check if castling
			if (absXDifference > 1 || absYDifference > 1) {
				validMove = false;
			}
			break;
		case Q:
			//can move in a line and diagonally
			if (absXDifference != 0 && absYDifference != 0) {
				if (absYDifference != absXDifference) {
					validMove = false;
				}
			}
			break;
		case R:
			//Straight line only
			if (absXDifference != 0 && absYDifference != 0) {
				validMove = false;
			}
			break;
		case N:
			//2 in one direction and one perpendicular
			if (absXDifference < 1 || absXDifference > 2 ||
					absYDifference < 1 || absYDifference > 2) {
				if (Math.abs(absXDifference - absYDifference) != 1) {
					validMove = false;
				}
			}
			break;
		case B:
			//diagonal only
			if (absXDifference != absYDifference) {
				validMove = false;
			}
			break;
		case P:
			//one space only
			//TODO: allow for 2-space move if pawn is being moved for first time
			if (absXDifference > 1 || absYDifference > 0) {
				validMove = false;
			}
			break;
		}
		return validMove;
	}
	
}

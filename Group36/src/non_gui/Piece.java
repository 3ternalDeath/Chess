package non_gui;
public class Piece {
	
	private final PieceType type;
	private final PieceColour colour;
	private int x;
	private int y;
	
	public Piece(PieceType type, PieceColour colour, int y, int x) {
		this.type = type;
		this.colour = colour;
		this.x = x;
		this.y = y;
	}

	public void move(int newY, int newX) {
		//moves the piece
		this.x = newX;
		this.y = newY;
	}
	
	public String toString(){
		//generates a string to represent a piece as long as it is not blank
		if(getColour() != PieceColour.NONE && getType() != PieceType.NONE)
			return ((getColour()+"").charAt(0) + "") + ((getType()+"").charAt(0) + "") + "";
		else
			return ("  ");
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public PieceType getType() {
		return type;
	}
	
	public PieceColour getColour() {
		return colour;
	}
	
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
			//TODO: allow for 2-space move on first turn
			if (absXDifference > 1 || absYDifference > 0) {
				validMove = false;
			}
			break;
		}
		//need a check that two pieces are not occupying
		//the same space, and a check that a piece is not
		//jumping over another piece (unless it's a knight
		//or a castling rook/king)
		//these may be better suited for the Board class, however
		return validMove;
	}
	
}
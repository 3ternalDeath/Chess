package non_gui;
public class Piece {
	
	private final PieceType type;
	private final PieceColour colour;
	private int x;
	private int y;
	
	public Piece(PieceType type, PieceColour colour, int x, int y) {
		this.type = type;
		this.colour = colour;
		this.x = x;
		this.y = y;
	}

	public void move(int newX, int newY) {
		if (validMove(newX, newY)) {
			this.x = newX;
			this.y = newY;
		}
		else {
			//throw an exception?
		}
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
	
	private boolean validMove(int newX, int newY){
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
			if (absXDifference != 0) {
				if (absYDifference != absXDifference) {
					validMove = false;
				}
			}
			break;
		case R:
			if (absXDifference != 0 && absYDifference != 0) {
				validMove = false;
			}
			break;
		case N:
			if (absXDifference < 2 || absXDifference > 3 ||
					absYDifference < 2 || absYDifference > 3) {
				if (Math.abs(absXDifference - absYDifference) != 1) {
					validMove = false;
				}
			}
			break;
		case B:
			if (absXDifference != absYDifference) {
				validMove = false;
			}
			break;
		case P:
			//allow for 2-space move on first turn
			if (absXDifference > 1 || xDifference < 0 || absYDifference > 0) {
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
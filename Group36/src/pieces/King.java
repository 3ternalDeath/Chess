package pieces;

import java.util.ArrayList;

import engine.Coordinates;

/**
 * A single King piece.
 * @author Group 36
 */
@SuppressWarnings("serial")
public class King extends Piece {

	/**
	 * Constructor for the King class.
	 * @param coor The starting coordinates of the king.
	 * @param color The color of the king.
	 * @param isFirstMove The first move status of the king
	 */
	public King(Coordinates coor, PieceColor color, boolean isFirstMove) {
		super(coor, PieceType.King, color, isFirstMove);
	}
	
	/**
	 * Updates the list of possible moves for King.
	 * @return All moves the piece can theoretically make from its current location.
	 */
	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		//First move castling
		if (isFirstMove()) {
			if (Coordinates.inBound(getX() + 2, getY()))
				moves.add(new Coordinates(getX() + 2, getY()));
			if(Coordinates.inBound(getX() - 2, getY()))
				moves.add(new Coordinates(getX() - 2, getY()));
		}
			
		//Horizontal and vertical moves
		if (Coordinates.inBound(getX(), getY() + 1))
			moves.add(new Coordinates(getX(), getY() + 1));
		if (Coordinates.inBound(getX(), getY() - 1))
			moves.add(new Coordinates(getX(), getY() - 1));
		if (Coordinates.inBound(getX() + 1, getY()))
			moves.add(new Coordinates(getX() + 1, getY()));
		if (Coordinates.inBound(getX() - 1, getY()))
			moves.add(new Coordinates(getX() - 1, getY()));
		
		//Diagonal moves.
		if (Coordinates.inBound(getX() + 1, getY() + 1))
			moves.add(new Coordinates(getX() + 1, getY() + 1));
		if (Coordinates.inBound(getX() + 1, getY() - 1))
			moves.add(new Coordinates(getX() + 1, getY() - 1));
		if (Coordinates.inBound(getX() - 1, getY() + 1))
			moves.add(new Coordinates(getX() - 1, getY() + 1));
		if (Coordinates.inBound(getX() - 1, getY() - 1))
			moves.add(new Coordinates(getX() - 1, getY() - 1));
			
		return moves;
	}
}

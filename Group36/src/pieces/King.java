package pieces;

import java.util.ArrayList;

import engine.Coordinates;

/**
 * A single King piece.
 * @author Group 36
 */
public class King extends Piece {

	/**
	 * Constructor for the King class.
	 * @param coor The starting coordinates of the king.
	 * @param Color The color of the king.
	 * @param b 
	 */
	public King(Coordinates coor, PieceColor Color, boolean isFirstMove) {
		super(coor, PieceType.King, Color, isFirstMove);
	}
	

	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
			
		if (isFirstMove()) {
			if (Coordinates.inBound(getX() + 2, getY()))
				moves.add(new Coordinates(getX() + 2, getY()));
			if(Coordinates.inBound(getX() - 2, getY()))
				moves.add(new Coordinates(getX() - 2, getY()));
		}
			
		if (Coordinates.inBound(getX(), getY() + 1))
			moves.add(new Coordinates(getX(), getY() + 1));
		if (Coordinates.inBound(getX(), getY() - 1))
			moves.add(new Coordinates(getX(), getY() - 1));
		if (Coordinates.inBound(getX() + 1, getY()))
			moves.add(new Coordinates(getX() + 1, getY()));
		if (Coordinates.inBound(getX() - 1, getY()))
			moves.add(new Coordinates(getX() - 1, getY()));
			
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

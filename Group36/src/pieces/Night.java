package pieces;

import java.util.ArrayList;

import engine.Coordinates;

/**
 * A single Knight piece.
 * @author Group 36
 */
public class Night extends Piece {

	/**
	 * Constructor for the Knight class.
	 * @param coor The starting coordinates of the knight.
	 * @param Color The color of the knight.
	 * @param isFirstMove 
	 */
	public Night(Coordinates coor, PieceColor Color, boolean isFirstMove) {
		super(coor, PieceType.Night, Color, isFirstMove);
	}

	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		if (Coordinates.inBound(getX() + 2, getY() + 1))
			moves.add(new Coordinates(getX() + 2, getY() + 1));
		if (Coordinates.inBound(getX() + 2, getY() - 1))
			moves.add(new Coordinates(getX() + 2, getY() - 1));
		
		if (Coordinates.inBound(getX() - 2, getY() + 1))
			moves.add(new Coordinates(getX() - 2, getY() + 1));
		if (Coordinates.inBound(getX() - 2, getY() - 1))
			moves.add(new Coordinates(getX() - 2, getY() - 1));
		
		if (Coordinates.inBound(getX() + 1, getY() + 2))
			moves.add(new Coordinates(getX() + 1, getY() + 2));
		if (Coordinates.inBound(getX() + 1, getY() - 2))
			moves.add(new Coordinates(getX() + 1, getY() - 2));
		
		if (Coordinates.inBound(getX() - 1, getY() + 2))
			moves.add(new Coordinates(getX() - 1, getY() + 2));
		if (Coordinates.inBound(getX() - 1, getY() - 2))
			moves.add(new Coordinates(getX() - 1, getY() - 2));
		
		return moves;
	}
}

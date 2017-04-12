package pieces;

import java.util.ArrayList;

import engine.Coordinates;

/**
 * A single Knight piece.
 * @author Group 36
 */
@SuppressWarnings("serial")
public class Night extends Piece {

	/**
	 * Constructor for the Knight class.
	 * @param coor The starting coordinates of the knight.
	 * @param color The color of the knight.
	 * @param isFirstMove The first move status of the knight.
	 */
	public Night(Coordinates coor, PieceColor color, boolean isFirstMove) {
		super(coor, PieceType.Night, color, isFirstMove);
	}
	
	/**
	 * Updates the list of possible moves for Night.
	 * @return All moves the piece can theoretically make from its current location.
	 */
	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		//Left moves
		if (Coordinates.inBound(getX() - 2, getY() + 1))
			moves.add(new Coordinates(getX() - 2, getY() + 1));
		if (Coordinates.inBound(getX() - 2, getY() - 1))
			moves.add(new Coordinates(getX() - 2, getY() - 1));

		//Right moves
		if (Coordinates.inBound(getX() + 2, getY() + 1))
			moves.add(new Coordinates(getX() + 2, getY() + 1));
		if (Coordinates.inBound(getX() + 2, getY() - 1))
			moves.add(new Coordinates(getX() + 2, getY() - 1));
		
		//Up moves
		if (Coordinates.inBound(getX() + 1, getY() + 2))
			moves.add(new Coordinates(getX() + 1, getY() + 2));
		if (Coordinates.inBound(getX() - 1, getY() + 2))
			moves.add(new Coordinates(getX() - 1, getY() + 2));
		
		//Down moves
		if (Coordinates.inBound(getX() + 1, getY() - 2))
			moves.add(new Coordinates(getX() + 1, getY() - 2));
		if (Coordinates.inBound(getX() - 1, getY() - 2))
			moves.add(new Coordinates(getX() - 1, getY() - 2));
		
		return moves;
	}
}

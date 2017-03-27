package pieces;

import java.util.ArrayList;

import engine.Coordinates;

/**
 * An empty space -- behaves like a piece in some ways, but
 * is not technically a piece.
 * @author Group 36
 */
public class Empty extends Piece {

	/**
	 * Constructor, with color option, for the Empty class.
	 * @param coor The starting coordinates of the empty space.
	 * @param color The color of the empty space (goes unused).
	 */
	public Empty(Coordinates coor, PieceColor color) {
		super(coor);
	}

	/**
	 * Constructor, without color option, for the Empty class.
	 * @param coor The starting coordinates of the empty space.
	 */
	public Empty(Coordinates coor) {
		super(coor);
	}
	
	public ArrayList<Coordinates> getPossibleMoves() {
		return null;
	}
}

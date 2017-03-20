package pieces;

import java.util.ArrayList;

import non_gui.Coordinates;

/**
 * An empty space -- behaves like a piece in some ways, but
 * is not technically a piece.
 * @author Group 36
 */
public class Empty extends Piece {

	/**
	 * Constructor for the Empty class.
	 * @param coor The starting coordinates of the empty space.
	 */
	public Empty(Coordinates coor, PieceColor color) {
		super(coor);
	}

	public Empty(Coordinates coor) {
		super(coor);
	}
	
	public ArrayList<Coordinates> getPossibleMoves() {
		return null;
	}
}

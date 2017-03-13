package non_gui;

import java.util.ArrayList;

/**
 * An empty space -- behaves like a piece in some ways, but
 * is not technically a piece.
 * @author Group 36
 */
public class Empty extends Piece {

	/**
	 * Constructor for the Empty class.
	 * @param x The starting x position of the empty space.
	 * @param y The starting y position of the empty space.
	 */
	public Empty(int x, int y) {
		super(x, y);
	}
	
	public Empty(PieceColor k, int x, int y) {
		super(x, y);
	}

	public ArrayList<Coordinates> getPossibleMoves() {
		return null;
	}
}

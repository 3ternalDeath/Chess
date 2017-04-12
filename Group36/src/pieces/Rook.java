package pieces;

import java.util.ArrayList;

import engine.Coordinates;

/**
 * A single Rook piece.
 * @author Group 36
 */
public class Rook extends Piece {

	/**
	 * Constructor for the Rook class.
	 * @param coor The starting coordinates of the rook.
	 * @param color The color of the rook.
	 * @param isFirstMove The first move status of the rook.
	 */
	public Rook(Coordinates coor, PieceColor color, boolean isFirstMove) {
		super(coor, PieceType.Rook, color, isFirstMove);
	}
	
	/**
	 * Updates the list of possible moves for Rook.
	 * @return All moves the piece can theoretically make from its current location.
	 */
	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		//horizontal and vertical moves
		for (int x = 0; x < SIZE; x++)
			if (x != getX())
				moves.add(new Coordinates(x, getY()));
		for (int y = 0; y < SIZE; y++)
			if (y != getY())
				moves.add(new Coordinates(getX(), y));
		
		return moves;
	}
}
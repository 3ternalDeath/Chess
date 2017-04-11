package pieces;

import java.util.ArrayList;

import engine.Coordinates;

/**
 * A single Bishop piece.
 * @author Group 36
 */
public class Bishop extends Piece {

	/**
	 * Constructor for the Bishop class.
	 * @param coor The starting coordinates of the bishop.
	 * @param color The color of the bishop.
	 * @param isFirstMove The first move status of the bishop.
	 */
	public Bishop(Coordinates coor, PieceColor color, boolean isFirstMove) {
		super(coor, PieceType.Bishop, color, isFirstMove);
	}

	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		for (int h = 1; Coordinates.inBound(getX() + h, getY() + h); h++) {
			moves.add(new Coordinates(getX() + h, getY() + h));
		}
		for (int h = 1; Coordinates.inBound(getX() + h, getY() - h); h++) {
			moves.add(new Coordinates(getX() + h, getY() - h));
		}
		for (int h = 1; Coordinates.inBound(getX() - h, getY() + h); h++) {
			moves.add(new Coordinates(getX() - h, getY() + h));
		}
		for (int h = 1; Coordinates.inBound(getX() - h, getY() - h); h++) {
			moves.add(new Coordinates(getX() - h, getY() - h));
		}
		
		return moves;
	}
}

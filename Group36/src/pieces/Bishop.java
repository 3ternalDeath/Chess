package pieces;

import java.util.ArrayList;

import non_gui.Coordinates;

/**
 * A single Bishop piece.
 * @author Group 36
 */
public class Bishop extends Piece {

	/**
	 * Constructor for the Bishop class.
	 * @param Color The color of the bishop.
	 * @param x The starting x position of the bishop.
	 * @param y The starting y position of the bishop.
	 */
	public Bishop(Coordinates coor,PieceColor Color) {
		super(coor, PieceType.Bishop, Color);
	}

	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		for(int h = 1; Coordinates.inBound(getX() + h, getY() + h); h++) {
			moves.add(new Coordinates(getX() + h, getY() + h));
		}
		for(int h = 1; Coordinates.inBound(getX() + h, getY() - h); h++) {
			moves.add(new Coordinates(getX() + h, getY() - h));
		}
		for(int h = 1; Coordinates.inBound(getX() - h, getY() + h); h++) {
			moves.add(new Coordinates(getX() - h, getY() + h));
		}
		for(int h = 1; Coordinates.inBound(getX() - h, getY() - h); h++) {
			moves.add(new Coordinates(getX() - h, getY() - h));
		}
		
		return moves;
	}
}

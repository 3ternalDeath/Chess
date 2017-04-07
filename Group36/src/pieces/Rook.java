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
	 * @param Color The color of the rook.
	 * @param isFirstMove 
	 */
	public Rook(Coordinates coor, PieceColor Color, boolean isFirstMove) {
		super(coor, PieceType.Rook, Color, isFirstMove);
	}

	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		for (int x = getX()+1; Coordinates.inBound(x); x++) {
			moves.add(new Coordinates(x, getY()));
		}
		for (int x = getX()-1; Coordinates.inBound(x); x--) {
			moves.add(new Coordinates(x, getY()));
		}
		for (int y = getY()+1; Coordinates.inBound(y); y++) {
			moves.add(new Coordinates(getX(), y));
		}
		for (int y = getY()-1; Coordinates.inBound(y); y--) {
			moves.add(new Coordinates(getX(), y));
		}
		
		return moves;
	}
}
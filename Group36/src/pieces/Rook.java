package pieces;

import java.util.ArrayList;

import non_gui.Coordinates;

/**
 * A single Rook piece.
 * @author Group 36
 */
public class Rook extends Piece {

	/**
	 * Constructor for the Rook class.
	 * @param Color The color of the rook.
	 * @param x The starting x position of the rook.
	 * @param y The starting y position of the rook.
	 */
	public Rook(Coordinates coor, PieceColor Color) {
		super(coor, PieceType.Rook, Color);
	}

	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		for(int x = getX()+1; Coordinates.inBound(x); x++) {
			moves.add(new Coordinates(x, getY()));
		}
		for(int x = getX()-1; Coordinates.inBound(x); x--) {
			moves.add(new Coordinates(x, getY()));
		}
		for(int y = getX()+1; Coordinates.inBound(y); y++) {
			moves.add(new Coordinates(getX(), y));
		}
		for(int y = getX()-1; Coordinates.inBound(y); y--) {
			moves.add(new Coordinates(getX(), y));
		}
		return moves;
	}
}
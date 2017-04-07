package pieces;

import java.util.ArrayList;

import engine.Coordinates;

/**
 * A single Queen piece.
 * @author Group 36
 */
public class Queen extends Piece {
	
	/**
	 * Constructor for the Queen class.
	 * @param coor The starting coordinates of the queen.
	 * @param Color The color of the queen.
	 */
	public Queen(Coordinates coor, PieceColor Color, boolean isFirstMove) {
		super(coor, PieceType.Queen, Color, isFirstMove);
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

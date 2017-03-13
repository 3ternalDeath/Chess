package non_gui;

import java.util.ArrayList;

/**
 * A single Queen piece.
 * @author Group 36
 */
public class Queen extends Piece {
	
	/**
	 * Constructor for the Queen class.
	 * @param Color The color of the queen.
	 * @param x The starting x position of the queen.
	 * @param y The starting y position of the queen.
	 */
	public Queen(PieceColor Color, int x, int y) {
		super(PieceType.Queen, Color, x, y);
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

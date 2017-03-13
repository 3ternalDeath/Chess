package non_gui;

import java.util.ArrayList;

/**
 * A single King piece.
 * @author Group 36
 */
public class King extends Piece {

	/**
	 * Constructor for the King class.
	 * @param Color The color of the king.
	 * @param x The starting x position of the king.
	 * @param y The starting y position of the king.
	 */
	public King(PieceColor Color, int x, int y) {
		super(PieceType.King, Color, x, y);
	}
		
	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
			
		if(isFirstMove()) {
			if(Coordinates.inBound(getX() + 2, getY()))
				moves.add(new Coordinates(getX() + 2, getY()));
		}
			
		if(Coordinates.inBound(getX(), getY() + 1))
			moves.add(new Coordinates(getX(), getY() + 1));
		if(Coordinates.inBound(getX(), getY() - 1))
			moves.add(new Coordinates(getX(), getY() - 1));
		if(Coordinates.inBound(getX() + 1, getY()))
			moves.add(new Coordinates(getX() + 1, getY()));
		if(Coordinates.inBound(getX() - 1, getY()))
			moves.add(new Coordinates(getX() - 1, getY()));
			
		if(Coordinates.inBound(getX() + 1, getY() + 1))
			moves.add(new Coordinates(getX() + 1, getY() + 1));
		if(Coordinates.inBound(getX() + 1, getY() - 1))
			moves.add(new Coordinates(getX() + 1, getY() - 1));
		if(Coordinates.inBound(getX() - 1, getY() + 1))
			moves.add(new Coordinates(getX() - 1, getY() + 1));
		if(Coordinates.inBound(getX() - 1, getY() - 1))
			moves.add(new Coordinates(getX() - 1, getY() - 1));
			
		return moves;
	}
}

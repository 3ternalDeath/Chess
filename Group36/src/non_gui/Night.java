package non_gui;

import java.util.ArrayList;

/**
 * A single Knight piece.
 * @author Group 36
 */
public class Night extends Piece {

	/**
	 * Constructor for the Knight class.
	 * @param Color The color of the knight.
	 * @param x The starting x position of the knight.
	 * @param y The starting y position of the knight.
	 */
	public Night(PieceColor Color, int x, int y) {
		super(PieceType.Night, Color, x, y);
	}

	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		if(Coordinates.inBound(getX() + 2, getY() + 1))
			moves.add(new Coordinates(getX() + 2, getY() + 1));
		if(Coordinates.inBound(getX() + 2, getY() - 1))
			moves.add(new Coordinates(getX() + 2, getY() - 1));
		
		if(Coordinates.inBound(getX() - 2, getY() + 1))
			moves.add(new Coordinates(getX() - 2, getY() + 1));
		if(Coordinates.inBound(getX() - 2, getY() - 1))
			moves.add(new Coordinates(getX() - 2, getY() - 1));
		
		if(Coordinates.inBound(getX() + 1, getY() + 2))
			moves.add(new Coordinates(getX() + 1, getY() + 2));
		if(Coordinates.inBound(getX() + 1, getY() - 2))
			moves.add(new Coordinates(getX() + 1, getY() - 2));
		
		if(Coordinates.inBound(getX() - 1, getY() + 2))
			moves.add(new Coordinates(getX() - 1, getY() + 2));
		if(Coordinates.inBound(getX() - 1, getY() - 2))
			moves.add(new Coordinates(getX() - 1, getY() - 2));
		
		return moves;
	}
}

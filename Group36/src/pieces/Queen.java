package pieces;

import java.util.ArrayList;

import engine.Coordinates;

/**
 * A single Queen piece.
 * @author Group 36
 */
@SuppressWarnings("serial")
public class Queen extends Piece {
	
	/**
	 * Constructor for the Queen class.
	 * @param coor The starting coordinates of the queen.
	 * @param color The color of the queen.
	 * @param isFirstMove The first move status of the queen.
	 */
	public Queen(Coordinates coor, PieceColor color, boolean isFirstMove) {
		super(coor, PieceType.Queen, color, isFirstMove);
	}
	
	/**
	 * Updates the list of possible moves for Queen.
	 * @return All moves the piece can make on an empty board from its current location.
	 */
	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		//Horizontal and vertical moves
		for (int x = 0; x < SIZE; x++)
			if (x != getX())
				moves.add(new Coordinates(x, getY()));
		for (int y = 0; y < SIZE; y++)
			if (y != getY())
				moves.add(new Coordinates(getX(), y));
		
		//Diagonal moves
		for (int h = 1; Coordinates.inBound(getX() + h, getY() + h); h++)
			moves.add(new Coordinates(getX() + h, getY() + h));
		for (int h = 1; Coordinates.inBound(getX() + h, getY() - h); h++)
			moves.add(new Coordinates(getX() + h, getY() - h));
		for (int h = 1; Coordinates.inBound(getX() - h, getY() + h); h++)
			moves.add(new Coordinates(getX() - h, getY() + h));
		for (int h = 1; Coordinates.inBound(getX() - h, getY() - h); h++)
			moves.add(new Coordinates(getX() - h, getY() - h));
		
		return moves;
	}
}
package pieces;

import java.util.ArrayList;

import engine.Coordinates;

/**
 * A single Pawn piece.
 * @author Group 36
 */
public class Pawn extends Piece {
	
	/**
	 * Constructor for the Pawn class.
	 * @param coor The starting coordinates of the pawn.
	 * @param color The color of the pawn.
	 * @param isFirstMove The first move status of the pawn.
	 */
	public Pawn(Coordinates coor, PieceColor color, boolean isFirstMove) {
		super(coor, PieceType.Pawn, color, isFirstMove);
	}

	/**
	 * Updates the list of possible moves for Pawn.
	 * @return All moves the piece can theoretically make from its current location.
	 */
	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		if (getColor() == PieceColor.White) {
			//First move two spaces
			if (isFirstMove())
				if (Coordinates.inBound(getX(), getY() + 2))
					moves.add(new Coordinates(getX(), getY() + 2));
		
			//Moving forwards
			if (Coordinates.inBound(getX() , getY() + 1))
				moves.add(new Coordinates(getX(), getY() + 1));
			
			//Killing diagonally
			if (Coordinates.inBound(getX() + 1, getY() + 1))
				moves.add(new Coordinates(getX() + 1, getY() + 1));
			if (Coordinates.inBound(getX() - 1, getY() + 1))
				moves.add(new Coordinates(getX() - 1, getY() + 1));
		}
		else {
			//First move two spaces.
			if (isFirstMove())
				if (Coordinates.inBound(getX(), getY() - 2))
					moves.add(new Coordinates(getX(), getY() - 2));
		
			//Moving forwards.
			if (Coordinates.inBound(getX() , getY() - 1))
				moves.add(new Coordinates(getX(), getY() - 1));
			
			//Killing diagonally.
			if (Coordinates.inBound(getX() + 1, getY() - 1))
				moves.add(new Coordinates(getX() + 1, getY() - 1));
			if (Coordinates.inBound(getX() - 1, getY() - 1))
				moves.add(new Coordinates(getX() - 1, getY() - 1));
		}
		
		return moves;
	}
}

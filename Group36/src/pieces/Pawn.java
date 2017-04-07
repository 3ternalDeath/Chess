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
	 * @param Color The color of the pawn.
	 * @param isFirstMove 
	 */
	public Pawn(Coordinates coor, PieceColor Color, boolean isFirstMove) {
		super(coor, PieceType.Pawn, Color, isFirstMove);
	}

	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		if (this.getColor() == PieceColor.White) {
			if (isFirstMove())
				if (Coordinates.inBound(getX(), getY() + 2))
					moves.add(new Coordinates(getX(), getY() + 2));
		
			if (Coordinates.inBound(getX() , getY() + 1))
				moves.add(new Coordinates(getX(), getY() + 1));
			
			if (Coordinates.inBound(getX() + 1, getY() + 1))
				moves.add(new Coordinates(getX() + 1, getY() + 1));
			if (Coordinates.inBound(getX() - 1, getY() + 1))
				moves.add(new Coordinates(getX() - 1, getY() + 1));
		}
		else {
			if (isFirstMove())
				if (Coordinates.inBound(getX(), getY() - 2))
					moves.add(new Coordinates(getX(), getY() - 2));
		
			if (Coordinates.inBound(getX() , getY() - 1))
				moves.add(new Coordinates(getX(), getY() - 1));
			
			if (Coordinates.inBound(getX() + 1, getY() - 1))
				moves.add(new Coordinates(getX() + 1, getY() - 1));
			if (Coordinates.inBound(getX() - 1, getY() - 1))
				moves.add(new Coordinates(getX() - 1, getY() - 1));
		}
		
		return moves;
	}
}

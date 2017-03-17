package pieces;

import java.util.ArrayList;

import non_gui.Coordinates;

/**
 * A single Pawn piece.
 * @author Group 36
 */
public class Pawn extends Piece {
	
	/**
	 * Constructor for the Pawn class.
	 * @param Color The color of the pawn.
	 * @param x The starting x position of the pawn.
	 * @param y The starting y position of the pawn.
	 */
	public Pawn(PieceColor Color, int x, int y) {
		super(PieceType.Pawn, Color, x, y);
	}

	public ArrayList<Coordinates> getPossibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		if(this.getColor() == PieceColor.White) {
			if(isFirstMove())
				if(Coordinates.inBound(getX(), getY() + 2))
					moves.add(new Coordinates(getX(), getY() + 2));
		
			if(Coordinates.inBound(getX() , getY() + 1))
				moves.add(new Coordinates(getX(), getY() + 1));
			
			if(Coordinates.inBound(getX() + 1, getY() + 1))
				moves.add(new Coordinates(getX() + 1, getY() + 1));
			if (Coordinates.inBound(getX() - 1, getY() + 1))
				moves.add(new Coordinates(getX() - 1, getY() + 1));
		}
		else {
			if(isFirstMove())
				if(Coordinates.inBound(getX(), getY() - 2))
					moves.add(new Coordinates(getX(), getY() - 2));
		
			if(Coordinates.inBound(getX() , getY() - 1))
				moves.add(new Coordinates(getX(), getY() - 1));
			
			if(Coordinates.inBound(getX() + 1, getY() - 1))
				moves.add(new Coordinates(getX() + 1, getY() - 1));
			if (Coordinates.inBound(getX() - 1, getY() - 1))
				moves.add(new Coordinates(getX() - 1, getY() - 1));
		}
		
		return moves;
	}
}

package non_gui;

import java.util.ArrayList;

public class Pawn extends Piece {
	
	/**
	 * initialize a pawn
	 * @param Color the color of the pawn
	 * @param x the x position of the pawn
	 * @param y the y position of the pawn
	 */
	public Pawn(PieceColor Color, int x, int y){
		super(PieceType.Pawn, Color, x, y);
	}

	/**
	 * updates the possible moves list based on the piece type
	 */
	public ArrayList<Coordinates> getPosibleMoves() {
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

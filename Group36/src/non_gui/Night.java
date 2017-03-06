package non_gui;

import java.util.ArrayList;

public class Night extends Piece {

	/**
	 * initialize a knight
	 * @param Color the color of the knight
	 * @param x the x position of the knight
	 * @param y the y position of the knight
	 */
	public Night(PieceColor Color, int x, int y){
		super(PieceType.Night, Color, x, y);
	}

	/**
	 * updates the possible moves list based on the piece type
	 */
	public ArrayList<Coordinates> updateMoves() {
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

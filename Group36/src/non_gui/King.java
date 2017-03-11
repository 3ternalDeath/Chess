package non_gui;

import java.util.ArrayList;

public class King extends Piece {

	/**
	 * initialize a king
	 * @param Color the color of the king
	 * @param x the x position of the king
	 * @param y the y position of the king
	 */
	public King(PieceColor Color, int x, int y){
		super(PieceType.King, Color, x, y);
	}
		
	/**
	 * updates the possible moves list based on the piece type
	 */
	public ArrayList<Coordinates> getPosibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
			
		if(isFirstMove()){
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

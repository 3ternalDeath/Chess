package non_gui;

import java.util.ArrayList;

public class Bishop extends Piece {

	/**
	 * initialize a bishop
	 * @param Color the color of the bishop
	 * @param x the x position of the bishop
	 * @param y the y position of the bishop
	 */
	public Bishop(PieceColor Color, int x, int y){
		super(PieceType.Bishop, Color, x, y);
	}

	/**
	 * updates the possible moves list based on the piece type
	 */
	public ArrayList<Coordinates> getPosibleMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		for(int h = 1; Coordinates.inBound(getX() + h, getY() + h); h++){
			moves.add(new Coordinates(getX() + h, getY() + h));
		}
		for(int h = 1; Coordinates.inBound(getX() + h, getY() - h); h++){
			moves.add(new Coordinates(getX() + h, getY() - h));
		}
		for(int h = 1; Coordinates.inBound(getX() - h, getY() + h); h++){
			moves.add(new Coordinates(getX() - h, getY() + h));
		}
		for(int h = 1; Coordinates.inBound(getX() - h, getY() - h); h++){
			moves.add(new Coordinates(getX() - h, getY() - h));
		}
		
		return moves;
	}

}

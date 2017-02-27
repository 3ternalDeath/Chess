package non_gui;

import java.util.ArrayList;

public class Rook extends Piece {

	/**
	 * initialize a rook
	 * @param Color the color of the rook
	 * @param x the x position of the rook
	 * @param y the y position of the rook
	 */
	public Rook(PieceColor Color, int x, int y){
		super(PieceType.Rook, Color, x, y);
	}

	/**
	 * updates the possible moves list
	 */
	protected void updateMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		for(int x = getX()+1; Coordinates.inBound(x); x++){
			moves.add(new Coordinates(x, getY()));
		}
		for(int x = getX()-1; Coordinates.inBound(x); x--){
			moves.add(new Coordinates(x, getY()));
		}
		for(int y = getX()+1; Coordinates.inBound(y); y++){
			moves.add(new Coordinates(getX(), y));
		}
		for(int y = getX()-1; Coordinates.inBound(y); y--){
			moves.add(new Coordinates(getX(), y));
		}
		posMoves = moves;
	}

}

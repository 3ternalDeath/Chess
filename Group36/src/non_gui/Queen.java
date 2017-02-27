package non_gui;

import java.util.ArrayList;

public class Queen extends Piece{
	
	public Queen(PieceColor Color, int x, int y){
		super(PieceType.Queen, Color, x, y);
	}

	/**
	 * updates the possible moves list based on the piece type
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
		
		posMoves = moves;
	}
}

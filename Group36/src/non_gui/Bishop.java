package non_gui;

import java.util.ArrayList;

public class Bishop extends Piece {

	public Bishop(PieceColor Color, int x, int y){
		super(PieceType.Bishop, Color, x, y);
	}

	protected void updateMoves() {
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
		
		posMoves = moves;
	}

}

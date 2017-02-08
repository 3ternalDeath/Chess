package non_gui;

import java.util.ArrayList;

public class Queen extends Piece{
	
	public Queen(PieceColour colour, int x, int y){
		super(PieceType.Queen, colour, x, y);
	}

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

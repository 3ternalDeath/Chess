package non_gui;

import java.util.ArrayList;

public class Rook extends Piece {

	public Rook(PieceColour colour, int x, int y){
		super(PieceType.Rook, colour, x, y);
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
		posMoves = moves;
	}

}

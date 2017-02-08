package non_gui;

import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(PieceColour colour, int x, int y){
		super(PieceType.Night, colour, x, y);
	}

	protected void updateMoves() {
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
		
		posMoves = moves;
	}

}

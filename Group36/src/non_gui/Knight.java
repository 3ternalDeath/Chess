package non_gui;

import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(PieceColour colour, int x, int y){
		super(PieceType.Night, colour, x, y);
	}

	protected ArrayList<Coordinates> updateMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		moves.add(new Coordinates(getX() + 2, getY() + 1));
		moves.add(new Coordinates(getX() + 2, getY() - 1));
		moves.add(new Coordinates(getX() - 2, getY() + 1));
		moves.add(new Coordinates(getX() - 2, getY() - 1));
		moves.add(new Coordinates(getX() + 1, getY() + 2));
		moves.add(new Coordinates(getX() + 1, getY() - 2));
		moves.add(new Coordinates(getX() - 1, getY() + 2));
		moves.add(new Coordinates(getX() - 1, getY() - 2));
		
		return moves;
	}

}

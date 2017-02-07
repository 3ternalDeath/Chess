package non_gui;

import java.util.ArrayList;

public class Pawn extends Piece {
	
	private boolean firstMove;

	public Pawn(PieceColour colour, int x, int y){
		super(PieceType.Pawn, colour, x, y);
		firstMove = true;
	}

	public void move(Coordinates newCoor) {
		if(firstMove)
			firstMove = false;
		super.move(newCoor);
	}
	
	protected ArrayList<Coordinates> updateMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		if(firstMove)
			moves.add(new Coordinates(getX(), getY() + 2));
		moves.add(new Coordinates(getX(), getY() + 1));
		
		return moves;
	}

}

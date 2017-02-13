package non_gui;

import java.util.ArrayList;

public class Pawn extends Piece {
	
	private boolean firstMove;

	public Pawn(PieceColor Color, int x, int y){
		super(PieceType.Pawn, Color, x, y);
		firstMove = true;
		updateMoves();//repeated in this constructor due to being unable to have firstMove = true before it is run in the Piece constructor
	}

	public void move(Coordinates newCoor) {//overridden due to the special nature of pawn
		if(firstMove)
			firstMove = false;
		super.move(newCoor);
	}
	
	protected void updateMoves() {
		ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
		
		if(this.getColor() == PieceColor.White) {
			if(firstMove)
				if(Coordinates.inBound(getX(), getY() + 2))
					moves.add(new Coordinates(getX(), getY() + 2));
		
			if(Coordinates.inBound(getX() , getY() + 1))
				moves.add(new Coordinates(getX(), getY() + 1));
			
			if(Coordinates.inBound(getX() + 1, getY() + 1))
				moves.add(new Coordinates(getX() + 1, getY() + 1));
			if (Coordinates.inBound(getX() - 1, getY() + 1))
				moves.add(new Coordinates(getX() - 1, getY() + 1));
		}
		else {
			if(firstMove)
				if(Coordinates.inBound(getX(), getY() - 2))
					moves.add(new Coordinates(getX(), getY() - 2));
		
			if(Coordinates.inBound(getX() , getY() - 1))
				moves.add(new Coordinates(getX(), getY() - 1));
			
			if(Coordinates.inBound(getX() + 1, getY() - 1))
				moves.add(new Coordinates(getX() + 1, getY() - 1));
			if (Coordinates.inBound(getX() - 1, getY() - 1))
				moves.add(new Coordinates(getX() - 1, getY() - 1));
		}
		
		posMoves = moves;
	}

}

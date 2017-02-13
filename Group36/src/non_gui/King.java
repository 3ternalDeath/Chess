package non_gui;

import java.util.ArrayList;

public class King extends Piece {

		public King(PieceColor Color, int x, int y){
			super(PieceType.King, Color, x, y);
		}

		protected void updateMoves() {
			ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
			
			if(Coordinates.inBound(getX(), getY() + 1))
				moves.add(new Coordinates(getX(), getY() + 1));
			if(Coordinates.inBound(getX(), getY() - 1))
				moves.add(new Coordinates(getX(), getY() - 1));
			if(Coordinates.inBound(getX() + 1, getY()))
				moves.add(new Coordinates(getX() + 1, getY()));
			if(Coordinates.inBound(getX() - 1, getY()))
				moves.add(new Coordinates(getX() - 1, getY()));
			
			
			if(Coordinates.inBound(getX() + 1, getY() + 1))
				moves.add(new Coordinates(getX() + 1, getY() + 1));
			if(Coordinates.inBound(getX() + 1, getY() - 1))
				moves.add(new Coordinates(getX() + 1, getY() - 1));
			if(Coordinates.inBound(getX() - 1, getY() + 1))
				moves.add(new Coordinates(getX() - 1, getY() + 1));
			if(Coordinates.inBound(getX() - 1, getY() - 1))
				moves.add(new Coordinates(getX() - 1, getY() - 1));
			
			posMoves = moves;
			
		}
}

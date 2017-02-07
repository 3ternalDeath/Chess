package non_gui;

import java.util.ArrayList;

public class King extends Piece {

		public King(PieceColour colour, int x, int y){
			super(PieceType.King, colour, x, y);
		}

		protected ArrayList<Coordinates> updateMoves() {
			ArrayList<Coordinates> moves = new ArrayList<Coordinates>();
			
			moves.add(new Coordinates(getX(), getY() + 1));
			moves.add(new Coordinates(getX(), getY() - 1));
			moves.add(new Coordinates(getX() + 1, getY()));
			moves.add(new Coordinates(getX() - 1, getY()));
			moves.add(new Coordinates(getX() + 1, getY() + 1));
			moves.add(new Coordinates(getX() + 1, getY() - 1));
			moves.add(new Coordinates(getX() - 1, getY() + 1));
			moves.add(new Coordinates(getX() - 1, getY() - 1));
			
			return moves;
			
		}
}

package non_gui;

import java.util.ArrayList;

public class Empty extends Piece {

	public Empty(int x, int y){
		super(null, null, x, y);
	}

	protected ArrayList<Coordinates> updateMoves() {
		return null;
	}

}

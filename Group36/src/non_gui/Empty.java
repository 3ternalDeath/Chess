package non_gui;

import java.util.ArrayList;

public class Empty extends Piece {

	/**
	 * initialize an empty
	 * @param x the x position of the empty
	 * @param y the y position of the empty
	 */
	public Empty(int x, int y){
		super(x, y);
	}

	public ArrayList<Coordinates> updateMoves() {
		return null;

	}

}



package non_gui;

public class Player {
	private PieceColour colour;
	private int score;
	
	public Player(PieceColour colour, int score) {
		this.colour = colour;
		this.score = score;
	}
	
	public PieceColour getColour() {
		return colour;
	}
	
	public int getScore() {
		return score;
	}
	
	public void adjustScore(int adjustment) {
		score += adjustment;
	}
}

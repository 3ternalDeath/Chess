package non_gui;

public class Main {
	
	public static int currentTurn;
	private static Player p1;
	private static Player p2;

	public static void main(String[] args) {
		currentTurn = 1;
		p1 = new Player(PieceColour.White, 0);
		p2 = new Player(PieceColour.Black, 0);
		//create a board populated with the traditional chess arrangement
		Board board = new Board();
		board.populateBoard();
		
		//displays board then updates board according to user input
		while (true) {
			System.out.println("P1 Score: " + p1.getScore());
			System.out.println("P2 Score: " + p2.getScore());
			board.display();
			if (currentTurn % 2 == 1) {
				System.out.println(p1.getColour());
				board.update(p1);
				p1.adjustScore(board.pushScoreChange());
			}
			else {
				System.out.println(p2.getColour());
				board.update(p2);
				p2.adjustScore(board.pushScoreChange());
			}
			currentTurn++;
		}
	}
}

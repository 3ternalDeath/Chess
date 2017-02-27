package non_gui;

public class Main {
	
	private static int currentTurn;
	private static Player p1;
	private static Player p2;

	public static void main(String[] args) {
		currentTurn = 1;
		p1 = new Player(PieceColor.White, 0);
		p2 = new Player(PieceColor.Black, 0);
		//create a board populated with the traditional chess arrangement
		Board board = new Board();
		board.populateBoard();
		
		//displays board then updates board according to user input
		boolean running = true;
		while (running) {
			board.display();
			if (currentTurn % 2 == 1) {
				System.out.println(p1.getColor());
				p1.makeMove(board);
			}
			else {
				System.out.println(p2.getColor());
				p2.makeMove(board);
			}
			currentTurn++;
		}
	}
}

package non_gui;

public class Game {
	private int currentTurn;
	private Player p1;
	private Player p2;
	private Board board;
	public static boolean DEBUG = true;
	
	public Game(GameType type) {
		currentTurn = 1;
		switch (type) {
		case SINGLE:
			break;
		case MULTI:
			p1 = new Player(PieceColor.White, 0);
			p2 = new Player(PieceColor.Black, 0);
			break;
		}
		board = new Board();
	}
	
	public void run() {
		board.populateBoard();
		
		while (!board.blackLose() && !board.whiteLose()) {
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
		
		board.display();
		
		if(board.blackLose()){
			System.out.println("White has won, Black has lost");
		}
		else if(board.whiteLose()){
			System.out.println("Black has won, White has lost");
		}
	}
	
	public static void debugMsg(String msg){
		if(DEBUG)
			System.out.println(msg);
	}
}

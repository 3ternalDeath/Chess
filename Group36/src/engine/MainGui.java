package engine;

public class MainGui {
	public static void main(String[] args) {
		
		//creates instance of the game
		ChessGame game = new ChessGame();
		
		//creates window for the game
		new Window(game);
	}
}

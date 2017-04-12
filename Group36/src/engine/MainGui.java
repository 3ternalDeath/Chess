package engine;

public class MainGui {
	public static void main(String[] args) {
		//Creates instance of the game
		ChessGame game = new ChessGame();
		
		//Creates window for the game
		new Window(game);
	}
}

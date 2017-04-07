package engine;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

public class MainGui {
	public static void main(String[] args) throws FileNotFoundException {
		ChessGame game = new ChessGame();
		new Window(game);
	}
}

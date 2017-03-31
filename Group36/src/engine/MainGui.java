package engine;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

public class MainGui {
	public static void main(String[] args) throws FileNotFoundException {
		ChessGame game = new ChessGame();
		JFrame f = new JFrame();

		f.setTitle("Chess");
		f.setSize(ChessGame.WINDOW - 36, ChessGame.WINDOW);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.add(game);
	}
}

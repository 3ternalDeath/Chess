package engine;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

public class MainGui {
	public static void main(String[] args) throws FileNotFoundException {
		ChessGame t = new ChessGame();
		JFrame f = new JFrame();

		f.setTitle("Hello");
		f.setSize(ChessGame.WINDOW - 36, ChessGame.WINDOW);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.add(t);
	}
}

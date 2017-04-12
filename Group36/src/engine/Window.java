package engine;

import javax.swing.JFrame;

/**
 * A general window frame for the program.
 * @author Group 36
 */
public class Window {
	public Window(ChessGame game) {
		JFrame f = new JFrame();

		f.setTitle("Chess");
		f.setSize(ChessGame.WINDOW - 36, ChessGame.WINDOW);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		f.add(game);
	}
}

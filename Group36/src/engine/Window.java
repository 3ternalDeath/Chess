package engine;

import javax.swing.JFrame;

/**
 * A general window frame for the program.
 * @author Group 36
 */
public class Window extends JFrame{
	public Window(ChessGame game) {
		setTitle("Chess");
		setSize(ChessGame.WINDOW - 36, ChessGame.WINDOW);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		add(game);
		setLocationRelativeTo(null);
		pack();
		
		
	}
}

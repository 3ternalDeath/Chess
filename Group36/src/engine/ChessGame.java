package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 * Handles the main functionality of the chess game.
 * @author Group 36
 */
public class ChessGame extends JPanel implements ActionListener, Serializable {
	private static final long serialVersionUID = 111L;

	final static int SIZE = 8;
	final static int WINDOW = SIZE * 78;
	GridBagConstraints gbc = new GridBagConstraints();
	public static boolean debug = true;
	
	private boolean firstSec = true;
	private Coordinates init;
	private Coordinates fin;
	private boolean valid;
	private LogicHandler handler;

	Button[][] button = new Button[SIZE][SIZE];

	/**
	 * Constructor for the ChessGame class.
	 * @throws FileNotFoundException if chessboard arrangement is missing.
	 */
	public ChessGame() throws FileNotFoundException {
		setLayout(new GridBagLayout());
		try{
			handler = new LogicHandler();
			button = handler.updateButtons();
			populateWindow();
		}catch(FileNotFoundException fnfe){
			add(new JLabel("File is not there"));
		}
		
	}
	
	public void populateWindow() {
		for (int y = SIZE - 1; y >= 0; y--) {
			for (int x = 0; x < SIZE; x++) {
				// Size
				button[x][y].setPreferredSize(new Dimension(71, 71));

				// Checkered Background
				if ((x + y) % 2 == 0) button[x][y].setBackground(Color.BLACK);
				else button[x][y].setBackground(Color.WHITE);

				// Setting Icons
				button[x][y].setIcon(button[x][y].getImage());

				// Action Listener
				button[x][y].addActionListener(this);
				button[x][y].setActionCommand(x + "" + y);

				// GridBag dimensions
				gbc.gridx = x;
				gbc.gridy = SIZE - y;

				// Add Button
				add(button[x][y], gbc);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		int x = e.getActionCommand().charAt(0) - '0';
		int y = e.getActionCommand().charAt(1) - '0';

		//if button click is to choose a piece and neither player has lost
		if (firstSec && !handler.gameLost()) {
			init = new Coordinates(x, y);
			System.out.println("Piece at:" + init);
			if (handler.validInit(init)) {
				firstSec = false;	
			}
		}
		//if button click is to move the chosen piece and neither player has lost
		else if (!handler.gameLost()) {
			fin = new Coordinates(x, y);
			System.out.println("Moved to:" + fin);
			firstSec = true;
			if (handler.validFin(init, fin)) {
				valid = true;
			}
		}
		/*else {
			if (player1.isLost())
				System.out.println(player2.getColor() + " Won!!");
			else if (player2.isLost())
				System.out.println(player1.getColor() + " Won!!");
			stop();
		}*/
		// Change Icon if valid
		if (valid) {
			moveStuff(init, fin);
			valid = false;
		}
	}
	
	/**
	 * Adjusts the icons on every button in the chessboard.
	 */
	public void updateIcons() {
		for (Button[] x: button) {
			for (Button y : x) {
				y.updateIcon();
			}
		}
	}
	
	/**
	 * Displays a debugging message, if the debug switch is turned on.
	 * @param msg The message to display.
	 */
	public static void debugMsg(String msg) {
		if(debug)
			System.out.println(msg);
	}
	
	/**
	 * Shuts off all functionality to the game.
	 */
	private void stop() {
		for (int y = SIZE - 1; y >= 0; y--)
			for (int x = 0; x < SIZE; x++) 
				button[x][y].removeActionListener(this);
	}
	
	/**
	 * Moves the object from the initial coordinate to the final coordinate
	 * after performing error checking.
	 * @param init The coordinate to start at.
	 * @param fin The coordinate to end at.
	 */
	private void moveStuff(Coordinates init, Coordinates fin) {
		handler.makeMove(init, fin);
		button = handler.updateButtons();
		super.removeAll();
		setLayout(new GridBagLayout());
		populateWindow();
		super.validate();
	}
}

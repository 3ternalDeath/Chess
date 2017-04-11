package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Handles the main functionality of the chess game.
 * @author Group 36
 */
public class ChessGame extends JPanel implements ActionListener, Serializable {
	private static final long serialVersionUID = 111L;

	final static int SIZE = 8;
	final static int WINDOW = SIZE * 83;
	
	private boolean firstSec = true;
	private boolean valid;
	private Coordinates init, fin;
	private LogicHandler handler;

	GridBagConstraints gbc = new GridBagConstraints();
	Button[][] button = new Button[SIZE][SIZE];
	JButton undo, saveGame;
	static JLabel msgDisplay = new JLabel("Make a move, Player 1!", JLabel.CENTER);
	
	/**
	 * Constructor for the ChessGame class.
	 */
	public ChessGame() {
		mainMenu();
		
		undo = new JButton("Undo");
		undo.addActionListener(this);
		saveGame = new JButton("Save Game");
		saveGame.addActionListener(this);
	}
	

	/**
	 * Displays the game's start menu.
	 */
	private void mainMenu() {
		
		//Title
		gbc.anchor = GridBagConstraints.CENTER;
		msgDisplay.setText("Let's play chess!");
		add(msgDisplay, gbc);
		
		//New Game Button
		JButton temp = new JButton("New Game");
		temp.addActionListener(this);
		add(temp, gbc);
		
		//Load Game Button
		temp = new JButton("Load Game");
		temp.addActionListener(this);
		add(temp, gbc);
	}
	
	/**
	 * Listens for an action
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.length() == 2) {
			actionMove(cmd);
		}
		else if (cmd.equals("New Game")) {
			newGame();
		}
		else if (cmd.equals("Load Game")) {
			loadMenu();
		}
		else if (cmd.equals("Save Game")) {
			saveGame();
		}
		else if (cmd.equals("Undo")) {
			handler.undo();
			refresh();
		}
	}

	/**
	 * Actuates a move based on user's actions.
	 * @param move A two-character string containing the user's coordinates.
	 */
	private void actionMove(String move) {
		int x = move.charAt(0) - '0';
		int y = move.charAt(1) - '0';

		//if button click is to choose a piece and neither player has lost
		if (firstSec && !handler.gameOver()) {
			init = new Coordinates(x, y);
			if (handler.validInit(init)) {
				firstSec = false;	
			}
		}
		//if button click is to move the chosen piece and neither player has lost
		else if (!handler.gameOver()) {
			fin = new Coordinates(x, y);
			firstSec = true;
			if (handler.validFin(init, fin)) {
				valid = true;
			}
		}
		
		// move is valid
		if (valid) {
			moveStuff(init, fin);
			valid = false;
		}
		
		if (handler.gameOver()) {
			stop();
		}
	}
	
	/**
	 * Moves the object from the initial coordinate to the final coordinate
	 * after performing error checking, then runs computer move.
	 * @param init The coordinate to start at.
	 * @param fin The coordinate to end at.
	 */
	private void moveStuff(Coordinates init, Coordinates fin) {
		try {
			handler.makeMove(init, fin);
		} catch (ClassNotFoundException | IOException e) {
			msgDisplay.setText("Unknown error with standard save file. Contact system administrator.");
			e.printStackTrace();
		}
		refresh();
		handler.nextTurn();
		refresh();
	}
	
	/**
	 * Starts a new game of chess.
	 */
	private void newGame() {
		try {
			handler = new LogicHandler();
		}
		catch (FileNotFoundException fnfe) {
			msgDisplay.setText("Missing critical file.");
		}
		catch (IOException | ClassNotFoundException iocnfe) {
			msgDisplay.setText("Unknown error. Contact system administrator.");
			iocnfe.printStackTrace();
		}
		
		msgDisplay.setText("Make a move, Player 1!");
		refresh();
	}
	
	
	/**
	 * Displays the game's load menu
	 */
	private void loadMenu() {
		String file = JOptionPane.showInputDialog("Enter save filename: ", "*.dat");
		try {
			handler = new LogicHandler(file);
		}
		catch (FileNotFoundException fnfe) {
			msgDisplay.setText("File is not there.");
		}
		catch (IOException ioe) {
			msgDisplay.setText("Unknown error. Contact system administrator.");
			ioe.printStackTrace();
		}
		catch (ClassNotFoundException cne) {
			msgDisplay.setText("That is not a Chess save file.");
		}
		
		msgDisplay.setText("Make a move, Player 1!");
		refresh();
	}
	
	/**
	 * Displays the game's save menu.
	 */
	private void saveGame() {
		String file = JOptionPane.showInputDialog("Enter new save filename: ", "*.dat");
		try {
			handler.writeLogic(file);
		}
		catch (FileNotFoundException fnfe) {
			msgDisplay.setText("File could not be saved.");
		}
		catch (IOException ioe) {
			msgDisplay.setText("Unknown error. Contact system administrator.");
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Fills window with 2d array of chessboard buttons and displays game-related messages.
	 */
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
		
		//add game message JLabel
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = SIZE;
		gbc.gridx = 0;
		gbc.gridy = SIZE + 1;
		gbc.ipady = 10;
		msgDisplay.setVisible(true);
		add(msgDisplay, gbc);
		
		//add buttons
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = SIZE + 2;
		gbc.ipady = 0;
		add(undo, gbc);
		gbc.gridx = 4;
		add(saveGame, gbc);
	}
	
	/**
	 * Displays a game-related message to the user.
	 * @param message The message to display.
	 * @param realMessage Whether or not this message concerns a real action or a hypothetical one.
	 */
	public static void gameMsg(String message, boolean realMessage) {
		if (realMessage) {
			msgDisplay.setText(message);
		}
	}
	
	/**
	 * Repaints game screen.
	 */
	private void refresh() {
		button = handler.updateButtons();
		
		//removes and repaints
		removeAll();
		repaint();
		
		setLayout(new GridBagLayout());
		populateWindow();
		validate();
	}

	/**
	 * Shuts off all functionality to the game.
	 */
	private void stop() {
		for (int y = SIZE - 1; y >= 0; y--) {
			for (int x = 0; x < SIZE; x++) {
				button[x][y].removeActionListener(this);
			}
		}
	}
}

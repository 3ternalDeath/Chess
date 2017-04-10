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
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pieces.PieceColor;

/**
 * Handles the main functionality of the chess game.
 * @author Group 36
 */
public class ChessGame extends JPanel implements ActionListener, Serializable {
	private static final long serialVersionUID = 111L;

	final static int SIZE = 8;
	final static int WINDOW = SIZE * 80;
	GridBagConstraints gbc = new GridBagConstraints();
	public static boolean debug = true;
	static JLabel msgDisplay = new JLabel("Make a move, Player 1!", JLabel.CENTER);
	
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
	public ChessGame() {
		mainMenu();
		
	}
	
	private void newGameSP(){
		newGame(PlayerType.Computer);
	}
	
	private void newGame2P(){
		newGame(PlayerType.User);
	}
	
	private void newGame(PlayerType type){
		try{
			handler = new LogicHandler(type);
		}
		catch(FileNotFoundException fnfe){
			msgDisplay.setText("Missing critical file");
		}
		catch(IOException ioe){
			msgDisplay.setText("Something went wrong");
			ioe.printStackTrace();
		}
		removeAll();
		repaint();
		msgDisplay.setText("Make a move, Player 1!");
		setLayout(new GridBagLayout());
		button = handler.updateButtons();
		populateWindow();
		validate();
	}
	
	private void newGameMenu(){
		removeAll();
		repaint();
		msgDisplay.setText("What type?");
		add(msgDisplay);
		JButton temp = new JButton("Single Player");
		temp.addActionListener(this);
		add(temp);
		temp = new JButton("2 Player");
		temp.addActionListener(this);
		add(temp);
		validate();
	}
	
	private void mainMenu(){
		msgDisplay.setText("Play Some EPIK Chess bro");
		add(msgDisplay);
		JButton temp = new JButton("New Game");
		temp.addActionListener(this);
		add(temp);
		temp = new JButton("Load Game");
		temp.addActionListener(this);
		add(temp);
	}
	
	private void loadMenu(){
		String file = JOptionPane.showInputDialog("Enter Name of File: ", "Right Here");
		try{
			handler = new LogicHandler(file);
		}
		catch(FileNotFoundException fnfe){
			msgDisplay.setText("File is not there");
		}
		catch(IOException ioe){
			msgDisplay.setText("Something else went wrong");
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cne){
			msgDisplay.setText("That is not a Chess save file");
		}
		
		removeAll();
		repaint();
		msgDisplay.setText("Make a move, Player 1!");
		setLayout(new GridBagLayout());
		button = handler.updateButtons();
		populateWindow();
		validate();
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
				gbc.gridwidth = 1;
				gbc.gridx = x;
				gbc.gridy = SIZE - y;

				// Add Button
				add(button[x][y], gbc);
			}
		}
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = SIZE;
		gbc.gridx = 0;
		gbc.gridy = SIZE + 1;
		msgDisplay.setVisible(true);
		add(msgDisplay, gbc);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.length() == 2){
			actionMove(cmd);
		}
		
		else if(cmd.equals("New Game")){
			newGameMenu();
		}
		
		else if(cmd.equals("Load Game")){
			loadMenu();
		}
		
		else if(cmd.equals("Single Player")){
			newGameSP();
		}
		
		else if(cmd.equals("2 Player")){
			newGame2P();
		}
	}
	
	private void actionMove(String move){
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
		
		if(handler.gameOver()) {
			stop();
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
	 * Displays a game-related message to the user.
	 * @param message The message to display.
	 * @param color The color of the player it concerns--must be white for message to display.
	 * @param realMessage Whether or not this message concerns a real action or a hypothetical one.
	 */
	public static void gameMsg(String message, PieceColor color, boolean realMessage) {
		if(color == PieceColor.White && realMessage) {
			msgDisplay.setText(message);
		}
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
		removeAll();
		repaint();
		setLayout(new GridBagLayout());
		populateWindow();
		validate();
		pause(500);
		handler.nextTurn();
		button = handler.updateButtons();
		removeAll();
		repaint();
		setLayout(new GridBagLayout());
		populateWindow();
		validate();
	}

	private void pause(int ms){

		try {
			TimeUnit.MILLISECONDS.sleep(ms);

		} catch (InterruptedException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

}

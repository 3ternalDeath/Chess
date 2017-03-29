package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pieces.PieceColor;
import pieces.PieceType;

/**
 * Handles the main functionality of the chess game.
 * @author Group 36
 */
public class ChessGame extends JPanel implements ActionListener {

	private static final long serialVersionUID = -3433959957442938842L;

	final static int SIZE = 8;
	final static int WINDOW = SIZE * 78;
	GridBagConstraints gbc = new GridBagConstraints();
	String fileName = "standard";
	public static boolean debug = true;
	
	private boolean firstSec = true;
	private ChessLogic logic;
	private Player player1, player2;
	private Coordinates init;
	private Coordinates fin;
	private boolean valid;

	Button[][] button = new Button[SIZE][SIZE];

	/**
	 * Constructor for the ChessGame class.
	 * @throws FileNotFoundException if chessboard arrangement is missing.
	 */
	public ChessGame() throws FileNotFoundException {
		setLayout(new GridBagLayout());
		Scanner file = new Scanner(new File("src/engine/" + fileName + ".txt"));
		
		for (int y = SIZE - 1; y >= 0; y--) {
			for (int x = 0; x < SIZE; x++) {
				// new Button
				Coordinates coor = new Coordinates(x, y);
				String piece = file.next();
				button[x][y] = new Button(coor, piece);
				
				if (button[x][y].getPieceRef() != null) {
					if (button[x][y].getPieceRef().getType() == PieceType.King) {
						if (button[x][y].getPieceRef().getColor() == PieceColor.White) {
							player1 = new Player(PieceColor.White, PlayerType.User, true, coor);
						} 
						else if (button[x][y].getPieceRef().getColor() == PieceColor.Black) {
							player2 = new Player(PieceColor.Black, PlayerType.Computer, false, coor);
						}
					}
				}

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
		
		file.close();
		logic = new ChessLogic(button);// Purposeful privacy leak
	}

	public void actionPerformed(ActionEvent e) {
		int x = e.getActionCommand().charAt(0) - '0';
		int y = e.getActionCommand().charAt(1) - '0';

		//if button click is to choose a piece and neither player has lost
		if (firstSec && !player1.isLost() && !player2.isLost()) {
			init = new Coordinates(x, y);
			System.out.println("Piece at:" + init);
			firstSec = false;
		}
		//if button click is to move the chosen piece and neither player has lost
		else if (!player1.isLost() && !player2.isLost()) {
			fin = new Coordinates(x, y);
			System.out.println("Moved to:" + fin);
			firstSec = true;
			if (player1.isMyTurn()) {
				if (logic.validMove(init, fin, player1)) {
					valid = true;
				}
			} 
			else if (player2.isMyTurn()) {
				if (logic.validMove(init, fin, player2)) {
					valid = true;
				}
			}
		}
		else {
			if (player1.isLost())
				System.out.println(player2.getColor() + " Won!!");
			else if (player2.isLost())
				System.out.println(player1.getColor() + " Won!!");
			stop();
		}
		// Change Icon if valid
		if (valid) {
			moveStuff(init, fin);
			nextTurn();
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
		if (logic.getType(init) == PieceType.King) {
			if (player1.isMyTurn())
				player1.setKingCoor(fin);
			else if (player2.isMyTurn())
				player2.setKingCoor(fin);
		}
		
		logic.updateBoard(init, fin, player1, player2);
		logic.updateButton();
		updateIcons();
	}

	/**
	 * Rolls game progression over to the next turn.
	 */
	private void nextTurn() {
		player1.switchTurn();
		player2.switchTurn();

		if (player1.getType() == PlayerType.Computer && player1.isMyTurn() && !player1.isLost()) {
			compMove(player1);
			nextTurn();
		}
		else if (player2.getType() == PlayerType.Computer && player2.isMyTurn() && !player2.isLost()) {
			compMove(player2);
			nextTurn();
		}
	}

	/**
	 * Generates a random move for the computer player and executes it
	 * @param comp The computer player.
	 */
	private void compMove(Player comp) {
		boolean goodMove = false;
		do {
			Coordinates init = logic.compGetInit(comp);
			Coordinates fin = logic.compGetFin(comp, init);

			if (logic.validMove(init, fin, comp)) {
				moveStuff(init, fin);
				goodMove = true;
			}

		} while (!goodMove);
	}
}

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
	
	private boolean firstSec = true;
	private ChessLogic logic;
	private Player p1, p2;
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
							p1 = new Player(PieceColor.White, PlayerType.User, true, coor);
						} 
						else if (button[x][y].getPieceRef().getColor() == PieceColor.Black) {
							p2 = new Player(PieceColor.Black, PlayerType.Computer, false, coor);
						}
					}
				}

				// Size
				button[x][y].setPreferredSize(new Dimension(71, 71));

				// Checkered Background
				if ((x + y) % 2 == 0)
					button[x][y].setBackground(Color.BLACK);
				else
					button[x][y].setBackground(Color.WHITE);

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

		if (firstSec && !p1.isLost() && !p2.isLost()) {
			init = new Coordinates(x, y);
			System.out.println("Piece at:" + init);
			firstSec = false;
		} 
		else if (!p1.isLost() && !p2.isLost()) {
			fin = new Coordinates(x, y);
			System.out.println("Moved to:" + fin);
			firstSec = true;
			if (p1.isMyTurn()) {
				if (logic.validMove(init, fin, p1)) {
					valid = true;
				}
			} 
			else if (p2.isMyTurn()) {
				if (logic.validMove(init, fin, p2)) {
					valid = true;
				}
			}
		}
		else {
			if (p1.isLost())
				System.out.println(p2.getColor() + " Won!!");
			else if (p2.isLost())
				System.out.println(p1.getColor() + " Won!!");
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
		for (Button[] a : button) {
			for (Button i : a) {
				i.updateIcon();
			}
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
		if (logic.getType(init) == PieceType.King) {
			if (p1.isMyTurn())
				p1.setKingCoor(fin);
			else if (p2.isMyTurn())
				p2.setKingCoor(fin);
		}
		
		logic.updateBoard(init, fin, p1, p2);
		logic.updateButton();
		updateIcons();
	}

	/**
	 * Rolls game progression over to the next turn.
	 */
	private void nextTurn() {
		p1.switchTurn();
		p2.switchTurn();

		if (p1.getType() == PlayerType.Computer && p1.isMyTurn() && !p1.isLost()) {
			compMove(p1);
			nextTurn();
		}
		else if (p2.getType() == PlayerType.Computer && p2.isMyTurn() && !p2.isLost()) {
			compMove(p2);
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

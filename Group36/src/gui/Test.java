package gui;

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

import non_gui.Coordinates;
import non_gui.PlayerType;
import pieces.PieceColor;
import pieces.PieceType;

public class Test extends JPanel implements ActionListener {

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

	public Test() throws FileNotFoundException {
		setLayout(new GridBagLayout());
		Scanner file = new Scanner(new File("src/gui/" + fileName + ".txt"));
		for (int y = SIZE - 1; y >= 0; y--)
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
		file.close();
		logic = new ChessLogic(button);// Purposeful privacy leak
	}

	public void actionPerformed(ActionEvent e) {

		int x = e.getActionCommand().charAt(0) - '0';
		int y = e.getActionCommand().charAt(1) - '0';

		if (firstSec) {
			init = new Coordinates(x, y);
			System.out.println("Piece at:" + init);
			firstSec = false;
		} else {
			fin = new Coordinates(x, y);
			System.out.println("Moved to:" + fin);
			firstSec = true;
			if (p1.isMyTurn()) {
				if (logic.validMove(init, fin, p1)) {
					valid = true;
				}
			} else if (p2.isMyTurn()) {
				if (logic.validMove(init, fin, p2)) {
					valid = true;
				}
			}
		}
		// Change Icon if valid
		if (valid) {
			moveStuff(init, fin);
			nextTurn();
			valid = false;
		}
	}

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

	private void nextTurn() {
		p1.switchTurn();
		p2.switchTurn();

		if (p1.getType() == PlayerType.Computer && p1.isMyTurn()){
			compMove(p1);
			nextTurn();
		}
		else if (p2.getType() == PlayerType.Computer && p2.isMyTurn()){
			compMove(p2);
			nextTurn();
		}
	}

	private void compMove(Player comp) {
		boolean goodMove = false;
		do {
			Coordinates init = logic.compGetInit(comp);
			Coordinates fin = logic.comGetFin(comp, init);

			if (logic.validMove(init, fin, comp)) {
				moveStuff(init, fin);
				goodMove = true;
			}

		} while (!goodMove);

	}

	public void updateIcons() {
		for (Button[] a : button) {
			for (Button i : a) {
				i.updateIcon();
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Test t = new Test();
		JFrame f = new JFrame();

		f.setTitle("Hello");
		f.setSize(WINDOW - 36, WINDOW);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.add(t);

	}
}

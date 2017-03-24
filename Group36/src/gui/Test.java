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


public class Test extends JPanel implements ActionListener {

	private static final long serialVersionUID = -3433959957442938842L;
	
	final static int SIZE = 8;
	final static int WINDOW = SIZE*78;
	GridBagConstraints gbc = new GridBagConstraints();
	String fileName = "standard";
	private boolean firstSec= true;
	private Logic logic;
	private Player p1, p2;
	private Coordinates init;
	private Coordinates fin;
	private boolean valid;
	
	Button[][] button= new Button[SIZE][SIZE];
	
	public Test() throws FileNotFoundException {
		setLayout(new GridBagLayout());
		Scanner file = new Scanner(new File("src/gui/"+ fileName +".txt"));
		for(int y = SIZE-1; y >= 0; y--)
			for(int x = 0; x < SIZE; x++)
			{
				//new Button
				Coordinates coor = new Coordinates(x,y);
				String piece = file.next();
				button[x][y] = new Button(coor,piece);
				
				//Size
				button[x][y].setPreferredSize(new Dimension(71,71));
				
				//Checkered Background
				if ((x+y)%2 == 0)
					button[x][y].setBackground(Color.BLACK);
				else
					button[x][y].setBackground(Color.WHITE);
				
				//Setting Icons
				button[x][y].setIcon(button[x][y].getImage());
				
				//Action Listener
				button[x][y].addActionListener(this);
				button[x][y].setActionCommand(x+""+y);
				
				//GridBag dimensions
				gbc.gridx = x;
				gbc.gridy = SIZE-y;
				
				//Add Button
				add(button[x][y],gbc);
			}
		file.close();
		logic = new Logic(button);//Purposeful privacy leak
		p1 = new Player(PieceColor.White, PlayerType.User, true);
		p2 = new Player(PieceColor.Black, PlayerType.User, false);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		int x = e.getActionCommand().charAt(0) - '0';
		int y = e.getActionCommand().charAt(1) - '0';
		
		if (firstSec){
			init = new Coordinates(x,y);
			System.out.println("Piece at:" + init);
			firstSec = false;
		}
		else{
			fin = new Coordinates(x,y);
			System.out.println("Moved to:" + fin);
			firstSec = true;
			if(p1.isMyTurn()){
				if(logic.validMove(init, fin, p1.getColor())){
					valid = true;
				}
			}
			else if(p2.isMyTurn()){
				if(logic.validMove(init, fin, p2.getColor())){
					valid = true;
				}
			}
		}
		//Change Icon if valid
		if (valid) {
			p1.switchTurn();
			p2.switchTurn();
			logic.updateBoard(init, fin);
			logic.updateButton();
			updateIcons();
			valid = false;
		}
	}
	
	public void updateIcons(){
		for(Button[] a : button){
			for(Button i : a){
				i.updateIcon();
			}
		}
	}
	
	
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException{
		Test t = new Test();
		JFrame f = new JFrame();

		f.setTitle("Hello");
		f.setSize(WINDOW-36,WINDOW);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.add(t);
		
	}
}

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


public class Test extends JPanel implements ActionListener {

	private static final long serialVersionUID = -3433959957442938842L;
	
	final static int SIZE = 8;
	final static int WINDOW = SIZE*76;
	GridBagConstraints gbc = new GridBagConstraints();
	String fileName = "standard";
	boolean hi= true;
	
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
	
	Coordinates init;
	Coordinates fin;
	boolean valid;
	public void actionPerformed(ActionEvent e) {
		
		int x = e.getActionCommand().charAt(0) - '0';
		int y = e.getActionCommand().charAt(1) - '0';
		
		if (hi){
			init = new Coordinates(x,y);
			System.out.println("Piece at:" + init);
			hi = false;
		}
		else{
			fin = new Coordinates(x,y);
			System.out.println("Moved to:" + fin);
			hi = true;
			
			//TODO: CHECK VALID
			
			valid = true;
		}
		//Change Icon if valid
		if (valid) {
			button[fin.getX()][fin.getY()].setIcon(button[init.getX()][init.getY()].getIcon());
			button[init.getX()][init.getY()].setIcon(null);
			valid = false;
		}
	}
}

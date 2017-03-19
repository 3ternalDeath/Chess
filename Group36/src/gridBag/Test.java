package gridBag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import non_gui.Coordinates;


public class Test extends JPanel implements ActionListener {

	private static final long serialVersionUID = -3433959957442938842L;
	
	final static int SIZE = 8;
	final static int WINDOW = SIZE*75;
	GridBagConstraints gbc = new GridBagConstraints();
	boolean hi= true;
	
	Button[][] button= new Button[SIZE][SIZE];
	
	public Test() throws FileNotFoundException {
		setLayout(new GridBagLayout());
		Scanner file = new Scanner(new File("src/gridBag/standard.txt"));
		for(int y = 0; y < SIZE; y++)
			for(int x = 0; x < SIZE; x++)
			{
				//new Button
				Coordinates coor = new Coordinates(x,y);
				button[x][y] = new Button(coor);
				button[x][y].setPreferredSize(new Dimension(70,70));
				
				//Setting Icons
				String type = file.next();
				ImageIcon img;
				if (!type.equals("ee")){
					img = new ImageIcon("src/Images/"+ type +".png");
				}
				else{
					img = null;
				}
				button[x][y].setIcon(img);
				
				//ActionListener
				button[x][y].addActionListener(this);
				button[x][y].setActionCommand(x+""+y);
				
				//Checkered Background
				if ((x+y)%2 == 0){
					button[x][y].setBackground(Color.BLACK);
				}
				else{
					button[x][y].setBackground(Color.WHITE);
				}
				
				//GridBag
				gbc.gridx = x;
				gbc.gridy = SIZE-y;
				
				//Add Button
				add(button[x][y],gbc);
			}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Test t = new Test();
		JFrame f = new JFrame();

		f.setTitle("Hello");
		f.setSize(WINDOW,WINDOW);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.add(t);
		
	}
	
	Coordinates init;
	public void actionPerformed(ActionEvent e) {
		
		int x = e.getActionCommand().charAt(0)-'0';
		int y = e.getActionCommand().charAt(1)-'0';
		
		if (hi){
			System.out.println("Piece at:(" + x + ", " + y +  ")");
			hi = false;
			init = new Coordinates(x,y);
		}
		else{
			System.out.println("Moved to:(" + x + ", " + y + ")");
			hi = true;
			
			//TODO: CHECK VALID
			
			boolean valid = true;
			//Change Icon if valid
			if (valid){
				button[x][y].setIcon(button[init.getX()][init.getY()].getIcon());
				button[init.getX()][init.getY()].setIcon(null);
			}
		}
	}
}

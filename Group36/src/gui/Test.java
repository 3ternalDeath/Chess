package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Test extends JPanel implements ActionListener {

	private static final long serialVersionUID = -3433959957442938842L;
	
	final static int SIZE = 8;
	final static int WINDOW = SIZE*100;
	GridBagConstraints gbc = new GridBagConstraints();
	JButton button;
	boolean hi= true;
	
	public Test() {
		setLayout(new GridBagLayout());
		for(int x = 0; x < SIZE; x++)
			for(int y = 0; y < SIZE; y++)
			{
				
				button = new JButton(x+", "+y);
				button.addActionListener(this);
				button.setPreferredSize(new Dimension(70,70));
				if ((x+y)%2 == 0){
					button.setBackground(Color.BLACK);
					button.setForeground(Color.WHITE);
				}
				else{
					button.setBackground(Color.WHITE);
					button.setForeground(Color.BLACK);
				}
				
				
				gbc.gridx = x;
				gbc.gridy = SIZE-y;
				button.setActionCommand(x+" "+y);
				add(button,gbc);
			}
	}

	public static void main(String[] args) {
		Test t = new Test();
		JFrame f = new JFrame();

		f.setTitle("Hello");
		f.setSize(WINDOW,WINDOW);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.add(t);
		
	}
	public void actionPerformed(ActionEvent e) {
		
		char x = e.getActionCommand().charAt(0);
		char y = e.getActionCommand().charAt(2);
		
		if (hi){
			System.out.println("Piece at:(" + x + ", " + y +  ")");
			hi = false;
		}
		else{
			System.out.println("Moved to:(" + x + ", " + y + ")");
			hi = true;
		}
			
		
	}

}

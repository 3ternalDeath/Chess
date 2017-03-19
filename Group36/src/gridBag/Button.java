package gridBag;

import javax.swing.Icon;
import javax.swing.JButton;

import non_gui.Coordinates;

public class Button extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2530095940327637907L;
	
	private Coordinates coor;
	private String img;
	
	public Button(Coordinates coor){
		super();
		this.coor = new Coordinates(coor.getX(),coor.getY());
	}
	
	public Button(Coordinates coor, Icon icon){
		super(icon);
		
		this.coor = new Coordinates(coor.getX(),coor.getY());
	}
	
	public Coordinates getCoor(){
		return new Coordinates(coor.getX(),coor.getY());
	}
	
}

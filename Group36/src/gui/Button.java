package gui;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceType;
import non_gui.Coordinates;

public class Button extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2530095940327637907L;
	
	private Piece piece;
	
	//public Button(Coordinates coor, PieceColor color, PieceType type){}
	
	public Button(Coordinates coor, String string){
		super();
		PieceColor color;
		PieceType type;
		switch(string.charAt(0)){
		case'b': color = PieceColor.Black; break;
		case'w': color = PieceColor.White; break;
		default: color = null;
		}
		switch(string.charAt(1)){
		case'k': type = PieceType.King; break;
		case'n': type = PieceType.Night; break;
		case'r': type = PieceType.Rook; break;
		case'b': type = PieceType.Bishop; break;
		case'p': type = PieceType.Pawn; break;
		case'q': type = PieceType.Queen; break;
		default: type = null;
		}
		
		System.out.println(coor);
		System.out.println("Color: " + color);
		System.out.println("Type: " + type);
		
		
		piece = Piece.createPiece(coor, color, type);
	}
	
//	public Button(Coordinates coor, Icon icon){
//		super(icon);
//		
//		this.coor = new Coordinates(coor.getX(),coor.getY());
//	}
//	
	public Coordinates getCoor(){
		return piece.getCoordinates();
	}
	
	public ImageIcon getImage(){
		if (piece != null)
			return piece.getImage();
		else
			return null;
	}
	
}

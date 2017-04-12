package engine;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import pieces.Piece;
import pieces.PieceColor;

/**
 * A button representing a specific location on the chessboard.
 * @author Group 36
 */
public class Button extends JButton {
	private static final long serialVersionUID = 2530095940327637907L;
	
	private Piece piece;
	
	/**
	 * Constructor for the Button class from initial boardlayout.
	 * @param coor The coordinates of the button.
	 * @param creation The attributes of its corresponding piece.
	 */
	public Button(Coordinates coor, String piece) {
		super();
		this.piece = Piece.createPiece(coor, piece);
	}
	
	/**
	 * Constructs a button that contains a given Piece object.
	 * @param piece The Piece object for the button to hold.
	 */
	public Button(Piece piece) {
		super();
		
		if (piece != null) this.piece = piece;
		else			   this.piece = null;
	}
	
	/**
	 * Returns the image of the button's corresponding piece,
	 * if the button has a corresponding piece.
	 * @return The piece's image.
	 */
	public ImageIcon getImage() {
		
		ImageIcon img = null;
		if (piece != null) img = piece.getImage();
		return img;
	}
	
	/**
	 * Returns a new piece with the attributes of the button's corresponding piece.
	 * @return The new piece.
	 */
	public Piece getPiece() {
		/*
		 * The following code pertaining to Class and Constructor was acquired from:
		 * http://stackoverflow.com/questions/6094575/creating-an-instance-using-the-class-name-and-calling-constructor
		 * and edited to meet the needs of this
		 */
		if (piece != null) {
			Class<? extends Piece> clazz = piece.getClass();
			Constructor<? extends Piece> ctor;
			try {
				ctor = clazz.getConstructor(Coordinates.class, PieceColor.class);
				Piece object = ctor.newInstance(piece.getCoordinates(), piece.getColor());
				return object;
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				System.err.println("Something went wrong");
				e.printStackTrace();
				System.err.println("It is done going wrong");
			}
		}
		return null;
	}
}

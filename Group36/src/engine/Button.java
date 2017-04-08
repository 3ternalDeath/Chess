package engine;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceType;

/**
 * A button representing a specific location on the chessboard.
 * @author Group 36
 */
public class Button extends JButton {
	private static final long serialVersionUID = 2530095940327637907L;
	
	private Piece piece;
	
	/**
	 * Constructor for the Button class.
	 * @param coor The coordinates of the button.
	 * @param creation The attributes of its corresponding piece.
	 */
	public Button(Coordinates coor, String creation) {
		super();
		
		PieceColor color;
		PieceType type;
		switch(creation.charAt(0)) {
		case 'b': color = PieceColor.Black; break;
		case 'w': color = PieceColor.White; break;
		default: color = null;
		}
		
		switch(creation.charAt(1)) {
		case 'k': type = PieceType.King;    break;
		case 'q': type = PieceType.Queen;   break;
		case 'r': type = PieceType.Rook;    break;
		case 'n': type = PieceType.Night;   break;
		case 'b': type = PieceType.Bishop;  break;
		case 'p': type = PieceType.Pawn;    break;
		default: type = null;
		}
		
		piece = Piece.createPiece(coor, type, color,true);
	}
	
	public Button(Piece piece) {
		super();
		
		if (piece != null) {
			this.piece = piece;
		}
		else
			this.piece = null;
	}

	/**
	 * Returns the button's coordinates.
	 * @return The coordinates of the button.
	 */
	public Coordinates getCoor() {
		return piece.getCoordinates();
	}
	
	/**
	 * Returns the image of the button's corresponding piece,
	 * if the button has a corresponding piece.
	 * @return The piece's image.
	 */
	public ImageIcon getImage() {
		if (piece != null)
			return piece.getImage();
		else
			return null;
	}
	
	/**
	 * Returns a new piece with the attributes of the button's corresponding piece.
	 * @return The new piece.
	 */
	public Piece getPiece() {
		//the following code pertaining to Class and Constructor was acquired from: http://stackoverflow.com/questions/6094575/creating-an-instance-using-the-class-name-and-calling-constructor
		//and edited to meet the needs of this
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
	
	/**
	 * Sets the piece reference on the button to a new piece reference.
	 * @param piece The piece reference to use.
	 */
	public void setPieceRef(Piece piece) {
		this.piece = piece;
	}
	
	/**
	 * Updates the icon on the button, for when the piece reference is changed.
	 */
	public void updateIcon() {
		if (piece != null)
			setIcon(piece.getImage());
		else
			setIcon(null);
	}
}

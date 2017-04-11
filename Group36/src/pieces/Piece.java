package pieces;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import engine.Coordinates;

/**
 * Handles all the attributes and actions of a generic
 * piece on the chessboard.
 * @author Group 36
 */
public abstract class Piece implements Serializable{
	private static final long serialVersionUID = 411L;
	private final PieceType type;
	private final PieceColor color;
	private boolean firstMove;
	
	private Coordinates coor;
	private ImageIcon img;
		
	/**
	 * Initializes a piece with no type or color.
	 * @param coor The starting coordinates of the piece.
	 */
	public Piece(Coordinates coor) {
		type = null;
		color = null;
		this.coor = new Coordinates(coor.getX(), coor.getY());
		firstMove = false;
		img = null;
	}
	/**
	 * Constructor for the Piece class.
	 * @param coor The starting coordinates of the piece.
	 * @param type The type of the piece.
	 * @param color The color of the piece.
	 * @param isFirstMove The first move status of the piece.
	 */
	public Piece(Coordinates coor, PieceType type, PieceColor color, boolean isFirstMove) {
		this.type = type;
		this.color = color;
		this.coor = new Coordinates(coor.getX(), coor.getY());
		this.firstMove = isFirstMove;
		if (type!=null)
			img = new ImageIcon("src/Images/"+ Character.toLowerCase(color.toString().charAt(0)) + "" + Character.toLowerCase(type.toString().charAt(0)) +".png");
	}

	/**
	 * Moves piece to a given set of coordinates.
	 * No error checking is done; user must ensure validity of move before calling.
	 * @param newCoor The new coordinates of the piece.
	 */
	public void move(Coordinates newCoor) {
		if (firstMove)
			firstMove = false;
		//moves the piece
		coor.setX(newCoor.getX());
		coor.setY(newCoor.getY());
	}
	
	/**
	 * Generates and returns a string to represent a piece, as long as it is not blank.
	 */
	public String toString() {
		if (getColor() != null && getType() != null)
			return ((getColor()+"").charAt(0) + "") + ((getType()+"").charAt(0) + "") + "";
		else
			return ("  ");
	}
	
	/**
	 * Generates a new piece with the given attributes.
	 * @param coor The coordinates of the new piece.
	 * @param type The type of the new piece.
	 * @param color The color of the new piece.
	 * @param isFirstMove The first move status of the new piece.
	 * @return The generated piece.
	 */
	public static Piece createPiece(Coordinates coor, PieceType type, PieceColor color, boolean isFirstMove) {
		Piece piece;
		if (type != null)
			switch(type) {
			case King:   piece = new King   (coor, color, isFirstMove); break;
			case Night:  piece = new Night  (coor, color, isFirstMove); break;
			case Rook: 	 piece = new Rook   (coor, color, isFirstMove); break;
			case Bishop: piece = new Bishop (coor, color, isFirstMove); break;
			case Queen:  piece = new Queen  (coor, color, isFirstMove); break;
			case Pawn: 	 piece = new Pawn   (coor, color, isFirstMove); break;
			default:     piece = null;
			}

		else
			piece = null;

		return piece;
	}
	
	public static Piece createPiece(Coordinates coor, String creation) {
		PieceType type;
		switch(creation.charAt(1)) {
		case 'k': type = PieceType.King;    break;
		case 'q': type = PieceType.Queen;   break;
		case 'r': type = PieceType.Rook;    break;
		case 'n': type = PieceType.Night;   break;
		case 'b': type = PieceType.Bishop;  break;
		case 'p': type = PieceType.Pawn;    break;
		default: type = null;
		}
		
		PieceColor color;
		switch(creation.charAt(0)) {
		case 'b': color = PieceColor.Black; break;
		case 'w': color = PieceColor.White; break;
		default: color = null;
		}
		
		return createPiece(coor, type, color, true);
	}
	
	/**
	 * Checks to see if piece can make move in a void.
	 * User must still check whether move is valid in practice.
	 * @param newCoor The coordinates of the destination.
	 * @return True if move can can be made, false otherwise.
	 */
	public boolean validMove(Coordinates newCoor) {
		ArrayList<Coordinates> posMoves = getPossibleMoves();
		
		/*System.out.println(getColor() + " " + getType() + " at " + getCoordinates());
		for (Coordinates coor : posMoves){
			System.out.print(coor + ", ");
		}
		System.out.println();
		*/
		
		
		if (posMoves != null) {
			for (int index = 0; index < posMoves.size(); index++) {
				if (newCoor.equals(posMoves.get(index))) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the piece's current x value.
	 * @return The current x value.
	 */
	public int getX() {
		return coor.getX();
	}
	
	/**
	 * Returns the piece's current y value.
	 * @return The current y value.
	 */
	public int getY() {
		return coor.getY();
	}
	
	/**
	 * Returns the piece's current coordinates.
	 * @return The current coordinates.
	 */
	public Coordinates getCoordinates() {
		return new Coordinates(coor.getX(), coor.getY());
	}
	
	/**
	 * Returns the type of piece.
	 * @return The piece type.
	 */
	public PieceType getType() {
		return type;
	}
	
	/**
	 * Returns the color of the piece.
	 * @return The piece color.
	 */
	public PieceColor getColor() {
		return color;
	}
	
	/**
	 * Updates the list of possible moves, based on the piece's type.
	 * @return All moves the piece can theoretically make from its current location.
	 */
	public abstract ArrayList<Coordinates> getPossibleMoves();
	
	/**
	 * Returns whether piece has made first move.
	 * @return True is piece has not moved, false otherwise.
	 */
	public boolean isFirstMove() {
		return firstMove;
	}
	
	/**
	 * Returns an image representation of the piece.
	 * @return The picture of the piece.
	 */
	public ImageIcon getImage() {
		return img;
	}
}

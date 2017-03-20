package pieces;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import non_gui.Coordinates;

/**
 * Handles all the attributes and actions of a generic
 * piece on the chessboard.
 * @author Group 36
 */
public abstract class Piece {
	private final PieceType type;
	private final PieceColor color;
	private boolean firstMove;
	private Coordinates loco;
	private ImageIcon img;
	
	/**
	 * Constructor for the Piece class.
	 * @param type The type of the piece.
	 * @param color The color of the piece.
	 * @param x The starting x position of the piece.
	 * @param y The starting y position of the piece.
	 */
	public Piece(PieceType type, PieceColor color, int x, int y) {
		this.type = type;
		this.color = color;
		loco = new Coordinates(x,y);
		firstMove = true;
		img = new ImageIcon("src/Images/"+ Character.toLowerCase(color.toString().charAt(0)) + "" + Character.toLowerCase(type.toString().charAt(0)) +".png");
	}
	
	/**
	 * Initializes a piece with no type or color.
	 * @param x The starting x position of the piece.
	 * @param y The starting y position of the piece.
	 */
	public Piece(int x, int y) {
		type = null;
		color = null;
		loco = new Coordinates(x,y);
		firstMove = false;
		img = null;
	}

	/**
	 * Moves piece to a given set of coordinates.
	 * No error checking is done; user must ensure validity of move before calling.
	 * @param newCoor The new coordinates of the piece.
	 */
	public void move(Coordinates newCoor) {
		if(firstMove)
			firstMove = false;
		//moves the piece
		loco.setX(newCoor.getX());
		loco.setY(newCoor.getY());
	}
	
	/**
	 * Generates and returns a string to represent a piece, as long as it is not blank.
	 */
	public String toString() {
		if(getColor() != null && getType() != null)
			return ((getColor()+"").charAt(0) + "") + ((getType()+"").charAt(0) + "") + "";
		else
			return ("  ");
	}
	
	/**
	 * Returns the piece's current x value.
	 * @return The current x value.
	 */
	public int getX() {
		return loco.getX();
	}
	
	/**
	 * Returns the piece's current y value.
	 * @return The current y value.
	 */
	public int getY() {
		return loco.getY();
	}
	
	/**
	 * Returns the piece's current coordinates.
	 * @return The current coordinates.
	 */
	public Coordinates getCoordinates() {
		return new Coordinates(loco.getX(), loco.getY());
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
	 * Checks to see if piece can make move in a void.
	 * User must still check whether move is valid in practice.
	 * @param newCoor The coordinates of the destination.
	 * @return True if move can can be made, false otherwise.
	 */
	public boolean validMove(Coordinates newCoor) {
		ArrayList<Coordinates> posMoves = getPossibleMoves();
		
		if(posMoves != null) {
			
			for(int index = 0; index < posMoves.size(); index++) {
				if(newCoor.equals(posMoves.get(index))) {
					return true;
				}
			}
		}
		
		return false;
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
	
	public ImageIcon getImage(){
		return img;
	}
}

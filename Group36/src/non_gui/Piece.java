package non_gui;

import java.util.ArrayList;

public abstract class Piece {
	
	private final PieceType type;
	private final PieceColor color;
	private boolean firstMove;
	private Coordinates loco;
	
	/**
	 * initialize a piece
	 * @param type the type of the piece
	 * @param color the color of the piece
	 * @param x the x position of the piece
	 * @param y the y position of the piece
	 */
	public Piece(PieceType type, PieceColor color, int x, int y) {
		this.type = type;
		this.color = color;
		loco = new Coordinates(x,y);
		firstMove = true;
		getPosibleMoves();
	}
	
	/**
	 * initialize a piece with no type or color
	 * @param x the x position of the piece
	 * @param y the y position of the piece
	 */
	public Piece(int x, int y) {
		type = null;
		color = null;
		loco = new Coordinates(x,y);
		firstMove = false;
	}

	/**
	 * moves piece to newCoor
	 * no error checking is done, ensure validity of move before calling
	 * @param newCoor the new coordinates of the piece
	 */
	public void move(Coordinates newCoor) {
		if(firstMove)
			firstMove = false;
		//moves the piece
		loco.setX(newCoor.getX());
		loco.setY(newCoor.getY());
	}
	
	/**
	 * generates and returns a string to represent a piece as long as it is not blank
	 */
	public String toString(){
		if(getColor() != null && getType() != null)
			return ((getColor()+"").charAt(0) + "") + ((getType()+"").charAt(0) + "") + "";
		else
			return ("  ");
	}
	
	/**
	 * returns the x value
	 * @return the x value
	 */
	public int getX() {
		return loco.getX();
	}
	
	/**
	 * returns the y value
	 * @return the y value
	 */
	public int getY() {
		return loco.getY();
	}
	
	public Coordinates getCoordinates(){
		return new Coordinates(loco.getX(), loco.getY());
	}
	
	/**
	 * returns the type of piece
	 * @return the piece type
	 */
	public PieceType getType() {
		return type;
	}
	
	/**
	 * returns the color of piece
	 * @return the piece color
	 */
	public PieceColor getColor() {
		return color;
	}
	
	/**
	 * checks to see if piece can make move in a void
	 * further validity checks required
	 * @param newCoor the coordinates of the destination
	 * @return true if move can can be made, false otherwise
	 */
	public boolean validMove(Coordinates newCoor){
		
		ArrayList<Coordinates> posMoves = getPosibleMoves();
		
		if(posMoves != null){
			
			for(int index = 0; index < posMoves.size(); index++){
				if(newCoor.equals(posMoves.get(index))){
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * updates the possible moves list based on the piece type
	 * @return TODO
	 */
	public abstract ArrayList<Coordinates> getPosibleMoves();
	
	/**
	 * returns if first move
	 * @return boolean based on if it is first move
	 */
	public boolean isFirstMove() {
		return firstMove;
	}

	
}

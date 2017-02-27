package non_gui;

import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSplitPaneUI;

public abstract class Piece {
	
	private final PieceType type;
	private final PieceColor Color;
	private boolean firstMove;
	private int x;
	private int y;
	protected ArrayList<Coordinates> posMoves;
	
	/**
	 * initialize a piece
	 * @param type the type of the piece
	 * @param Color the color of the piece
	 * @param x the x position of the piece
	 * @param y the y position of the piece
	 */
	public Piece(PieceType type, PieceColor Color, int x, int y) {
		this.type = type;
		this.Color = Color;
		this.x = x;
		this.y = y;
		firstMove = true;
		updateMoves();
	}
	
	/**
	 * initialize a piece with no type or color
	 * @param x the x position of the piece
	 * @param y the y position of the piece
	 */
	public Piece(int x, int y) {
		this.type = null;
		this.Color = null;
		this.x = x;
		this.y = y;
		firstMove = false;
		updateMoves();
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
		this.x = newCoor.getX();
		this.y = newCoor.getY();
		updateMoves();
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
		return this.x;
	}
	
	/**
	 * returns the y value
	 * @return the y value
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * returns the type of piece
	 * @return the piece type
	 */
	public PieceType getType() {
		return this.type;
	}
	
	/**
	 * returns the color of piece
	 * @return the piece color
	 */
	public PieceColor getColor() {
		return this.Color;
	}
	
	/**
	 * checks to see if piece can make move in a void
	 * further validity checks required
	 * @param newCoor the coordinates of the destination
	 * @return true if move can can be made, false otherwise
	 */
	public boolean validMove(Coordinates newCoor){
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
	 */
	protected abstract void updateMoves();

	/**
	 * returns if first move
	 * @return boolean based on if it is first move
	 */
	public boolean isFirstMove() {
		return firstMove;
	}

	
}

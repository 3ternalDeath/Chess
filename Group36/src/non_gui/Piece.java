package non_gui;

import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSplitPaneUI;

public abstract class Piece {
	
	private final PieceType type;
	private final PieceColour colour;
	private int x;
	private int y;
	protected ArrayList<Coordinates> posMoves;
	
	//Constructor
	public Piece(PieceType type, PieceColour colour, int x, int y) {
		this.type = type;
		this.colour = colour;
		this.x = x;
		this.y = y;
		updateMoves();
	}

	//changes x and y values according to parameters
	public void move(Coordinates newCoor) {
		//moves the piece
		this.x = newCoor.getX();
		this.y = newCoor.getY();
		updateMoves();
	}
	
	//generates and returns a string to represent a piece as long as it is not blank
	public String toString(){
		if(getColour() != null && getType() != null)
			return ((getColour()+"").charAt(0) + "") + ((getType()+"").charAt(0) + "") + "";
		else
			return ("  ");
	}
	
	//returns the x value
	public int getX() {
		return this.x;
	}
	
	//returns the y value
	public int getY() {
		return this.y;
	}
	
	//returns the type of piece
	public PieceType getType() {
		return this.type;
	}
	
	//returns the colour of piece
	public PieceColour getColour() {
		return this.colour;
	}
	
	//returns true if move is valid, false otherwise
	public boolean validMove(Coordinates newCoor){
		for(int index = 0; index < posMoves.size(); index++){
			if(newCoor.equals(posMoves.get(index))){
				return true;
			}
		}
		
		return false;
	}
	
	protected abstract void updateMoves();
	
}

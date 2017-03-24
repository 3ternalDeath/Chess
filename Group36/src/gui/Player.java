package gui;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import non_gui.Coordinates;
import non_gui.PlayerType;
import pieces.Piece;
import pieces.PieceColor;

/**
 * Allows user to interact with and move pieces on board,
 * or to have pieces be moved by an AI.
 * @author Group 36
 */
public class Player {
	private PieceColor color;
	private PlayerType type;
	private boolean myTurn;
	private boolean inCheck;
	private boolean lost;
	 
	/**
	 * Constructor for the Player class.
	 * @param color The color of the player.
	 * @param type The type of the player.
	 */
	public Player(PieceColor color, PlayerType type, boolean first) {
		this.color = color;
		this.type = type;
		myTurn = first;
		inCheck = false;
		lost = false;
	}
	
	/**
	 * Returns the player's color.
	 * @return The color of the player.
	 */
	public PieceColor getColor() {
		return color;
	}
	
	private Coordinates pickPiece(Piece[][] board) {
		Random num = new Random();
		int x = num.nextInt(8);
		int y = num.nextInt(8);
		
		while(board[x][y].getColor() != color) {
			x = num.nextInt(8);
			y = num.nextInt(8);
		}
		
		return new Coordinates(x, y);
	}
	
	private Coordinates pickMove(Coordinates piece, Piece[][] board) {
		ArrayList<Coordinates> moves = board[piece.getX()][piece.getY()].getPossibleMoves();
		int index = new Random().nextInt(moves.size());
		return moves.get(index);
	}
	
	public PlayerType getType(){
		return type;
	}
	
	public boolean isMyTurn(){
		return myTurn;
	}
	
	public void switchTurn(){
		if(myTurn)
			myTurn = false;
		else
			myTurn = true;
	}
}

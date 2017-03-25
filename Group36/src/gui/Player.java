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
	private Coordinates kingCoor;
	private boolean inCheck;
	private boolean lost;
	 
	/**
	 * Constructor for the Player class.
	 * @param color The color of the player.
	 * @param type The type of the player.
	 */
	public Player(PieceColor color, PlayerType type, boolean first, Coordinates king) {
		this.color = color;
		this.type = type;
		myTurn = first;
		setInCheck(false);
		setLost(false);
		setKingCoor(new Coordinates(king.getX(), king.getY()));
	}
	
	public Player(Player player){
		this(player.color, player.type, player.myTurn, player.kingCoor);
	}
	
	/**
	 * Returns the player's color.
	 * @return The color of the player.
	 */
	public PieceColor getColor() {
		return color;
	}
	
	public Coordinates pickPiece(Piece[][] board) {
		Random num = new Random();
		boolean ok = false;
		int x;
		int y;
		
		 do{
			x = num.nextInt(8);
			y = num.nextInt(8);
			if(board[x][y] != null)
				if(board[x][y].getColor() == color)
					ok = true;
			
		}while(!ok);
		
		return new Coordinates(x, y);
	}
	
	public Coordinates pickMove(Coordinates piece, Piece[][] board) {
		ArrayList<Coordinates> moves = board[piece.getX()][piece.getY()].getPossibleMoves();
		if(moves.size() != 0){
			int index = new Random().nextInt(moves.size());
			return moves.get(index);
		}
		else{
			return piece;
		}
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

	public Coordinates getKingCoor() {
		return new Coordinates(kingCoor.getX(), kingCoor.getY());
	}

	public void setKingCoor(Coordinates kingCoor) {
		this.kingCoor = new Coordinates(kingCoor.getX(), kingCoor.getY());
	}

	public boolean isInCheck() {
		return inCheck;
	}

	public void setInCheck(boolean inCheck) {
		this.inCheck = inCheck;
	}

	public boolean isLost() {
		return lost;
	}

	public void setLost(boolean lost) {
		this.lost = lost;
	}
}

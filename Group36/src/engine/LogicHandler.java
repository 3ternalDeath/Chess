package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceType;

public class LogicHandler {
	private Stack<int[][]> moves;
	private Stack<Piece> deadPiece;
	private int[] castle;
	private ChessLogic logic;
	private Player currentPlayer;
	
	public LogicHandler() throws FileNotFoundException {
		moves = new Stack<int[][]>();
		deadPiece = new Stack<Piece>();
		castle = new int[2];
		castle[0] = -1;
		castle[1] = -1;
		logic = new ChessLogic();
		currentPlayer = new Player(logic.getP1());
	}
	
	/*public LogicHandler(LogicHandler copy) throws FileNotFoundException {
		this();
		for(int x = 0; x < ChessGame.SIZE; x++) {
			for(int y = 0; y < ChessGame.SIZE; y++) {
				gameBoard[x][y] = copy.getPieceAt(new Coordinates(x, y));
			}
		}
	}*/
	
	/**
	 * Constructor for the LogicHandler class that copies a 2d array of chessboard buttons.
	 * @param grid The array of buttons to copy.
	 */
	/*
	 * public LogicHandler(Button[][] grid) {
		this();
		buttons = grid;

		for (int x = 0; x < ChessGame.SIZE; x++)
			for (int y = 0; y < ChessGame.SIZE; y++)
				gameBoard[x][y] = buttons[x][y].getPieceRef();
	}
	*/
	
	public boolean validInit(Coordinates init) {
		return logic.validInit(init, currentPlayer.getColor());
	}
	
	public boolean validFin(Coordinates init, Coordinates fin) {
		return logic.validFin(init, fin, currentPlayer.getColor());
	}
	
	/**
	 * Sets the piece reference of every button in the button array to that of the
	 * corresponding piece in the piece array.
	 */
	public Button[][] updateButtons() {
		Button[][] buttons = new Button[ChessGame.SIZE][ChessGame.SIZE];
		for (int x = 0; x < ChessGame.SIZE; x++) {
			for (int y = 0; y < ChessGame.SIZE; y++) {
				buttons[x][y] = new Button(logic.getPieceAt(new Coordinates(x, y)));
			}
		}
		return buttons;
	}
	
	/**
	 * Randomly generates a set of initial coordinates for a computer player.
	 * @param comp The computer player.
	 * @return The set of initial coordinates.
	 */
	public Coordinates compGetInit(Player comp) {
		return logic.compGetInit(comp);
	}
	
	/**
	 * Randomly generates a set of final coordinates for a computer player.
	 * @param comp The computer player.
	 * @param init The corresponding set of initial coordinates.
	 * @return The set of final coordinates.
	 */
	public Coordinates compGetFin(Player comp, Coordinates init) {
		return logic.compGetFin(comp, init);
	}
	
	/**
	 * Updates the board according to a given group of coordinates.
	 * @param init The initial coordinates.
	 * @param fin The final coordinates.
	 * @param p1 The game's player 1.
	 * @param p2 The game's player 2.
	 */
	public void updateBoard(Coordinates init, Coordinates fin) {
		/*if (castleNow) {
			makeMove(new Coordinates(fin.getX() + 1, fin.getY()), new Coordinates(fin.getX() - 1, fin.getY()));
			if (castle[0] != -1) {
				castle[1] = moves.size();
			} 
			else {
				castle[0] = moves.size();
			}
			castleNow = false;
		}*/
		
		makeMove(init, fin);
		updateButtons();
		//updateCheckMate(p1, p2);
	}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             	/**
	 * Updates the entire board based on user input.
  	 * Takes the piece at initial coordinates, and moves it to final coordinates.
	 * Does not check for valid movement;
	 * user must only call update after movement has been error-checked.
	 * @param init The coordinates of the piece that is moving.
	 * @param fin The coordinates that the piece is moving to.
	 */
	public void makeMove(Coordinates init, Coordinates fin) {
		System.out.println("Selected Piece:" + logic.getType(init));
		System.out.println("Piece Color: " + logic.getColor(init));
		
		//move the specified piece to the specified location
		//add move to the moves Stack
		
		
		//undo moves
		int[][] currentMove = {{init.getX(), init.getY()}, {fin.getX(), fin.getY()}};
		moves.add(currentMove);
		deadPiece.add(logic.getPieceAt(fin));
		
		logic.movePiece(init, fin);
		
		if (currentPlayer.getColor() == PieceColor.White)
			currentPlayer = logic.getP2();
		else
			currentPlayer = logic.getP1();
	}
	
	public boolean gameLost() {
		return logic.p1Lost() && logic.p2Lost();
	}
	
	public Player getCurrentPlayer() {
		return new Player(currentPlayer);
	}
}

package engine;

import java.io.Serializable;

/**
 * Container for an x value and a y value.
 * Also performs several validation checks for a hypothetical chessboard.
 * @author Group 36
 */
public class Coordinates implements Serializable{
	private static final long serialVersionUID = 811L;
	
	private int x, y;
	private final static int SIZE = 8;

	/**
	 * Constructor for the Coordinates class.
	 * @param x The x value of the coordinates.
	 * @param y The y value of the coordinates.
	 */
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Default Constructor
	 */
	public Coordinates() {
		this(0, 0);
	}

	/**
	 * Constructor which creates a copy of coordinates.
	 * @param copy The coordinates that needs to by copied.
	 */
	public Coordinates(Coordinates copy) {
		this(copy.getX(), copy.getY());
	}

	/**
	 * Returns the x value of the coordinates.
	 * @return x The current x value.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y value of the coordinates.
	 * @return y The current y value.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Changes the value of x.
	 * @param x The new x value.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Changes the value of y.
	 * @param y The new y value.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Augments x value by a given amount.
	 * @param x The amount to add to the x value.
	 */
	public void incrementX(int x) {
		setX(this.x + x);
	}
	
	/**
	 * Augments y value by a given amount.
	 * @param y The amount to add to the y value.
	 */
	public void incrementY(int y) {
		setY(this.y + y);
	}
	
	/**
	 * Checks whether a given set of coordinates points to the same location.
	 * @param compare The set of coordinates to compare.
	 * @return True if the two coordinates are equal, false otherwise.
	 */
	public boolean equals(Coordinates compare) {
		if (compare.getX() == x && compare.getY() == y)
			return true;
		else
			return false;
	}
	
	/**
	 * Prints data from the coordinates
	 */
	public String toString() {
		return("(" + x + "," + y + ")");
	}
	
	/**
	 * Checks whether a given set of x and y values is in the bounds of a
	 * hypothetical Board object.
	 * @param x The x value to check.
	 * @param y The y value to check.
	 * @return True if both values would be in bounds, false otherwise.
	 */
	public static boolean inBound(int x, int y) {
		if (x < 0 || x >= SIZE || y < 0 || y >= SIZE)
			return false;
		else
			return true;
	}
}

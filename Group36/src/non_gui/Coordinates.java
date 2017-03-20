package non_gui;

/**
 * Container for an x value and a y value.
 * Also performs several validation checks for a hypothetical chessboard.
 * @author Group 36
 */
public class Coordinates {
	private int x;
	private int y;
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
		if(compare.getX() == x && compare.getY() == y)
			return true;
		else
			return false;
	}
	
	public String toString() {
		return("(" + x + "," + y + ")");
	}
	
	/**
	 * Checks whether a given x value is in the bounds of a hypothetical Board object.
	 * @param x The x value to check.
	 * @return True if the x value would be in bounds, false otherwise.
	 */
	public static boolean inBound(int x) {
		if(x < 0 || x >= SIZE)
			return false;
		else
			return true;
	}
	
	/**
	 * Checks whether a given set of x and y values is in the bounds of a
	 * hypothetical Board object.
	 * @param x The x value to check.
	 * @param y The y value to check.
	 * @return True if both values would be in bounds, false otherwise.
	 */
	public static boolean inBound(int x, int y) {
		if(x < 0 || x >= SIZE || y < 0 || y >= SIZE)
			return false;
		else
			return true;
	}
	
	/**
	 * Checks whether a set of letter values can translate into valid, in-bound
	 * coordinates for a hypothetical Board object.
	 * @param letter The letter coordinate to check.
	 * @param number The number coordinate to check.
	 * @return True if both values would be in bounds, false otherwise.
	 */
	public static boolean inBoundPlus(char letter, char number) {
		boolean valid = true;

		// Letter is between 'a' and 'h'
		if (letter < 'a' || letter > 'h') {
			System.out.println("Letter coordinate is out of range(a-h).");
			valid = false;
		}
		// Number is between '1' and '8'
		if (number < '1' || number > '8') {
			System.out.println("Number coordinate is out of range(1-8).");
			valid = false;
		}
		
		return valid;
	}
}

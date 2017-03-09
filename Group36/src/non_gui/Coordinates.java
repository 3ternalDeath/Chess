package non_gui;

public class Coordinates {

	private int x;
	private int y;
	private final static int SIZE = 8;

	/**
	 * constructor for coordinates
	 * @param x the x value of coordinates
	 * @param y the y value of coordinates
	 */
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * returns the x value
	 * @return x
	 */
	public int getX(){
		return x;
	}
	/**
	 * returns the y value
	 * @return y
	 */
	public int getY(){
		return y;
	}

	/**
	 * gives a new value to x
	 * @param x the new x value
	 */
	public void setX(int x){
		this.x = x;
	}
	/**
	 * gives a new value to y
	 * @param y the new y value
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * @param x
	 */
	public void incrementX(int x){
		setX(this.x + x);
	}
	
	/**
	 * @param y
	 */
	public void incrementY(int y){
		setY(this.y + y);
	}
	
	/**
	 * @param compare
	 * @return
	 */
	public boolean equals(Coordinates compare){
		if(compare.getX() == x && compare.getY() == y)
			return true;
		else
			return false;
	}
	
	public String toString(){
		return("(" + x + ", " + y + ")");
	}
	
	/**
	 * @param x
	 * @return
	 */
	public static boolean inBound(int x){
		if(x < 0 || x >= SIZE)
			return false;
		else
			return true;
	}
	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean inBound(int x, int y){
		if(x < 0 || x >= SIZE || y < 0 || y >= SIZE)
			return false;
		else
			return true;
	}
	/**
	 * @param letter
	 * @param number
	 * @return
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

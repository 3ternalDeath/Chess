package non_gui;

public class Coordinates {

	private int x;
	private int y;
	private final static int SIZE = 8;

	//Constructor
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	//Getters
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}

	//Setters
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	
	public void incrementX(int x){
		setX(this.x + x);
	}
	
	public void incrementY(int y){
		setY(this.y + y);
	}
	
	public boolean equals(Coordinates compare){
		if(compare.getX() == x && compare.getY() == y)
			return true;
		else
			return false;
	}
	
	public static boolean inBound(int x){
		if(x < 0 || x >= SIZE)
			return false;
		else
			return true;
	}
	public static boolean inBound(int x, int y){
		if(x < 0 || x >= SIZE || y < 0 || y >= SIZE)
			return false;
		else
			return true;
	}
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

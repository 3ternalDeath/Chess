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
	
	public boolean equals(Coordinates compare){
		if(compare.getX() == x && compare.getY() == y)
			return true;
		else
			return false;
	}
	
	public static boolean inBound(int x){
		if(x<0 || x > SIZE)
			return false;
		else
			return true;
	}
	public static boolean inBound(int x, int y){
		if(x < 0 || x > SIZE || y < 0 || y > SIZE)
			return false;
		else
			return true;
	}
}

package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class GameObject {

	protected int x;
	protected int y;
	protected ID id;
	protected Color color;
	protected Image image;
	
	public GameObject(int x, int y, Color color, Image image, ID id){
		this.x = x;
		this.y = y;
		this.id = id;
		this.color = color;
		this.image = image;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	//Setters
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setId(ID id){
		this.id = id;
	}
	public void setColor(Color color){
		this.color = color;
	}
	public void image(String name){
		try {
			image = ImageIO.read(new File("/Images/" + name + ".png"));
		}
		catch (IOException ex) {

		}
		this.image = image;
	}
	
	//Getters
	public int getX(int x) {
		return x;
	}
	public int getY(int y) {
		return y;
	}
	public ID getID() {
		return id;
	}
	public Color getColor() {
		return color;
	}
	
	
	

}

package deprecated;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class King extends GameObject {

	private BufferedImage image;

	public King(int x, int y, Color color, Image image) {
		super(x, y, color, image, ID.King);
	}

	public void tick() {

	}

	public void render(Graphics g) {

	}
}

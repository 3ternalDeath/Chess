package oldGui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 6691247796639148462L;

	public static final int SIZE = 80;
	public static final int WIDTH = SIZE * 8;
	public static final int HEIGHT = SIZE * 8 + 30;

	private Thread thread;
	private boolean running = false;

	private Random r;
	private Handler handler;

	public Game() {

		// Open Keyboard
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));

		// Start Window
		new Window(this, WIDTH, HEIGHT, "Window...");

		r = new Random();

	}

	// Starts the thread
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Popular Game Loop
	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render();
			}
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}

	private void tick() {
		handler.tick();
	}

	private void render() {

		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			// 3 is recommended.
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		// Background
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if ((x + y) % 2 == 0) {
					g.setColor(Color.white);
					g.fillRect(x * SIZE, y * SIZE, 80, 80);
				} else {
					g.setColor(Color.black);
					g.fillRect(x * SIZE, y * SIZE, 80, 80);
				}
			}
		}

		handler.render(g);

		g.dispose();
		bs.show();

	}

	public static void main(String[] args) {
		new Game();

	}

}

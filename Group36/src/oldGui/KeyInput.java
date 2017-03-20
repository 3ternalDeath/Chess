package oldGui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private Handler handler;

	private int lastKey;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		
		/*
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID()== ID.Player){
				//Key events for Player
				
				if(key == KeyEvent.VK_W) tempObject.setVelY(-5);
				if(key == KeyEvent.VK_S) tempObject.setVelY(5);
				
				if(key == KeyEvent.VK_A) tempObject.setVelX(-5);
				if(key == KeyEvent.VK_D) tempObject.setVelX(5);
				
				
			}
			
			if(tempObject.getID()== ID.Player2){
				//Key events for Player2
				
				if(key == KeyEvent.VK_UP) tempObject.setVelY(-5);
				if(key == KeyEvent.VK_DOWN) tempObject.setVelY(5);
				
				if(key == KeyEvent.VK_LEFT) tempObject.setVelX(-5);
				if(key == KeyEvent.VK_RIGHT) tempObject.setVelX(5);
				
				
				
			}
			
			lastKey = key;
			
		}
		*/

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		/*
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
		
			if(tempObject.getID()== ID.Player){
				//Key events for Player
				
				    if(key == KeyEvent.VK_W && lastKey != KeyEvent.VK_S) tempObject.setVelY(0);
				    if(key == KeyEvent.VK_S && lastKey != KeyEvent.VK_W) tempObject.setVelY(0);
				    if(key == KeyEvent.VK_D && lastKey != KeyEvent.VK_A) tempObject.setVelX(0);
				    if(key == KeyEvent.VK_A && lastKey != KeyEvent.VK_D) tempObject.setVelX(0);
				    

				
				
			}
			
			if(tempObject.getID()== ID.Player2){
				//Key events for Player2

				   if(key == KeyEvent.VK_UP && lastKey != KeyEvent.VK_DOWN) tempObject.setVelY(0);
				    if(key == KeyEvent.VK_DOWN && lastKey != KeyEvent.VK_UP) tempObject.setVelY(0);
				    if(key == KeyEvent.VK_LEFT && lastKey != KeyEvent.VK_RIGHT) tempObject.setVelX(0);
				    if(key == KeyEvent.VK_RIGHT && lastKey != KeyEvent.VK_LEFT) tempObject.setVelX(0);
				
				
			}
		}
		*/
	}

}

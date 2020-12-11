/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.program.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;
	
	private static final int PADDLE_WIDTH = 60;
	
	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** Runs the Breakout program. */
	public void run() {
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		this.setBackground(Color.BLACK);
		graphics.startScrin();
		graphics.setGame();
	}
	public void init(){
		addKeyListeners();
	}

	//выровнять края
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D) {
			if (paddle.getX() + PADDLE_WIDTH < WIDTH)
				paddle.move(5, 0);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A) {
			if (!(paddle.getX() < 0))
			paddle.move(-5, 0);
		}
	}	
	
	private GRect paddle = SetGame.getPaddle();
	SetGame graphics = new SetGame(this);
}

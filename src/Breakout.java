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

public class Breakout extends SetGame {
	//GRect rect = new GRect(400,400,20,20);

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;
	
	private static final int PADDLE_WIDTH = 60;
	
	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;
	
	/** Number of turns */
	private static final int NTURNS = 3;
	private static final int WIDTH = APPLICATION_WIDTH;
	/** Runs the Breakout program. */
	public void run() {
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		this.setBackground(Color.BLACK);
		super.startScrin();
		super.setGame();
		/*rect.setFilled(true);
		rect.setColor(Color.RED);
		add(rect);*/
		
	}
	

	//выровнять края
	
	//SetGame graphics = new SetGame(this);
	//private GRect paddle = super.getPaddle();
	
}

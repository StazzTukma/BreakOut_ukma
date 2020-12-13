/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Breakout extends SetGame {
	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;
	
	
	
	/** Number of turns */
	private static final int NTURNS = 3;
	private static final int WIDTH = APPLICATION_WIDTH;
	/** Runs the Breakout program. */
	
	public void run() {
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		this.setBackground(Color.BLACK);
		super.startScrin();
		super.setGame();
		super.playGame();
	}
}

import java.awt.Color;
import java.awt.event.KeyEvent;

import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class SetGame extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 5;
	private double vx = 3;
	private double vy = -3;
	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 50;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1)
			* BRICK_SEP)
			/ NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;
	
	/** Random generator */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private static final int SPEED_OF_PADDLE = 10;
	public GObject collision;
	//public GObject point;
	private GRect paddle;
	private GImage startScrin;
	private GOval ball;
	private int score;
	private GLabel score_text;

	// выровнять края
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D) {
			if (paddle.getX() + PADDLE_WIDTH< WIDTH)
				paddle.move(SPEED_OF_PADDLE, 0);
			
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A) {
			if (!(paddle.getX()<= 0))
				paddle.move(-1 * SPEED_OF_PADDLE, 0);
			
		}
	}

	public void startScrin() {
		startScrin = new GImage("startScrin.jpg", 0, 0);
		startScrin.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		add(startScrin);
		waitForClick();
		remove(startScrin);
	}

	public void setGame() {
		score = 0;
		installBrickWall();
		addHearts();
		addScore(score);
		createPaddle();
		addBall();
	}
	public void playGame() {
		moveOfBall();
		addScore(score);
	}
	

	public void installBrickWall() {
		for (int j = 1; j < NBRICK_ROWS + 1; j++) {
			for (int i = 0; i < NBRICKS_PER_ROW; i++) {
				createBrick(i, j);
			}
		}
	}

	public void createBrick(int i, int j) {
		int BRICK_X_OFFSET = WIDTH / 2 - (BRICK_WIDTH + BRICK_SEP)
				* NBRICKS_PER_ROW / 2 + BRICK_SEP / 2;
		GRect brick = new GRect(BRICK_X_OFFSET + (BRICK_WIDTH + BRICK_SEP) * i,
				BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * j, BRICK_WIDTH,
				BRICK_HEIGHT);
		brick.setFilled(true);
		setColorOfBricks(brick, j);
		add(brick);
	}

	// переписать
	public void setColorOfBricks(GRect brick, int j) {
		if (j == 1 || j == 2)
			brick.setColor(Color.RED);
		if (j == 3 || j == 4)
			brick.setColor(Color.ORANGE);
		if (j == 5 || j == 6)
			brick.setColor(Color.YELLOW);
		if (j == 7 || j == 8)
			brick.setColor(Color.GREEN);
		if (j == 9 || j == 10)
			brick.setColor(Color.CYAN);
	}

	public void addScore(int score) {
		score_text = new GLabel("Score : " + Integer.toString(score),
				WIDTH - 100, HEIGHT - 10);
		score_text.setFont("gulim-22");
		score_text.setColor(Color.WHITE);
		add(score_text);
	}

	// вниз
	public void addHearts() {
		int heartSize = 30;
		for (int i = 0; i < 3; i++) {
			GImage heart = new GImage("fullHeart.jpg", i * heartSize, HEIGHT
					- heartSize);
			heart.setSize(heartSize, heartSize);
			add(heart);
		}
	}

	public void createPaddle() {
		paddle = new GRect(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT
				- PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.lightGray);
		add(paddle);
	}

	public void addBall() {
		ball = new GOval(WIDTH / 2 - BALL_RADIUS,
				HEIGHT - PADDLE_Y_OFFSET - 30, BALL_RADIUS * 2, BALL_RADIUS * 2);
		ball.setFilled(true);
		ball.setColor(Color.WHITE);
		add(ball);
	}

	public void init() {
		addKeyListeners();
	}

	/*
*/
	public void moveOfBall() {
		while (true) {
			ball.move(vx, vy);
			GObject colliter = getCollidingObject();
			if(colliter !=null && colliter!= paddle){
			remove(colliter);
			vy=-vy;
			score++;
			remove(score_text);
			addScore(score);
			}
				
			if (ball.getX() <= 0) {
				ball.setLocation(0, ball.getY());
				vx = -vx;
			} else if (ball.getY() <= 0) {
				ball.setLocation(ball.getX(), 0);
				vy = -vy;
			} else if (ball.getX() + BALL_RADIUS * 2 >= WIDTH) {
				ball.setLocation(WIDTH - BALL_RADIUS * 2, ball.getY());
				vx = -vx;
			} else if (ball.getY() + BALL_RADIUS * 2 >= HEIGHT) {
				ball.setLocation(ball.getX(), HEIGHT - BALL_RADIUS * 2);
				vy = -vy;
			}
			if ((ball.getX() + BALL_RADIUS < paddle.getX() + PADDLE_WIDTH && 
					ball.getX() + BALL_RADIUS > paddle.getX())&& 
					ball.getY() + BALL_RADIUS * 2 >= HEIGHT - PADDLE_Y_OFFSET){
				ball.setLocation(ball.getX(),paddle.getY()-PADDLE_HEIGHT);
				vx = rgen.nextDouble(1.0, 3.0); 
				if (rgen.nextBoolean(0.5)) 
					vx =-vx;
				vy=-vy;
				
			}
			pause(10);

		}
	}
	private GObject getCollidingObject(){
		
			collision = getElementAt(ball.getX(), ball.getY());
			collision = getElementAt(ball.getX(), ball.getY()+2*BALL_RADIUS);
			collision = getElementAt(ball.getX()+2*BALL_RADIUS, ball.getY());
			collision = getElementAt(ball.getX()+2*BALL_RADIUS, ball.getY()+2*BALL_RADIUS);
			if(collision!=null){
				return collision;
				
			}
		return null;
		
	}

}

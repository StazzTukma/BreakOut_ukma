import java.awt.Color;
import java.awt.event.KeyEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class SetGame {
	
	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;
	
	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;
	
	private static GRect paddle;
	private GImage startScrin;
	private GraphicsProgram graphics;
	
	SetGame(GraphicsProgram graphics) {
		this.graphics = graphics;
	}

	public void startScrin() {
		startScrin = new GImage("startScrin.jpg", 0, 0);
		startScrin.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		graphics.add(startScrin);
		graphics.waitForClick();
		startScrin.setVisible(false);
	}
	
	public void setGame() {	
		int score = 0;
		installBrickWall();
		addHearts();
		addScore(score);
		createPaddle();
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
		graphics.add(brick);
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
		GLabel score_text = new GLabel("Score : " + Integer.toString(score),
				WIDTH - 100, 24);
		score_text.setFont("gulim-22");
		score_text.setColor(Color.WHITE);
		graphics.add(score_text);
	}
	
	public void addHearts() {
		int heartSize = 30;
		for (int i = 0; i < 3; i++) {
			GImage heart = new GImage("fullHeart.jpg", i * heartSize, 1);
			heart.setSize(heartSize, heartSize);
			graphics.add(heart);
		}
	}
	
	public void createPaddle() {
		paddle = new GRect(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT
				- PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.lightGray);
		graphics.add(paddle);
	}
	
	public static GRect getPaddle() {
		return paddle;
	}


	
}

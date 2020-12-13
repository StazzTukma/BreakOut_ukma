import java.applet.AudioClip;
import java.awt.Color;
import java.awt.event.KeyEvent;

import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.MediaTools;
import acm.util.RandomGenerator;

public class SetGame extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;
	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT - 40;

	private GOval ball;
	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 5;
	private double vx = 0.2;
	private double vy = -3;
	
	private GRect paddle;
	/** Dimensions of the paddle */
	private int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;
	private int SPEED_OF_PADDLE = 10;
	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 50;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;
	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;
	/** Separation between bricks */
	private static final int BRICK_SEP = 4;
	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1)* BRICK_SEP)/ NBRICKS_PER_ROW;
	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;
	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;
	
	/** Random generator */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private AudioClip clipOfPunch = MediaTools.loadAudioClip("soundOfPunch.wav");
	private GObject collisionObject;
	private GImage startScrin;
	private GLabel score_text;
	private GObject removedHeart;
	private int score;
	private int hearts = 3;
	private int level = 1;
	private int numberOfBricks = 0;
	private int heartSize = 30;
	
	/*****************************************************************************************************************************/
	/*****************************************************************************************************************************/
	
	public void startScrin() {
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		this.setBackground(Color.BLACK);
		startScrin = new GImage("startScreen.jpg", 0, 0);
		startScrin.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		add(startScrin);
		waitForClick();
		remove(startScrin);
	}
	
	public void setGame() {
		score = 0;
		numberOfBricks = 0;
		hearts = 3;
		
		if (paddle != null && score_text != null && ball != null) {
			remove(paddle);
			remove(ball);
			remove(score_text);
		}

		setLevelDifficulty();
		installBrickWall();
		addHearts();
		addScore(score);
		createPaddle();
		addBall();
	}

	public void playGame() {
		moveBall();
		addScore(score);
	}
	
	public void installBrickWall() {
		for (int j = 1; j < NBRICK_ROWS + 1; j++) {
			for (int i = 0; i < NBRICKS_PER_ROW; i++) {
				if(getElementAt(i, j)==null)
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
		numberOfBricks++;
	}
	
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
				WIDTH - 100, APPLICATION_HEIGHT - 10);
		score_text.setFont("gulim-22");
		score_text.setColor(Color.WHITE);
		add(score_text);
	}
	
	public void addHearts() {

		for (int i = 0; i < 3; i++) {
			GImage heart = new GImage("fullHeart.jpg", i * heartSize,
					APPLICATION_HEIGHT - heartSize);
			heart.setSize(heartSize, heartSize);
			add(heart);
		}
	}

	public void removeHeart() {
		if (hearts == 2) {
			removedHeart = getElementAt(2 * heartSize, APPLICATION_HEIGHT
					- heartSize);
			remove(removedHeart);
		}
		if (hearts == 1) {
			removedHeart = getElementAt(heartSize, APPLICATION_HEIGHT
					- heartSize);
			remove(removedHeart);
		}
		if (hearts == 0) {
			removedHeart = getElementAt(0, APPLICATION_HEIGHT - heartSize);
			remove(removedHeart);
		}
	}
	public void clearAllHearts(){
		
		removedHeart = getElementAt(2 * heartSize, APPLICATION_HEIGHT
				- heartSize);
		if(removedHeart!=null)
		remove(removedHeart);
		removedHeart = getElementAt( heartSize, APPLICATION_HEIGHT
				- heartSize);
		if(removedHeart!=null)
		remove(removedHeart);
		removedHeart = getElementAt(0, APPLICATION_HEIGHT
				- heartSize);
		if(removedHeart!=null)
		remove(removedHeart);
	}
	
	public void setLevelDifficulty() {
		if (level == 2) {
			PADDLE_WIDTH *= 0.9;
			vy *= 1.5;
			SPEED_OF_PADDLE *= 1.3;
		}
		if (level == 3) {
			PADDLE_WIDTH *= 0.9;
			vy *= 1.2;
			SPEED_OF_PADDLE *= 1.05;
		}
	}

	public void createPaddle() {
		paddle = new GRect(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT
				- PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.lightGray);
		add(paddle);
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D) {
			if (paddle.getX() + PADDLE_WIDTH < WIDTH)
				paddle.move(SPEED_OF_PADDLE, 0);

		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A) {
			if (!(paddle.getX() <= 0))
				paddle.move(-1 * SPEED_OF_PADDLE, 0);

		}
	}
	
	public void init() {
		addKeyListeners();
	}

	public void addBall() {
		ball = new GOval(WIDTH / 2 - BALL_RADIUS, HEIGHT - PADDLE_Y_OFFSET
				- 100, BALL_RADIUS * 2, BALL_RADIUS * 2);
		ball.setFilled(true);
		ball.setColor(Color.WHITE);
		add(ball);
	}
	
	public void moveBall() {
		while (true) {
			ball.move(vx, vy);
			checkForCollisions();
			endRound();
			pause(10);
		}
	}
	
	private GObject getCollidingObject() {
		collisionObject = getElementAt(ball.getX() + BALL_RADIUS * 0.5,ball.getY());
		if (collisionObject != null) {
			return collisionObject;
		}
		collisionObject = getElementAt(ball.getX() + BALL_RADIUS * 1.5,ball.getY());
		if (collisionObject != null) {
			return collisionObject;
		}
		collisionObject = getElementAt(ball.getX(), ball.getY() + BALL_RADIUS* 0.5);
		if (collisionObject != null) {
			return collisionObject;
		}
		collisionObject = getElementAt(ball.getX(), ball.getY() + BALL_RADIUS* 1.5);
		if (collisionObject != null) {
			return collisionObject;
		}
		collisionObject = getElementAt(ball.getX() + BALL_RADIUS * 0.5,ball.getY() + BALL_RADIUS * 2);
		if (collisionObject != null) {
			return collisionObject;
		}
		collisionObject = getElementAt(ball.getX() + BALL_RADIUS * 1.5,ball.getY() + BALL_RADIUS * 2);
		if (collisionObject != null) {
			return collisionObject;
		}
		collisionObject = getElementAt(ball.getX() + BALL_RADIUS * 2,ball.getY() + BALL_RADIUS * 0.5);
		if (collisionObject != null) {
			return collisionObject;
		}
		collisionObject = getElementAt(ball.getX() + BALL_RADIUS * 2,ball.getY() + BALL_RADIUS * 1.5);
		if (collisionObject != null) {
			return collisionObject;
		}

		
		return null;
	}
	
	public void checkForCollisions() {
		checkForBrick();
		checkForBorders();
		checkForBottomBorder();
		checkForPaddle();
	}

	public void checkForBrick() {
		GObject colliter = getCollidingObject();
		if (colliter != null && colliter != paddle) {
			clipOfPunch.stop();
			clipOfPunch.play();
			while(getElementAt(colliter.getX(),colliter.getY())!=null){
				remove(getElementAt(colliter.getX(),colliter.getY()));
			}
			
			vy = -vy;
			score++;
			remove(score_text);
			addScore(score);
		}
	}

	public void checkForPaddle() {
		GObject colliter = getCollidingObject();
		if (colliter == paddle) {
				clipOfPunch.stop();
				clipOfPunch.play();
				ball.setLocation(ball.getX(), paddle.getY() - PADDLE_HEIGHT);
				vx = rgen.nextDouble(1.0, 3.0);
				if (rgen.nextBoolean(0.5))
					vx = -vx;
				vy = -vy;
			
		}
	}

	public void checkForBorders() {
		if (ball.getX() <= 0) {
			ball.setLocation(0, ball.getY());
			vx = -vx;
			clipOfPunch.stop();
			clipOfPunch.play();
		} else if (ball.getY() <= 0) {
			ball.setLocation(ball.getX(), 0);
			vy = -vy;
			clipOfPunch.stop();
			clipOfPunch.play();
		} else if (ball.getX() + BALL_RADIUS * 2 >= WIDTH) {
			ball.setLocation(WIDTH - BALL_RADIUS * 2, ball.getY());
			vx = -vx;
			clipOfPunch.stop();
			clipOfPunch.play();
		}
	}

	public void checkForBottomBorder() {
		if (ball.getY() + BALL_RADIUS * 2 >= HEIGHT) {
			vx = 0.2;
			hearts--;
			if(hearts == 0){
				endLoseGame();
				return;
			}
			removeHeart();
			remove(ball);
			pause(1000);
			addBall();
			pause(1500);
		}
	}
	
	/*****************************************************************************************************************************/
	/*****************************************************************************************************************************/
//find it
	public void endRound() {
		if (numberOfBricks == score) {
			level++;
			if(level == 4){
				endWinGame();
				return;
			}
			AudioClip clipOfWin = MediaTools.loadAudioClip("winOfLevel.wav");
			clipOfWin.play();
			GImage winRound = new GImage("roundWin.png", 0, 0);
			winRound.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
			add(winRound);
			remove(ball);
			vx = 0.2;

			waitForClick();
			clipOfWin.stop();
			remove(winRound);
			clearAllHearts();
			setGame();
			
		}
	}
	
	private void endLoseGame(){
		AudioClip clipOfLose = MediaTools.loadAudioClip("loseOfLevel.wav");
		clipOfLose.play();
		GImage gameOver = new GImage("gameOver.png", 0, 0);
		gameOver.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		add(gameOver);
		setStartSetting();

		waitForClick();
		clipOfLose.stop();
		remove(gameOver);
		startScrin();
		clearAllHearts();
		setGame();
		
	}

	public void endWinGame() {
			AudioClip clipOfWin = MediaTools.loadAudioClip("winOfAll.wav");
			clipOfWin.play();
			GImage winScreen = new GImage("gameWin.png", 0, 0);
			winScreen.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
			add(winScreen);
			setStartSetting();
			
			waitForClick();
			clipOfWin.stop();
			remove(winScreen);
			startScrin();
			clearAllHearts();
			setGame();
	}
	
	private void setStartSetting(){
		level = 1;
		vy = -3;
		vx = 0.2;
		PADDLE_WIDTH = 60;
	}
}

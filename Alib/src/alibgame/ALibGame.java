package alibgame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public abstract class ALibGame extends Canvas implements Runnable {
	private int mWidth, mHeight;
	private String mTitle;
	private double mMaxUpdates = 60D;

	private JFrame frame;
	private BufferedImage image;
	private Thread thread;

	private static boolean keys[];
	private static boolean mouseButtons[];
	private static int mouseX = 0;
	private static int mouseY = 0;

	private boolean isRunning = false;

	public abstract void update();

	public abstract void draw(Graphics g);

	/**
	 * This class manages most of the game.
	 * 
	 * constructor should be called to initialize window and other game
	 * functionality.
	 * 
	 * @param width
	 *            width of the window.
	 * @param height
	 *            height of the window.
	 * @param title
	 *            title of the window.
	 */
	public ALibGame(int width, int height, String title) {
		mWidth = width;
		mHeight = height;
		mTitle = title;

		setPreferredSize(new Dimension(mWidth, mHeight));
		setMaximumSize(new Dimension(mWidth, mHeight));
		setMinimumSize(new Dimension(mWidth, mHeight));

		frame = new JFrame(mTitle);
		keys = new boolean[240];
		mouseButtons = new boolean[25];
		
		addMouseListener(mouseButton);
		addMouseMotionListener(mouseMovement);
		addKeyListener(keyboard);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(keyboard);
		frame.addWindowListener(winAdapter);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();

		image = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * starts the main thread and game loop.
	 */
	public synchronized void start() {
		isRunning = true;
		if (thread == null) {
			thread = new Thread(this, "game");
			thread.start();
		}
	}

	/**
	 * stops the game loop(should not need to be called).
	 */
	public synchronized void stop() {
		isRunning = false;
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerUpdate = 1000000000D / mMaxUpdates;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerUpdate;
			lastTime = now;

			while (delta >= 1) {
				update();
				delta -= 1;
			}

			render();

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
			}
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		image.getGraphics().fillRect(0, 0, mWidth, mHeight);

		draw(image.getGraphics());

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		g.dispose();
		bs.show();
	}

	/**
	 * @return width of the screen.
	 */
	public int getWidth() {
		return mWidth;
	}

	/**
	 * @return height of the screen.
	 */
	public int getHeight() {
		return mHeight;
	}

	/**
	 * sets the maximum amount of updates the game will do in 1 second.
	 * 
	 * @param maxUpdates
	 *            maximum updates per second.
	 */
	public void setMaxUpdates(double maxUpdates) {
		mMaxUpdates = maxUpdates;
	}

	/**
	 * returns the status of whether or not a certain key has been pressed.
	 * 
	 * @param key
	 *            what key you would like to know the status of.
	 * @return true if key pressed false if not.
	 */
	public static boolean isKeyPressed(int key) {
		return keys[key];
	}
	
	/**
	 * returns the status of whether or not a certain mouse button has been pressed.
	 * 
	 * @param button what button you would like to know the status of.
	 * @return true if button pressed false if not.
	 */
	public static boolean isMouseButtonPressed(int button) {
		return mouseButtons[button];
	}
	
	/**
	 * @return the mouse x position
	 */
	public static int getMouseX(){
		return mouseX;
	}
	
	/**
	 * @return the mouse y position
	 */
	public static int getMouseY(){
		return mouseY;
	}

	private KeyAdapter keyboard = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("key pressed");
			keys[e.getKeyCode()] = true;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			keys[e.getKeyCode()] = false;
		}
	};

	private MouseAdapter mouseButton = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			mouseButtons[e.getButton()] = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mouseButtons[e.getButton()] = false;
		}
	};
	
	private MouseMotionAdapter mouseMovement = new MouseMotionAdapter() {
		@Override
		public void mouseDragged(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}
	};
	
	private WindowAdapter winAdapter = new WindowAdapter() {

		@Override
		public void windowDeactivated(WindowEvent e) {
			keys = new boolean[240];
			mouseButtons = new boolean[25];
			System.out.println("window deactivated");
		}

		@Override
		public void windowIconified(WindowEvent e) {
			keys = new boolean[240];
			mouseButtons = new boolean[25];
		}

		@Override
		public void windowLostFocus(WindowEvent e) {
			keys = new boolean[240];
			mouseButtons = new boolean[25];
			System.out.println("window lost focus");
		}
		
	};
}
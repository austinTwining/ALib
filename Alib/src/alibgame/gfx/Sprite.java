package alibgame.gfx;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Sprite {
	private double x = 0, y = 0;
	private int mWidth, mHeight;
	private BufferedImage mImage;
	
	/**
	 * Sprite has more functionality than plain BufferedImage.
	 * @param image image the sprite will use.
	 * @param width width of sprite.
	 * @param height height of sprite.
	 */
	public Sprite(BufferedImage image, int width, int height){
		mImage = image;
		mWidth = width;
		mHeight = height;
	}
	
	/**
	 * draws the sprite on the given graphics object.
	 * @param g graphics object to be drawn to.
	 */
	public void draw(Graphics g){
		g.drawImage(mImage,(int) x,(int) y, null);
	}
	
	public Rectangle getCollisionBox(){
		return new Rectangle((int)x, (int)y, mWidth, mHeight);
	}
	
	/**
	 * sets both x and y position of sprite.
	 * @param x x position of sprite.
	 * @param y y position of sprite.
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return current x location of sprite.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @param x x position of sprite.
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * @return current y location of sprite.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * @param y y position of sprite.
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * @return width of sprite.
	 */
	public int getWidth() {
		return mWidth;
	}

	/**
	 * @return height of sprite.
	 */
	public int getHeight() {
		return mHeight;
	}
}

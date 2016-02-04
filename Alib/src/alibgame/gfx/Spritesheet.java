package alibgame.gfx;

import java.awt.image.BufferedImage;

public class Spritesheet {
	
	private BufferedImage[][] images;
	
	/**
	 * allows for splitting one big sprite sheet into
	 * its individual sprites.
	 * @param image original image to be split.
	 * @param subImageWidth width of each individual sub image.
	 * @param subImageHeight height of each individual sub image.
	 * @param numRows number of images in the y-axis
	 * @param numCols number of images in the x-axis
	 */
	public Spritesheet(BufferedImage image, int subImageWidth, int subImageHeight, int numRows, int numCols){
		images = new BufferedImage[numRows][numCols];
		split(image, subImageWidth, subImageHeight, numRows, numCols);
	}
	
	private void split(BufferedImage image, int width, int height, int numRows, int numCols){
		for(int y = 0; y < height/numRows; y++){
			for(int x = 0; x < width/numCols; x++){
				images[x][y] = image.getSubimage(x*width, y*width, width, height);
			}
		}
	}
	
	/**
	 * gets an individual image from the sprite sheet.
	 * @param x x location of sprite on sheet.
	 * @param y y location of sprite on sheet.
	 * @return the image.
	 */
	public BufferedImage getImage(int x, int y){
		return images[x][y];
	}

}

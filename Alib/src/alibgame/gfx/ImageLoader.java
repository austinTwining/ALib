package alibgame.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	private Class mRoot;
	
	/**
	 * ImageLoader class allows for loading images easily.
	 * @param root reference to the main class.
	 */
	public ImageLoader(Class root){
		mRoot = root;
	}
	
	/**
	 * loads an image from given path and returns it as a BufferedImage.
	 * @param path path to image you would like loaded in.
	 * @return image loaded in as BufferedImage.
	 */
	public BufferedImage loadImage(String path){
		try {
			BufferedImage image = ImageIO.read(mRoot.getResourceAsStream(path));
			return image;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}

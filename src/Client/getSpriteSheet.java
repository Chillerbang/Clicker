package Client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/*
 * Loads a buffered image from an image file 
 * this image loaded is a smaller part of the sprite sheet 
 */

public class getSpriteSheet {
	BufferedImage SpriteSheetSprut = null;
	
	public BufferedImage getSpriteSheetSprut() {
		return SpriteSheetSprut;
	}

	public getSpriteSheet(File FileLocation){
		try {
			LoadImage(FileLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage GetSprite(int x, int y, int width, int height){
		BufferedImage Sprte = SpriteSheetSprut.getSubimage(x, y, width, height);
		return Sprte;
	}

	public void LoadImage(File f) throws IOException {
		SpriteSheetSprut = ImageIO.read(f);
	}
}


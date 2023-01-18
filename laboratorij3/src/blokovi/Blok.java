package blokovi;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Blok {
	
	public BufferedImage slikaBloka;
	public boolean kolizija;
	
	public Blok(String file, boolean kolizija) {
		this.kolizija = kolizija;
		try {
			slikaBloka = ImageIO.read(getClass().getResourceAsStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

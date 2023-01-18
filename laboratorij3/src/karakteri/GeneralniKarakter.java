package karakteri;

import java.awt.Graphics2D;
import java.awt.Rectangle;


public abstract class GeneralniKarakter {
	
	public int pozicijaNaSvijetuX;
	public int pozicijaNaSvijetuY;
	
	public int pozicijaNaEkranuX;
	public int pozicijaNaEkranuY;
	
	public int brzinaKaraktera;
	public int smjer;
	public boolean jeIgrac;
	
	public Rectangle prostorCrtanja;
	
	public GeneralniKarakter() {
		this.prostorCrtanja = new Rectangle(8, 16, 32, 32);
		this.smjer = -1;
		this.jeIgrac = false;
	}
	
	public abstract void update();
	public abstract void loadResources();
	public abstract void draw(Graphics2D g2);

}

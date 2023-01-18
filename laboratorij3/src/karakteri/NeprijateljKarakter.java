package karakteri;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NeprijateljKarakter extends GeneralniKarakter implements Comparable<NeprijateljKarakter>{

	private GamePanel gamePanel;

	public BufferedImage slika;

	public boolean jeLiUEkranu = true;

	public NeprijateljKarakter(GamePanel gp, int pocetniX, int pocetniY) {

		this.gamePanel = gp;

		this.pozicijaNaSvijetuX = gamePanel.getpostavkeEkrana().velicinaBloka * pocetniX;
		this.pozicijaNaSvijetuY = gamePanel.getpostavkeEkrana().velicinaBloka * pocetniY;

		this.brzinaKaraktera = 7;

		this.pozicijaNaEkranuX = this.pozicijaNaSvijetuX +  gamePanel.igracevKarakter.pozicijaNaEkranuX - gamePanel.igracevKarakter.pozicijaNaSvijetuX;
		this.pozicijaNaEkranuY = this.pozicijaNaSvijetuY + gamePanel.igracevKarakter.pozicijaNaEkranuY - gamePanel.igracevKarakter.pozicijaNaSvijetuY;
		loadResources();
	}

	@Override
	public void update() {

		// pomakni neprijatelja
		if (!gamePanel.suceljeZaProvjeruSudara.provjeriBlokZaSudar(this) && !gamePanel.suceljeZaProvjeruSudara.provjeriSudarKaraktera(this)) {
			switch(this.smjer) {
			case -1:
				this.pozicijaNaSvijetuY+=this.brzinaKaraktera;
				break;
			case 1:
				this.pozicijaNaSvijetuY-=this.brzinaKaraktera;
				break;
			case -2:
				this.pozicijaNaSvijetuX-=this.brzinaKaraktera;
				break;
			case 2:
				this.pozicijaNaSvijetuX+=this.brzinaKaraktera;
				break;
			}
		} else {
			int rng = 0;
			while (rng == 0) {
				rng = GamePanel.dobijRandomBroj(-3, 3);
			}
			this.smjer = rng;
		}

		
		// ako je u ekranu postaviZastavicu
		if (this.pozicijaNaSvijetuX + gamePanel.getpostavkeEkrana().velicinaBloka > gamePanel.igracevKarakter.pozicijaNaSvijetuX - gamePanel.igracevKarakter.pozicijaNaEkranuX 
				&& this.pozicijaNaSvijetuX - gamePanel.getpostavkeEkrana().velicinaBloka < gamePanel.igracevKarakter.pozicijaNaSvijetuX + gamePanel.igracevKarakter.pozicijaNaEkranuX
				&& this.pozicijaNaSvijetuY + gamePanel.getpostavkeEkrana().velicinaBloka > gamePanel.igracevKarakter.pozicijaNaSvijetuY - gamePanel.igracevKarakter.pozicijaNaEkranuY
				&& this.pozicijaNaSvijetuY - gamePanel.getpostavkeEkrana().velicinaBloka < gamePanel.igracevKarakter.pozicijaNaSvijetuY + gamePanel.igracevKarakter.pozicijaNaEkranuY) {

			this.jeLiUEkranu = true;
			this.pozicijaNaEkranuX = this.pozicijaNaSvijetuX +  gamePanel.igracevKarakter.pozicijaNaEkranuX - gamePanel.igracevKarakter.pozicijaNaSvijetuX;
			this.pozicijaNaEkranuY = this.pozicijaNaSvijetuY + gamePanel.igracevKarakter.pozicijaNaEkranuY - gamePanel.igracevKarakter.pozicijaNaSvijetuY;
		} else {
			this.jeLiUEkranu = false;
		}
	}

	@Override
	public void loadResources() {
		try {
			this.slika = ImageIO.read(getClass().getResourceAsStream("/karakteri/neprijatelj.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		if (jeLiUEkranu) {
			g2.drawImage(this.slika, pozicijaNaEkranuX, pozicijaNaEkranuY, gamePanel.getpostavkeEkrana().velicinaBloka, gamePanel.getpostavkeEkrana().velicinaBloka, null);
			/*g2.setColor(Color.BLUE);
			g2.drawRect(pozicijaNaEkranuX + prostorCrtanja.x,
					pozicijaNaEkranuY + prostorCrtanja.y, 
					prostorCrtanja.width, 
					prostorCrtanja.height);*/
		}

	}

	@Override
	public int compareTo(NeprijateljKarakter o) {
		if (this.pozicijaNaSvijetuY > o.pozicijaNaSvijetuY) {
			return -1;
		} else if (this.pozicijaNaSvijetuY < o.pozicijaNaSvijetuY) {
			return +1;
		} else {
			return 0;
		}

	}

}

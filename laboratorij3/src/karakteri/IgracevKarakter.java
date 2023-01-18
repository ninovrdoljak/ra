package karakteri;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UpravljacTipkovnice;


public class IgracevKarakter extends GeneralniKarakter {
	
	private GamePanel gamePanel;
	private UpravljacTipkovnice upravljacTipkovnice;
	
	public BufferedImage slikaGore1;
	public BufferedImage slikaGore2;
	
	public BufferedImage slikaDolje1;
	public BufferedImage slikaDolje2;
	
	public BufferedImage slikaLijevo1;
	public BufferedImage slikaLijevo2;
	
	public BufferedImage slikaDesno1;
	public BufferedImage slikaDesno2;
	
	private int trBrojac;
	private int brojac;
	
	private boolean usudaruje;
	
	public IgracevKarakter(GamePanel gp, UpravljacTipkovnice upravljacTipkovnice) {
		this.gamePanel = gp;
		this.upravljacTipkovnice = upravljacTipkovnice;
		
		this.pozicijaNaEkranuX = (int)(gp.getpostavkeEkrana().sirinaEkrana/2 - gp.getpostavkeEkrana().velicinaBloka/2);
		this.pozicijaNaEkranuY = (int)(gp.getpostavkeEkrana().visinaEkrana/2- gp.getpostavkeEkrana().velicinaBloka/2);
		
		this.trBrojac = 1;
		this.brojac = 0;
		this.jeIgrac = true;
		this.usudaruje = false;
		
		this.brzinaKaraktera = 6;
		
		this.pozicijaNaSvijetuX = gamePanel.getpostavkeEkrana().velicinaBloka*7;
		this.pozicijaNaSvijetuY = gamePanel.getpostavkeEkrana().velicinaBloka*10;
		
		loadResources();
	}
	
	@Override
	public void loadResources() {
		try {
			this.slikaGore1 = ImageIO.read(getClass().getResourceAsStream("/karakteri/otragaDesna.png"));
			this.slikaGore2 = ImageIO.read(getClass().getResourceAsStream("/karakteri/otragaLijeva.png"));
			
			this.slikaDolje1 = ImageIO.read(getClass().getResourceAsStream("/karakteri/naprijedDesna.png"));
			this.slikaDolje2 = ImageIO.read(getClass().getResourceAsStream("/karakteri/naprijedLijeva.png"));
			
			this.slikaLijevo1 = ImageIO.read(getClass().getResourceAsStream("/karakteri/lijevoDesna.png"));
			this.slikaLijevo2 = ImageIO.read(getClass().getResourceAsStream("/karakteri/lijevoLijeva.png"));
			
			this.slikaDesno1 = ImageIO.read(getClass().getResourceAsStream("/karakteri/desnoDesna.png"));
			this.slikaDesno2 = ImageIO.read(getClass().getResourceAsStream("/karakteri/desnoLijeva.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		usudaruje = gamePanel.suceljeZaProvjeruSudara.provjeriSudarKaraktera(this);
		
		if (upravljacTipkovnice.pritisnutGumbDolje || upravljacTipkovnice.pritisnutGumbGore || upravljacTipkovnice.pritisnutGumbLijevo || upravljacTipkovnice.pritisnutGumbDesno) {
			if (upravljacTipkovnice.pritisnutGumbDolje) {
				this.smjer = -1;
				
			} else if (upravljacTipkovnice.pritisnutGumbGore) {
				this.smjer = 1;
				
			} else if (upravljacTipkovnice.pritisnutGumbLijevo) {
				this.smjer = -2;
				
			} else if (upravljacTipkovnice.pritisnutGumbDesno) {
				this.smjer = 2;
			}
			
			
			if (!gamePanel.suceljeZaProvjeruSudara.provjeriBlokZaSudar(this) && !usudaruje) {
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
			}
			
			
			brojac++;
			if (brojac > 10) {
				this.trBrojac = this.trBrojac == 1 ? 2 : 1;
				brojac = 0;
			}
		}
		
	}

	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		switch(this.smjer) {
		case -1:
			image = (trBrojac == 1 ? slikaDolje1 : slikaDolje2);
			break;
		case 1:
			image = (trBrojac == 1 ? slikaGore1 : slikaGore2);
			break;
		case -2:
			image = (trBrojac == 1 ? slikaLijevo1 : slikaLijevo2);
			break;
		case 2:
			image = (trBrojac == 1 ? slikaDesno1 : slikaDesno2);
			break;
		}
		g2.drawImage(image, pozicijaNaEkranuX, pozicijaNaEkranuY, gamePanel.getpostavkeEkrana().velicinaBloka, gamePanel.getpostavkeEkrana().velicinaBloka, null);
	}
}

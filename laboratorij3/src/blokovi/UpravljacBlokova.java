package blokovi;

import java.awt.Graphics2D;

import main.GamePanel;
import prikaz.Map;

public class UpravljacBlokova {

	private GamePanel gamePanel;
	public Blok[] blokoviPolje;
	private Map mapa;
	private int velicinaBloka;
	public int[][] polje;
	
	public UpravljacBlokova(GamePanel gp, String mapName) {
		this.gamePanel = gp;
		
		this.blokoviPolje = new Blok[9];
		
		blokoviPolje[0] = new Blok("/blokovi/pod1.png", false);
		
		blokoviPolje[1] = new Blok("/blokovi/zid1.png", true);
		blokoviPolje[2] = new Blok("/blokovi/staklo1.png", true);
		
		blokoviPolje[3] = new Blok("/blokovi/drvo1.png", false);
		blokoviPolje[4] = new Blok("/blokovi/pijesak1.png", false);
		
		blokoviPolje[5] = new Blok("/blokovi/grm1.png", true);
		
		blokoviPolje[6] = new Blok("/blokovi/put1.png", false);
		blokoviPolje[7] = new Blok("/blokovi/trava1.png", false);
		
		blokoviPolje[8] = new Blok("/blokovi/voda1.png", true);
		
		this.mapa = new Map(mapName);
		
		this.velicinaBloka = gamePanel.getpostavkeEkrana().velicinaBloka;
		this.polje = mapa.getMap();
		
		
	}
	
	public void draw(Graphics2D g2) {
		int vrstaBloka = 0;
		
		int trEkranX = 0;
		int trEkranY = 0;
		
		for (int svijetRedak = 0; svijetRedak < mapa.maxRedakMape; svijetRedak++) {
			for (int svijetStupac = 0; svijetStupac < mapa.maxStupacMape; svijetStupac++) {
				
				vrstaBloka = polje[svijetStupac][svijetRedak];
				
				trEkranX = svijetStupac * velicinaBloka + gamePanel.igracevKarakter.pozicijaNaEkranuX - gamePanel.igracevKarakter.pozicijaNaSvijetuX;
				trEkranY = svijetRedak * velicinaBloka + gamePanel.igracevKarakter.pozicijaNaEkranuY - gamePanel.igracevKarakter.pozicijaNaSvijetuY;
				
				// ako je u vidljivosti
				if (svijetStupac * velicinaBloka + velicinaBloka > gamePanel.igracevKarakter.pozicijaNaSvijetuX - gamePanel.igracevKarakter.pozicijaNaEkranuX
						&& svijetStupac * velicinaBloka - velicinaBloka < gamePanel.igracevKarakter.pozicijaNaSvijetuX + gamePanel.igracevKarakter.pozicijaNaEkranuX
						&& svijetRedak * velicinaBloka + velicinaBloka > gamePanel.igracevKarakter.pozicijaNaSvijetuY - gamePanel.igracevKarakter.pozicijaNaEkranuY
						&& svijetRedak * velicinaBloka - velicinaBloka < gamePanel.igracevKarakter.pozicijaNaSvijetuY + gamePanel.igracevKarakter.pozicijaNaEkranuY)
				g2.drawImage(blokoviPolje[vrstaBloka].slikaBloka, trEkranX, trEkranY, velicinaBloka, velicinaBloka, null);
			}
		}
	}
}

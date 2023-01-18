package prikaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


public class PostavkeEkrana {
	
	public final int velicinaBloka =  3 * 16;  // 3 * 16
	
	public final int maxEkranStupac = 16;
	public final int maxEkranRedak = 12;
	
	public final int sirinaEkrana = velicinaBloka * maxEkranStupac; //768
	public final int visinaEkrana = velicinaBloka * maxEkranRedak; //576
	
	public final Font endScreenFont = new Font("Verdana", Font.BOLD, 40);
	public final Font vrijemeFont = new Font("Verdana", Font.BOLD, 30);
	
	
	public void nacrtajEndScreen(Graphics2D g2) {
		g2.setFont(endScreenFont);
		g2.setColor(Color.BLACK);
		g2.drawString("GAME",sirinaEkrana/2 - endScreenFont.getSize(), visinaEkrana/2 - endScreenFont.getSize()/2 - endScreenFont.getSize());
		g2.drawString("OVER",sirinaEkrana/2 - endScreenFont.getSize(), visinaEkrana/2 - endScreenFont.getSize()/2);
		g2.setFont(vrijemeFont);
		g2.drawString("Restart: R",sirinaEkrana/2 - endScreenFont.getSize() - endScreenFont.getSize()/2, visinaEkrana/2 + endScreenFont.getSize()/2);
	}
	
	public void nacrtajVrijemePrezivljavanja(LocalTime pocetno, LocalTime trenutno, Graphics2D g2) {
		g2.setFont(vrijemeFont);
		g2.fillRect(0, 0, 300, 50 - 10);
		g2.setColor(Color.WHITE);
		long seconds = pocetno.until( trenutno, ChronoUnit.SECONDS );
		g2.drawString("Preživio: " + seconds + "s",50 - 15, 50 - 15); 
		
		
	}
	
}

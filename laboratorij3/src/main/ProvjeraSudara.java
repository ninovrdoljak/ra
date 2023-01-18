package main;

import java.util.List;

import blokovi.UpravljacBlokova;
import karakteri.GeneralniKarakter;
import karakteri.NeprijateljKarakter;

public class ProvjeraSudara {

	private GamePanel gamePanel;
	private UpravljacBlokova upravljacBlokova;

	public ProvjeraSudara(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		this.upravljacBlokova = gamePanel.getupravljacBlokova();
	}

	// kopiran kod iz Rectangle.intersects
	public static boolean jesiLiuSudaruKarakteri(GeneralniKarakter karakter1, GeneralniKarakter karakter2) {
		
		int tw = karakter1.prostorCrtanja.width;
		int th = karakter1.prostorCrtanja.height;
		int rw = karakter2.prostorCrtanja.width;
		int rh = karakter2.prostorCrtanja.height;
		
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		int tx = karakter1.prostorCrtanja.x + karakter1.pozicijaNaSvijetuX;
		int ty = karakter1.prostorCrtanja.y + karakter1.pozicijaNaSvijetuY;
		int rx = karakter2.prostorCrtanja.x + karakter2.pozicijaNaSvijetuX;
		int ry = karakter2.prostorCrtanja.y + karakter2.pozicijaNaSvijetuY;
		
		
		switch(karakter1.smjer) {
		case 1:
			ty -= karakter1.brzinaKaraktera;
			break;
		case -1:
			ty += karakter1.brzinaKaraktera;
			break;
		case -2:
			tx -= karakter1.brzinaKaraktera;
			break;
		case 2:
			tx += karakter1.brzinaKaraktera;
			break;
		}
		
		switch(karakter2.smjer) {
		case 1:
			ry -= karakter2.brzinaKaraktera;
			break;
		case -1:
			ry += karakter2.brzinaKaraktera;
			break;
		case -2:
			rx -= karakter2.brzinaKaraktera;
			break;
		case 2:
			rx += karakter2.brzinaKaraktera;
			break;
		}
		
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		
		
		
		//      overflow || intersect
		return ((rw < rx || rw > tx) &&
				(rh < ry || rh > ty) &&
				(tw < tx || tw > rx) &&
				(th < ty || th > ry));

	}


	public boolean provjeriSudarKaraktera(GeneralniKarakter karakter) {
		
		List<NeprijateljKarakter> listaNeprijatelja = gamePanel.getNeprijatelji();
		
		for (int i = 0; i < listaNeprijatelja.size(); i++) {
			NeprijateljKarakter neprijatelj = listaNeprijatelja.get(i);
			if (jesiLiuSudaruKarakteri(karakter, neprijatelj) && karakter != neprijatelj) {
				
				if (karakter.jeIgrac) {
					gamePanel.setIgraTraje(false);
				}
				
				return true;
			}
		}
		return false;

	}

	public boolean provjeriBlokZaSudar(GeneralniKarakter karakter) {
		
		// pravokutnik pravci
		int lijeviX = karakter.pozicijaNaSvijetuX + karakter.prostorCrtanja.x;
		int desniX = karakter.pozicijaNaSvijetuX + karakter.prostorCrtanja.x + karakter.prostorCrtanja.width;
		int gornjiY = karakter.pozicijaNaSvijetuY + karakter.prostorCrtanja.y;
		int donjiY = karakter.pozicijaNaSvijetuY + karakter.prostorCrtanja.y + karakter.prostorCrtanja.height;

		// stupci/redovi
		int lijeviStupac = (int)lijeviX/gamePanel.getpostavkeEkrana().velicinaBloka;
		int desniStupac = (int)desniX/gamePanel.getpostavkeEkrana().velicinaBloka;
		int gornjiRedak = (int)gornjiY/gamePanel.getpostavkeEkrana().velicinaBloka;
		int donjiRedak = (int)donjiY/gamePanel.getpostavkeEkrana().velicinaBloka;

		int blok1; 
		int blok2;

		switch(karakter.smjer) {
		
		// prema gore
		case 1:
			gornjiRedak = (gornjiY - karakter.brzinaKaraktera)/gamePanel.getpostavkeEkrana().velicinaBloka;
			blok1 = upravljacBlokova.polje[lijeviStupac][gornjiRedak];
			blok2 = upravljacBlokova.polje[desniStupac][gornjiRedak];
			if (upravljacBlokova.blokoviPolje[blok1].kolizija || upravljacBlokova.blokoviPolje[blok2].kolizija) {
				return true;
			}
			break;
			
		// prema dolje
		case -1:
			donjiRedak = (donjiY + karakter.brzinaKaraktera)/gamePanel.getpostavkeEkrana().velicinaBloka;
			blok1 = upravljacBlokova.polje[lijeviStupac][donjiRedak];
			blok2 = upravljacBlokova.polje[desniStupac][donjiRedak];
			if (upravljacBlokova.blokoviPolje[blok1].kolizija || upravljacBlokova.blokoviPolje[blok2].kolizija) {
				return true;
			}
			break;
			
		// prema lijevo
		case -2:
			lijeviStupac = (lijeviX - karakter.brzinaKaraktera)/gamePanel.getpostavkeEkrana().velicinaBloka;
			blok1 = upravljacBlokova.polje[lijeviStupac][gornjiRedak];
			blok2 = upravljacBlokova.polje[lijeviStupac][donjiRedak];
			if (upravljacBlokova.blokoviPolje[blok1].kolizija || upravljacBlokova.blokoviPolje[blok2].kolizija) {
				return true;
			}
			break;
			
		// prema desno
		case 2:
			desniStupac = (desniX + karakter.brzinaKaraktera)/gamePanel.getpostavkeEkrana().velicinaBloka;
			blok1 = upravljacBlokova.polje[desniStupac][gornjiRedak];
			blok2 = upravljacBlokova.polje[desniStupac][donjiRedak];
			if (upravljacBlokova.blokoviPolje[blok1].kolizija || upravljacBlokova.blokoviPolje[blok2].kolizija) {
				return true;
			}
			break;
		}
		return false;

	}


}

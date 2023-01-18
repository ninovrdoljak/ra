package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import blokovi.UpravljacBlokova;
import karakteri.IgracevKarakter;
import karakteri.NeprijateljKarakter;
import prikaz.PostavkeEkrana;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	private PostavkeEkrana postavkeEkrana;
	private UpravljacTipkovnice upravljacTipkovnice;
	private Thread dretvaIgre;
	private UpravljacBlokova upravljacBlokova;
	public IgracevKarakter igracevKarakter;
	private List<NeprijateljKarakter> neprijatelji;
	private JFrame prozor;

	public ProvjeraSudara suceljeZaProvjeruSudara;

	private boolean igraTraje;
	
	private int FPS = 60;

	private LocalTime pocetnoVrijeme;
	private LocalTime trenutnoVrijeme;

	public GamePanel(JFrame prozor) {
		this.prozor = prozor;
		
		this.postavkeEkrana = new PostavkeEkrana();
		this.upravljacTipkovnice = new UpravljacTipkovnice(this);
		this.upravljacBlokova = new UpravljacBlokova(this, "20x20_Mapa1.txt");
		this.suceljeZaProvjeruSudara = new ProvjeraSudara(this);
		
		this.FPS = 60;
		
		reset();

		

		this.setPreferredSize(new Dimension(postavkeEkrana.sirinaEkrana, postavkeEkrana.visinaEkrana));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);

		this.addKeyListener(upravljacTipkovnice);
		this.setFocusable(true);
	}


	// zapocni igricu
	public void startdretvaIgre() {
		this.dretvaIgre = new Thread(this);
		this.dretvaIgre.start();
	}


	// game loop
	@Override
	public void run() {
		double drawInterval = 1000000000/this.FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		long timer = 0;
		//int drawCount = 0;

		while (this.dretvaIgre != null) {
			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);

			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
				//drawCount++;
			}
			if (timer >= 1000000000) {
				if (this.igraTraje) {
					//System.out.println("FPS: "+ drawCount);
					this.trenutnoVrijeme = LocalTime.now();
				}
				timer = 0;
				//drawCount = 0;
			}

		}
	}


	// update stanja
	public void update() {
		if (igraTraje) {
			igracevKarakter.update();
			for (NeprijateljKarakter neprijatelj : neprijatelji) {
				neprijatelj.update();
			}
			//Collections.sort(neprijatelji);
		}

	}


	// crtaj komponente
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		upravljacBlokova.draw(g2);
		igracevKarakter.draw(g2);
		
		for (NeprijateljKarakter neprijatelj : neprijatelji) {
			neprijatelj.draw(g2);
		}
		this.postavkeEkrana.nacrtajVrijemePrezivljavanja(this.pocetnoVrijeme, this.trenutnoVrijeme, g2);
		if (!igraTraje) {
			this.postavkeEkrana.nacrtajEndScreen(g2);
		}
		g2.dispose();

	}


	public PostavkeEkrana getpostavkeEkrana() {
		return postavkeEkrana;
	}

	public void setpostavkeEkrana(PostavkeEkrana postavkeEkrana) {
		this.postavkeEkrana = postavkeEkrana;
	}

	public UpravljacTipkovnice getupravljacTipkovnice() {
		return upravljacTipkovnice;
	}

	public void setupravljacTipkovnice(UpravljacTipkovnice upravljacTipkovnice) {
		this.upravljacTipkovnice = upravljacTipkovnice;
	}

	public Thread getdretvaIgre() {
		return dretvaIgre;
	}

	public void setdretvaIgre(Thread dretvaIgre) {
		this.dretvaIgre = dretvaIgre;
	}

	public UpravljacBlokova getupravljacBlokova() {
		return upravljacBlokova;
	}

	public void setupravljacBlokova(UpravljacBlokova upravljacBlokova) {
		this.upravljacBlokova = upravljacBlokova;
	}

	
	public int getFPS() {
		return FPS;
	}

	public void setFPS(int fPS) {
		FPS = fPS;
	}

	public static int dobijRandomBroj(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}


	public List<NeprijateljKarakter> getNeprijatelji() {
		return neprijatelji;
	}


	public void setNeprijatelji(List<NeprijateljKarakter> neprijatelji) {
		this.neprijatelji = neprijatelji;
	}


	public boolean isIgraTraje() {
		return igraTraje;
	}


	public void setIgraTraje(boolean igraTraje) {
		this.igraTraje = igraTraje;
	}

	public void reset() {
		this.igracevKarakter = new IgracevKarakter(this, upravljacTipkovnice);

		this.neprijatelji = new ArrayList<>();
		this.neprijatelji.add(new NeprijateljKarakter(this, 6, 9));
		this.neprijatelji.add(new NeprijateljKarakter(this, 8, 17));
		this.neprijatelji.add(new NeprijateljKarakter(this, 8, 16));
		this.neprijatelji.add(new NeprijateljKarakter(this, 4, 9));
		this.neprijatelji.add(new NeprijateljKarakter(this, 12, 18));
		
		this.neprijatelji.add(new NeprijateljKarakter(this, 10, 9));
		this.neprijatelji.add(new NeprijateljKarakter(this, 10, 10));
		this.neprijatelji.add(new NeprijateljKarakter(this, 10, 11));
		this.neprijatelji.add(new NeprijateljKarakter(this, 10, 15));
		this.neprijatelji.add(new NeprijateljKarakter(this, 15, 15));
		

		this.pocetnoVrijeme = LocalTime.now();
		this.trenutnoVrijeme = LocalTime.now();

		this.igraTraje = true;
	}


	public JFrame getProzor() {
		return prozor;
	}


	public void setProzor(JFrame prozor) {
		this.prozor = prozor;
	}

}

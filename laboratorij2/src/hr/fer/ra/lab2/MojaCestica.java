package hr.fer.ra.lab2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class MojaCestica {

	private static Texture teksturaCestice;
	private static Tocka zOs = new Tocka(0.0f, 0.0f, 1.0f);

	private Tocka pozicija;
	private Tocka boja;
	private int vrijemeZivota;
	private Tocka brzina;
	private Tocka osRotacije;
	private float kutRotacije;
	private int vrijemeSpavanja;

	public MojaCestica(float x, float y, float z, int vrijemeZivota, float v1, float v2, float v3, int vrijemeSpavanja) {
		this.pozicija = new Tocka(x, y, z);
		this.boja = new Tocka(1.0f, 1.0f, 1.0f);
		this.vrijemeZivota = vrijemeZivota;
		this.brzina = new Tocka(v1, v2, v3);
		this.vrijemeSpavanja = vrijemeSpavanja;
		this.osRotacije = new Tocka(0.0f, 0.0f, 1.0f);
		this.kutRotacije = 0.0f;
	}

	public MojaCestica(Tocka pozicija, int vrijemeZivota, float v1, float v2, float v3 , int vrijemeSpavanja) {
		this.pozicija = pozicija;
		this.boja = new Tocka(1.0f, 1.0f, 1.0f);
		this.vrijemeZivota = vrijemeZivota;
		this.brzina = new Tocka(v1, v2, v3);
		this.vrijemeSpavanja = vrijemeSpavanja;
		this.osRotacije = new Tocka(0.0f, 0.0f, 1.0f);
		this.kutRotacije = 0.0f;
	}
	
	private static int getRandomBrojIzIntervala(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}

	public static MojaCestica stvoriNovuCesticu(MojIzvor2 izvor) {

		Tocka novaPozicija = izvor.dobijNovuRandomPoziciju();

		int vrijemeZivota = (int) (300 * Math.random() + 400);

		int randomX = getRandomBrojIzIntervala(-5, 5);
		int randomZ = getRandomBrojIzIntervala(-5, 5);
		
		
		float vX = 0.001f;
		float vY = -0.01f;
		float vZ = 0.001f;
		
		vX*=randomX; 
		vZ*=randomZ;
		
		int vrijemeSpavanja = (int) (500 * Math.random());

		return new MojaCestica(novaPozicija, vrijemeZivota, vX, vY, vZ, vrijemeSpavanja);

	}

	public void resetirajCesticu(MojIzvor2 izvor) {


		this.pozicija = izvor.dobijNovuRandomPoziciju();

		this.vrijemeZivota = (int) (300 * Math.random() + 400);
		
		this.boja.setX(1.0f);
		this.boja.setY(1.0f);
		this.boja.setZ(1.0f);
		
		int randomX = getRandomBrojIzIntervala(-5, 5);
		int randomZ = getRandomBrojIzIntervala(-5, 5);
		
		
		float vX = 0.001f;
		float vY = -0.01f;
		float vZ = 0.001f;
		
		vX*=randomX; 
		vZ*=randomZ;
		
		this.brzina.setX(vX);
		this.brzina.setY(vY);
		this.brzina.setZ(vZ);
	}



	public void azurirajCesticuZaJedanPomakVremena(MojIzvor2 izvor, Tocka pogledTocka) {

		if (this.vrijemeSpavanja > 0) {
			this.vrijemeSpavanja-=1;
			return;
		}

		// promjeni polozaj
		this.pozicija.setX(this.pozicija.getX() + this.brzina.getX());
		this.pozicija.setY(this.pozicija.getY() + this.brzina.getY());
		this.pozicija.setZ(this.pozicija.getZ() + this.brzina.getZ());

		// rotiraj cesticu prema pogledu
		this.osRotacije = Tocka.postaviOsZaRotaciju(zOs, pogledTocka, osRotacije);

		this.kutRotacije = Tocka.dobijKutUStupnjevima(zOs, pogledTocka);

		// smanji zivot
		this.vrijemeZivota -= 1;


		switch(vrijemeZivota) {
		case 300:
			this.boja.setX(0.0f);
			this.boja.setY(0.0f);
			this.boja.setZ(0.05f);
			break;
		case 270:
			this.boja.setZ(0.1f);
			break;
		case 240:
			this.boja.setZ(0.15f);
			break;
		case 210:
			this.boja.setZ(0.2f);
			break;
		case 180:
			this.boja.setZ(0.25f);
			break;
		case 150:
			this.boja.setZ(0.3f);
			break;
		case 120:
			this.boja.setZ(0.35f);
			break;
		case 90:
			this.boja.setZ(0.4f);
			break;
		case 60:
			this.boja.setZ(0.45f);
			break;
		case 30:
			this.boja.setZ(0.5f);
			break;
		}

		// ako je mrtva reset
		if (jeLiMrtva()) {
			resetirajCesticu(izvor);
		}

	}

	public boolean jeLiMrtva() {
		return vrijemeZivota < 0 ? true : false; 
	}


	public static Texture dobijTeksturuCestice(GLProfile gp) {
		if (teksturaCestice == null) {
			BufferedImage slika = null;
			slika = ucitajSlikuCestice("C:\\Users\\ninov\\Desktop\\snow.bmp");
			teksturaCestice = AWTTextureIO.newTexture(gp, slika, true);
		}
		return teksturaCestice;
	}

	public void nacrtajSliku(GL2 gl, GLProfile gp) {
		if (this.vrijemeSpavanja > 0) return;
		Texture tekstura = dobijTeksturuCestice(gp);
		if (tekstura != null) {
			gl.glPushMatrix();

			gl.glColor4f(this.boja.getX(), this.boja.getY(), this.boja.getZ(), 0.5f);

			tekstura.enable(gl);
			tekstura.bind(gl);

			gl.glTranslatef(this.pozicija.getX(),this.pozicija.getY(),this.pozicija.getZ());

			gl.glRotatef(this.kutRotacije, osRotacije.getX(), osRotacije.getY(), osRotacije.getZ());

			gl.glBegin(GL2.GL_QUADS);

			gl.glTexCoord2d(0.0, 0.0);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);

			gl.glTexCoord2d(1.0, 0.0);
			gl.glVertex3f(0.5f, 0.0f, 0.0f);

			gl.glTexCoord2d(1.0, 1.0);
			gl.glVertex3f(0.5f, 0.5f, 0.0f);

			gl.glTexCoord2d(0.0, 1.0);
			gl.glVertex3f(0.0f, 0.5f, 0.0f);

			gl.glEnd();

			tekstura.disable(gl);

			gl.glPopMatrix();
		}
	}

	private static BufferedImage ucitajSlikuCestice(String put) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(put));
		} catch (IOException e) {
			System.out.println("Nisam uspio ucitati sliku cestice!");
		}

		if (img != null) {
			img.flush();
		}
		System.out.println("Ucitao sliku cestice!");
		return img;

	}

}

package hr.fer.ra.lab2;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;

public class SustavCestica {

	private List<MojaCestica> listaCestica;
	private MojIzvor2 izvor;
	private GLProfile gp;
	private Tocka pogledTocka = new Tocka(0.0f, 0.0f, 10.0f);
	
	public SustavCestica(int brojCestica, GLProfile gp) {
		listaCestica = new ArrayList<>(brojCestica);
		this.izvor = new MojIzvor2(0, 0, 0);

		this.gp = gp;
		for (int i = 0; i < brojCestica; i++) {
			listaCestica.add(MojaCestica.stvoriNovuCesticu(izvor));
		}

	}

	public void pomakniIzvorZa(float x, float y, float z) {
		this.izvor.pomakniIzvorZa(x, y, z);
	}
	
	public void azurirajPogled(float x, float y, float z) {
		this.pogledTocka.setX(x);
		this.pogledTocka.setY(y);
		this.pogledTocka.setZ(z);
	}


	public void nacrtajSustav(GL2 gl) {
		
		izvor.nacrtaj(gl);
		
		for (MojaCestica c : listaCestica) {
			c.nacrtajSliku(gl, gp);
		}
	}

	public void azurirajSustav() {
		for (MojaCestica c : listaCestica) {
			c.azurirajCesticuZaJedanPomakVremena(izvor, pogledTocka);
		}
	}

}

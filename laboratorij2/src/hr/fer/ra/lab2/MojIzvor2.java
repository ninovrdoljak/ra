package hr.fer.ra.lab2;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class MojIzvor2 {
	
	private List<Tocka> lista;
	
	
	public MojIzvor2(float x, float y, float z) {
		this.lista = new ArrayList<>();
		
		Tocka t1 = new Tocka(x, y, z);
		Tocka t2 = new Tocka(x + 2, y, z);
		Tocka t3 = new Tocka(x + 2, y , z + 2);
		Tocka t4 = new Tocka(x, y, z + 2);
		lista.add(t1);
		lista.add(t2);
		lista.add(t3);
		lista.add(t4);
	}
	
	public Tocka dobijJedanodVrhovaIzvora(int indeks) {
		return lista.get(indeks);
	}
	
	public Tocka dobijNovuRandomPoziciju() {
		Tocka nova = Tocka.kopirajTocku(lista.get(0));
		
		//Tocka tockaX = lista.get(1);
		//Tocka tockaZ = lista.get(3);
		
		float postotak1 = (float) Math.random();
		float postotak2 = (float) Math.random();
		
		nova.setX(nova.getX() + 2.0f * postotak1 - 0.5f);
		nova.setY(nova.getY() - 0.5f);
		nova.setZ(nova.getZ() + 2.0f * postotak2 );
		
		return nova;
	}
	
	public void pomakniIzvorZa(float x, float y, float z) {
		//System.out.println("Izvor: " + lista.get(0));
		for (Tocka t : lista) {
			t.setX(t.getX() + x);
			t.setY(t.getY() + y);
			t.setZ(t.getZ() + z);
		}
	}
	
	public void nacrtaj(GL2 gl) {
		
		gl.glPushMatrix();
		
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL2.GL_LINE_LOOP);
		
		for (int i = 0; i < lista.size(); i++) {
			gl.glVertex3f(lista.get(i).getX(), lista.get(i).getY(), lista.get(i).getZ());
		}
		
		gl.glEnd();
		gl.glPopMatrix();
	}

}

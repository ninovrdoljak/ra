package hr.fer.ra.lab1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jogamp.opengl.GL2;

public class MojObjekt {
	
	private List<Tocka> listaTocaka;
	private List<Trokut> listaTrokuta;
	private Tocka sredisteObjekta;
	
	public Tocka getSredisteObjekta() {
		return sredisteObjekta;
	}

	public void setSredisteObjekta(Tocka sredisteObjekta) {
		this.sredisteObjekta = sredisteObjekta;
	}

	public MojObjekt(String put) {
		this.ucitajObjekt(put);
		this.sredisteObjekta = dobijSredisteObjekta();
	}
	
	private Tocka dobijSredisteObjekta() {
		float x = 0.0f;
		float y = 0.0f;
		float z = 0.0f;
		for (Tocka t : listaTocaka) {
			x += t.getX();
			y += t.getY();
			z += t.getZ();
		}
		return new Tocka(x/listaTocaka.size(), y/listaTocaka.size(), z/listaTocaka.size());
	}
	
	public void pomakniNaSrediste(GL2 gl2) {
		gl2.glTranslatef(-this.getSredisteObjekta().getX(),
				-this.getSredisteObjekta().getY(),
				-this.getSredisteObjekta().getZ());
		
	}
	
	public void nacrtajObjekt(GL2 gl2) {
		
		gl2.glBegin(GL2.GL_TRIANGLES);
		
		for (Trokut t : listaTrokuta) {
			gl2.glColor3f(1.0f, 0.0f, 0.0f);
			gl2.glVertex3f(t.getTocka1().getX(), t.getTocka1().getY(), t.getTocka1().getZ());
			
			gl2.glColor3f(0.0f, 1.0f, 0.0f);
			gl2.glVertex3f(t.getTocka2().getX(), t.getTocka2().getY(), t.getTocka2().getZ());
			
			gl2.glColor3f(0.0f, 0.0f, 1.0f); 
			gl2.glVertex3f(t.getTocka3().getX(), t.getTocka3().getY(), t.getTocka3().getZ());
			
		}
		gl2.glEnd();
		//gl2.glFlush();
		
	}
	
	private void ucitajObjekt(String put) {
		
		try {
			
			this.listaTocaka = new ArrayList<>();
			this.listaTrokuta = new ArrayList<>();
			String[] tmpPoljeString;
			
			File datotekaObj = new File(put);
			Scanner scanner = new Scanner(datotekaObj);
			
			String linija = "";
			
			while (scanner.hasNextLine()) {
				
				linija = scanner.nextLine();
				
				//System.out.println(linija);
				
				if (linija.startsWith("v")) {
					tmpPoljeString = linija.split(" ");
					if (tmpPoljeString.length == 4) {
						listaTocaka.add(new Tocka(Float.parseFloat(tmpPoljeString[1]),
													Float.parseFloat(tmpPoljeString[2]),
													Float.parseFloat(tmpPoljeString[3])
													));
					}
					
				} else if (linija.startsWith("f")) {
					tmpPoljeString = linija.split(" ");
					if (tmpPoljeString.length == 4) {
						listaTrokuta.add(Trokut.stvoriTrokut(listaTocaka, Integer.parseInt(tmpPoljeString[1]), 
																			Integer.parseInt(tmpPoljeString[2]), 
																			Integer.parseInt(tmpPoljeString[3])
																			));
					}
				} else {
					
					continue;
				}
			}
			scanner.close();
			/*
			System.out.println(listaTocaka.size());
			for (Tocka t : listaTocaka) {
				System.out.println(t);
			}
			System.out.println(listaTrokuta.size());
			
			for (Trokut t : listaTrokuta) {
				System.out.println(t);
			}*/
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	public List<Tocka> getListaTocaka() {
		return listaTocaka;
	}

	public void setListaTocaka(List<Tocka> listaTocaka) {
		this.listaTocaka = listaTocaka;
	}

	public List<Trokut> getListaTrokuta() {
		return listaTrokuta;
	}

	public void setListaTrokuta(List<Trokut> listaTrokuta) {
		this.listaTrokuta = listaTrokuta;
	}

}

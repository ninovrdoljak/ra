package hr.fer.ra.lab1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jogamp.opengl.GL2;

public class BsplajnKrivulja {


	private List<Tocka> listaTocaka;
	private Tocka[] poljeSvihTocakaZaSvakiT;
	private Tocka[] poljeSvihTocakaTangentiZaSvakiT;
	private Tocka[] poljeSvihTocakaOsiRotacijeZaSvakiT;
	private float[] kutoviZaRotaciju;
	private int brojPozicija = 0;

	

	public Tocka[] getPoljeSvihTocakaTangentiZaSvakiT() {
		return poljeSvihTocakaTangentiZaSvakiT;
	}

	public int getBrojPozicija() {
		return brojPozicija;
	}
	
	public Tocka[] getPoljeSvihTocakaZaSvakiT() {
		return poljeSvihTocakaZaSvakiT;
	}


	public BsplajnKrivulja(String put) {
		ucitajKrivulju(put);
		skalirajTockeListe(this.listaTocaka, -3, 3);
		/*for (Tocka t : listaTocaka) {
			System.out.println(t);
		}*/
		napraviPoljeSvihTocakaZaSvakiSegment(100);
		//System.out.println(brojPozicija);
		
	}
	
	public void rotirajObjekt(GL2 gl2, int trenutnaPozicija) {
		gl2.glRotatef(kutoviZaRotaciju[trenutnaPozicija],poljeSvihTocakaOsiRotacijeZaSvakiT[trenutnaPozicija].getX(), 
				poljeSvihTocakaOsiRotacijeZaSvakiT[trenutnaPozicija].getY(), 
				poljeSvihTocakaOsiRotacijeZaSvakiT[trenutnaPozicija].getZ());
	}
	
	public void translatirajNaPozicijuKrivulje(GL2 gl2, int trenutnaPozicija) {
		gl2.glTranslatef(poljeSvihTocakaZaSvakiT[trenutnaPozicija].getX(),
				poljeSvihTocakaZaSvakiT[trenutnaPozicija].getY(),
				poljeSvihTocakaZaSvakiT[trenutnaPozicija].getZ());
	}

	public void nacrtajTangentu(GL2 gl2, int trenutnaPozicija, boolean smjer) {

		gl2.glBegin(GL2.GL_LINES);
		gl2.glColor3f(1.0f, 0.0f, 0.0f);
		gl2.glVertex3f(poljeSvihTocakaZaSvakiT[trenutnaPozicija].getX(), 
				poljeSvihTocakaZaSvakiT[trenutnaPozicija].getY(), 
				poljeSvihTocakaZaSvakiT[trenutnaPozicija].getZ());
		if (smjer) {
			gl2.glVertex3f(poljeSvihTocakaZaSvakiT[trenutnaPozicija].getX() + poljeSvihTocakaTangentiZaSvakiT[trenutnaPozicija].getX()*0.5f, 
					poljeSvihTocakaZaSvakiT[trenutnaPozicija].getY() +poljeSvihTocakaTangentiZaSvakiT[trenutnaPozicija].getY()*0.5f, 
					poljeSvihTocakaZaSvakiT[trenutnaPozicija].getZ() +poljeSvihTocakaTangentiZaSvakiT[trenutnaPozicija].getZ()*0.5f);
			
		} else {
			gl2.glVertex3f(poljeSvihTocakaZaSvakiT[trenutnaPozicija].getX() - poljeSvihTocakaTangentiZaSvakiT[trenutnaPozicija].getX()*0.5f, 
					poljeSvihTocakaZaSvakiT[trenutnaPozicija].getY() -poljeSvihTocakaTangentiZaSvakiT[trenutnaPozicija].getY()*0.5f, 
					poljeSvihTocakaZaSvakiT[trenutnaPozicija].getZ() -poljeSvihTocakaTangentiZaSvakiT[trenutnaPozicija].getZ()*0.5f);
			
		}
		

		gl2.glEnd();
	}

	public void nacrtajKrivulju(GL2 gl2) {
		gl2.glBegin(GL2.GL_LINE_STRIP);
		gl2.glColor3f(1.0f, 1.0f, 0.0f);
		for (Tocka t : poljeSvihTocakaZaSvakiT) {
			
			gl2.glVertex3f(t.getX(), t.getY(), t.getZ());

		}
		gl2.glEnd();
	}
	
	
	private void skalirajTockeListe(List<Tocka> lista, float min, float max) {
		if (lista == null || lista.size() == 0) return;
		Tocka tr = lista.get(0);
		
		float maxX = tr.getX();
		float minX = tr.getX();
		
		float maxY = tr.getY();
		float minY = tr.getY();
		
		float maxZ = tr.getZ();
		float minZ = tr.getZ();
		
		for (int i = 0; i < lista.size(); i++) {
			tr = lista.get(i);
			if (tr.getX() > maxX) {
				maxX = tr.getX();
			}
			else if (tr.getX() < minX) {
				minX = tr.getX();
			}
			
			if (tr.getY() > maxY) {
				maxY = tr.getY();
			}
			else if (tr.getY() < minY) {
				minY = tr.getY();
			}
			
			if (tr.getZ() > maxZ) {
				maxZ = tr.getZ();
			}
			else if (tr.getZ() < minZ) {
				minZ = tr.getZ();
			}
		}
		
		float novaVrijednost = 0.0f;
		
		
		for (int i = 0; i < lista.size(); i++) {
			tr = lista.get(i);
			
			novaVrijednost = (max - min) * (tr.getX() - minX) / (maxX - minX) + min;
			tr.setX(novaVrijednost);
			
			novaVrijednost = (max - min) * (tr.getY() - minY) / (maxY - minY) + min;
			tr.setY(novaVrijednost);
			
			novaVrijednost = (max - min) * (tr.getZ() - minZ) / (maxZ - minZ) + min;
			tr.setZ(novaVrijednost);
			
		}
		
	}


	private void napraviPoljeSvihTocakaZaSvakiSegment(int iznosT) {
		if (listaTocaka == null || listaTocaka.size() == 0) return;
		Tocka vektorPremaPogledu = new Tocka(0.0f, 0.0f, 1.0f);

		int brojSegmenata = listaTocaka.size() - 3;
		this.brojPozicija = iznosT * brojSegmenata;
		
		
		Tocka[] poljeTocaka = new Tocka[brojPozicija];
		Tocka[] poljeTangenti = new Tocka[brojPozicija];
		Tocka[] poljeOsiRotacije = new Tocka[brojPozicija];
		float[] poljeKutova = new float[brojPozicija];
		
		
		int trenutnaTocka = 0;
		

		for (int trenutniSegment = 0; trenutniSegment < brojSegmenata; trenutniSegment++) {
			for (int trenutniT = 0; trenutniT < iznosT; trenutniT++) {

				poljeTocaka[trenutnaTocka] = dobijTockuSegmenta((double)trenutniT/iznosT, trenutniSegment);
				poljeTangenti[trenutnaTocka] = dobijTangentuTockeSegmenta((double)trenutniT/iznosT, trenutniSegment);
				
				poljeOsiRotacije[trenutnaTocka] = Tocka.dobijOsZaRotaciju(vektorPremaPogledu, poljeTangenti[trenutnaTocka]);
				poljeKutova[trenutnaTocka] = Tocka.dobijKutUStupnjevima(vektorPremaPogledu, poljeTangenti[trenutnaTocka]);

				trenutnaTocka++;
			}

		}

		this.poljeSvihTocakaZaSvakiT = poljeTocaka;
		this.poljeSvihTocakaTangentiZaSvakiT = poljeTangenti;
		this.poljeSvihTocakaOsiRotacijeZaSvakiT = poljeOsiRotacije;
		this.kutoviZaRotaciju = poljeKutova;

	}


	private Tocka dobijTockuSegmenta(double t, int segment) {
		//System.out.println(t);
		if (segment >= listaTocaka.size() - 3) return null;

		Tocka tocka1 = listaTocaka.get(segment);
		Tocka tocka2 = listaTocaka.get(segment+1);
		Tocka tocka3 = listaTocaka.get(segment+2);
		Tocka tocka4 = listaTocaka.get(segment+3);

		// matricno mnozenje po formuli
		float stupac1 =  (float) (( - Math.pow(t, 3) + 3 * Math.pow(t, 2) - 3 * t + 1) / 6);
		float stupac2 = (float) (( 3 * Math.pow(t, 3) -6 * Math.pow(t, 2) + 4) / 6);
		float stupac3 = (float) (( -3 * Math.pow(t, 3) +3 * Math.pow(t, 2) + 3 * t + 1) / 6);
		float stupac4 = (float) ( Math.pow(t, 3) / 6);

		float noviX = stupac1 * tocka1.getX() + stupac2 * tocka2.getX() + stupac3 * tocka3.getX() + stupac4 * tocka4.getX();
		float noviY = stupac1 * tocka1.getY() + stupac2 * tocka2.getY() + stupac3 * tocka3.getY() + stupac4 * tocka4.getY();
		float noviZ = stupac1 * tocka1.getZ() + stupac2 * tocka2.getZ() + stupac3 * tocka3.getZ() + stupac4 * tocka4.getZ();

		return new Tocka(noviX, noviY, noviZ);
	}

	private Tocka dobijTangentuTockeSegmenta(double t, int segment) {

		if (segment >= listaTocaka.size() - 3) return null;

		Tocka tocka1 = listaTocaka.get(segment);
		Tocka tocka2 = listaTocaka.get(segment+1);
		Tocka tocka3 = listaTocaka.get(segment+2);
		Tocka tocka4 = listaTocaka.get(segment+3);

		// matricno mnozenje po formuli
		float stupac1 =  (float) (( - Math.pow(t, 2) + 2 * t - 1) / 2);
		float stupac2 = (float) (( 3 * Math.pow(t, 2) - 4 * t) / 2);
		float stupac3 = (float) (( - 3 * Math.pow(t, 2) + 2 * t + 1) / 2);
		float stupac4 = (float) ( Math.pow(t, 2) / 2);

		float noviX = stupac1 * tocka1.getX() + stupac2 * tocka2.getX() + stupac3 * tocka3.getX() + stupac4 * tocka4.getX();
		float noviY = stupac1 * tocka1.getY() + stupac2 * tocka2.getY() + stupac3 * tocka3.getY() + stupac4 * tocka4.getY();
		float noviZ = stupac1 * tocka1.getZ() + stupac2 * tocka2.getZ() + stupac3 * tocka3.getZ() + stupac4 * tocka4.getZ();

		return new Tocka(noviX, noviY, noviZ);

	}

	private void ucitajKrivulju(String put) {

		try {

			this.listaTocaka = new ArrayList<>();
			String[] tmpPoljeString;

			File datotekaObj = new File(put);
			Scanner scanner = new Scanner(datotekaObj);

			String linija = "";

			while (scanner.hasNextLine()) {

				linija = scanner.nextLine();

				//System.out.println(linija);

				tmpPoljeString = linija.split(" ");
				if (tmpPoljeString.length == 4) {
					listaTocaka.add(new Tocka(Float.parseFloat(tmpPoljeString[1]),
							Float.parseFloat(tmpPoljeString[2]),
							Float.parseFloat(tmpPoljeString[3])
							));
				}
			}
			scanner.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	public static void main(String[] args) {
		
		BsplajnKrivulja krivulja = new BsplajnKrivulja("C:\\Users\\ninov\\Desktop\\objekti\\mojaKrivulja.obj");
		
		
		for (int i = 10; i < 100; i+=10) {
			System.out.println(krivulja.poljeSvihTocakaZaSvakiT[i]);
			System.out.println(krivulja.poljeSvihTocakaTangentiZaSvakiT[i]);
			System.out.println("--------------");
			
		}
		
	}*/

}

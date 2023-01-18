package hr.fer.ra.lab1;

import java.util.List;

public class Trokut {
	
	private Tocka tocka1;
	private Tocka tocka2;
	private Tocka tocka3;
	public Trokut(Tocka tocka1, Tocka tocka2, Tocka tocka3) {
		super();
		this.tocka1 = tocka1;
		this.tocka2 = tocka2;
		this.tocka3 = tocka3;
	}
	
	
	
	public static Trokut stvoriTrokut(List<Tocka> lista, int prvi, int drugi, int treci) {
		try {
			Tocka t1 = lista.get(prvi-1);
			Tocka t2 = lista.get(drugi-1);
			Tocka t3 = lista.get(treci-1);
			return new Trokut(t1, t2, t3);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("pogreska pri ucitavanju");
			
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return "Trokut"+ " (" + tocka1.toString() + " "+ tocka2.toString()+" " + tocka3.toString()+" )";
	}



	public Tocka getTocka1() {
		return tocka1;
	}



	public void setTocka1(Tocka tocka1) {
		this.tocka1 = tocka1;
	}



	public Tocka getTocka2() {
		return tocka2;
	}



	public void setTocka2(Tocka tocka2) {
		this.tocka2 = tocka2;
	}



	public Tocka getTocka3() {
		return tocka3;
	}



	public void setTocka3(Tocka tocka3) {
		this.tocka3 = tocka3;
	}
	
	

}

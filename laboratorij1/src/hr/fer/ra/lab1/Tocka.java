package hr.fer.ra.lab1;

public class Tocka {
	
	private float x;
	private float y;
	private float z;
	
	
	public Tocka(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	public static Tocka dobijOsZaRotaciju(Tocka s, Tocka e) {
		return new Tocka(s.y*e.z - e.y * s.z, e.x * s.z - s.x * e.z, s.x * e.y - s.y * e.x);
	}
	
	
	public static float dobijKutUStupnjevima(Tocka s, Tocka e) {
		
		float brojnik = s.x * e.x + s.y * e.y + s.z * e.z;
		float nazivnik = (float) (Math.sqrt(s.x * s.x + s.y * s.y + s.z * s.z) + Math.sqrt(e.x * e.x + e.y * e.y + e.z * e.z));
		
		// ovaj kut je u stupnjevima
		float kut = (float) Math.toDegrees(Math.acos(brojnik/nazivnik));
		
		return kut;
	}


	@Override
	public String toString() {
		return "Tocka"+ " " + x + " "+ y+" " + z;
	}


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public float getZ() {
		return z;
	}


	public void setZ(float z) {
		this.z = z;
	}
	
}

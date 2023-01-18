package hr.fer.ra.lab2;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;

public class MojGlEventListener implements GLEventListener{


	private GLU glu;
	private GLProfile gp;
	private SustavCestica sustavCestica;
	private Tocka pogled;
	private float kutX;
	private float kutY;
	public boolean pauzirano = false;
	
	public GLProfile getProfile() {
		return this.gp;
	}

	public void promijeniXZa(float i) {
		this.kutX += i;
		if (kutX == 360.0f) kutX = 0.0f;
	}

	public void promijeniYZa(float i) {
		this.kutY += i;
		if (kutX == 360.0f) kutX = 0.0f;
	}
	
	public void azurirajPogled() {
		float x = 0.0f;
		float y = 0.0f;
		float z = 0.0f;
		
		float xN = 0.0f;
		float yN = 0.0f;
		float zN = 0.0f;
		
		// rotacija oko x
		x = pogled.getX();
		y =  (float) (pogled.getY() * Math.cos(Math.toRadians(-kutX)) - pogled.getZ() * Math.sin(Math.toRadians(-kutX)));
		z =  (float) (pogled.getY() * Math.sin(Math.toRadians(-kutX)) + pogled.getZ() * Math.cos(Math.toRadians(-kutX)));
		
		// rotacija oko y
		xN = (float) (x * Math.cos(Math.toRadians(-kutY)) + z * Math.sin(Math.toRadians(-kutY)));
		yN =  y;
		zN =  (float) (- x * Math.sin(Math.toRadians(-kutY)) + z * Math.cos(Math.toRadians(-kutY)));
		//System.out.println("Pogled za kut: " + kutX +" "+ kutY +": "+ xN + " " + yN + " " + zN);
		this.sustavCestica.azurirajPogled(xN, yN, zN);
	}

	public void pomakniIzvorZa(float x, float y, float z) {
		this.sustavCestica.pomakniIzvorZa(x, y, z);
	} 

	public MojGlEventListener(GLProfile gp) {
		super();
		this.glu = new GLU();
		this.gp = gp;
		this.sustavCestica = new SustavCestica(400, gp);
		this.pogled = new Tocka(0.0f, 0.0f, 10.0f);
		this.kutX = 0.0f;
		this.kutY = 0.0f;
	}

	@Override
	public void display(GLAutoDrawable drawable) {

		final GL2 gl = drawable.getGL().getGL2();

		// ocisti screen i dubinski buffer
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		// resetiraj moderview matricu
		gl.glLoadIdentity();  

		// udalji sve od pogleda
		gl.glTranslatef(pogled.getX(), pogled.getY(), -pogled.getZ());

		// crtanje -----------------------------------------

		//gl.glRotatef(45.0f, 1.0f, 1.0f, 1.0f);

		//gl.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);

		gl.glRotatef(kutX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(kutY, 0.0f, 1.0f, 0.0f);

		//gl.glEnable(GL2.GL_BLEND);
		
		sustavCestica.nacrtajSustav(gl);

		gl.glFlush();

		// azuriraj sustav cestica
		azurirajPogled();
		if (!pauzirano) sustavCestica.azurirajSustav();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}


	@Override
	public void init(GLAutoDrawable drawable) {

		final GL2 gl = drawable.getGL().getGL2();

		// lijepo preklapanje boja
		gl.glShadeModel(GL2.GL_SMOOTH);

		// ocisti ekran na crnu boju
		gl.glClearColor(0f, 0f, 0f, 0f);

		// podesi buffer za dubinu
		gl.glClearDepth(1.0f);
		//gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);

		// tekstura
		gl.glEnable(GL.GL_TEXTURE_2D);

		gl.glEnable( GL.GL_BLEND );
		//gl.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA );

		//gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);

		// smanji performansu za ljepsi izgled scene
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

		//gl.glColor4f(1f, 1f, 1f, 0.5f);//50% Alpha
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL().getGL2();

		// sprijeci djeljenje s 0
		if( height <=0 )
			height =1;

		// resetiraj viewport
		gl.glViewport( 0, 0, width, height );

		// resetiraj projekciju
		gl.glMatrixMode( GL2.GL_PROJECTION );
		gl.glLoadIdentity();

		// podesi prozor
		final float h = ( float ) width / ( float ) height;
		glu.gluPerspective( 45.0f, h, 1.0, 20.0 );

		// resetiraj moderview
		gl.glMatrixMode( GL2.GL_MODELVIEW );
		gl.glLoadIdentity();

	}

}

package hr.fer.ra.lab1;



import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class MojGlEventListener implements GLEventListener{


	private GLU glu = new GLU();
	
	private MojObjekt mojObjekt;
	private BsplajnKrivulja mojaKrivulja;
	
	private int trenutnaPozicija = 0;
	private boolean naprijed = true;

	public MojGlEventListener(MojObjekt mojObjekt, BsplajnKrivulja mojaKrivulja) {
		super();
		this.mojObjekt = mojObjekt;
		this.mojaKrivulja = mojaKrivulja;
	}

	@Override
	public void display(GLAutoDrawable drawable) {

		final GL2 gl = drawable.getGL().getGL2();
		
		// ocisti screen i dubinski buffer
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		// resetiraj moderview matricu
		gl.glLoadIdentity();  

		// udalji sve od pogleda
		gl.glTranslatef(0.0f,0.0f,-10.0f);

		// rotiraj sve proizvoljno
		gl.glRotatef(45.0f, 1.0f, 1.0f, 1.0f);

		// nacrtaj krivulju
		mojaKrivulja.nacrtajKrivulju(gl);

		// nacrtaj tangentu
		mojaKrivulja.nacrtajTangentu(gl, trenutnaPozicija, naprijed);

		// tijelo translatiraj na poziciju krivulje
		mojaKrivulja.translatirajNaPozicijuKrivulje(gl, trenutnaPozicija);

		//gl.glScalef( 0.1f,0.1f,0.1f );
		
		// pravilno orijentiraj objekt
		mojaKrivulja.rotirajObjekt(gl, trenutnaPozicija);
		
		// pomakni se na srediste objekta
		mojObjekt.pomakniNaSrediste(gl);

		// nacrtaj objekt
		mojObjekt.nacrtajObjekt(gl);

		gl.glFlush();

		// azuriraj poziciju
		if (naprijed) {
			trenutnaPozicija+=1;
		} else  {
			trenutnaPozicija-=1;
		}

		// azuriraj smjer
		if (trenutnaPozicija == this.mojaKrivulja.getBrojPozicija() - 1 && naprijed) {
			naprijed = false;
			//System.out.println("kraj");
		} else if (trenutnaPozicija == 0 && naprijed == false) {
			naprijed = true;
			//System.out.println("pocetak");
		}
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
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
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		
		// smanji performansu za ljepsi izgled scene
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

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

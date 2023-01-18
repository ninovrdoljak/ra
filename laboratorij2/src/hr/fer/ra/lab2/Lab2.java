package hr.fer.ra.lab2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;



public class Lab2 {

	public static void main(String[] args) {

		final GLProfile gp = GLProfile.get(GLProfile.GL2);  
		GLCapabilities cap = new GLCapabilities(gp);  
		final GLCanvas gc = new GLCanvas(cap);

		MojGlEventListener mojGlEventListener = new MojGlEventListener(gp);

		gc.addGLEventListener(mojGlEventListener);  
		gc.setSize(400, 400);

		final FPSAnimator animator = new FPSAnimator(gc, 300, true );

		final JFrame frame = new JFrame("Racunalna animacija: Laboratorij 2");  
		
		frame.getContentPane().add(gc);

		//Shutdown
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if(animator.isStarted())
					animator.stop();
				System.exit(0);
			}
		});

		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

				int keyCode = e.getKeyCode();
				switch(keyCode) {
				case KeyEvent.VK_A:
					//System.out.println("a");
					mojGlEventListener.pomakniIzvorZa(-0.1f, 0.0f, 0.0f);
					break;
				case KeyEvent.VK_D:
					//System.out.println("d");
					mojGlEventListener.pomakniIzvorZa(0.1f, 0.0f, 0.0f);
					break;
				case KeyEvent.VK_S:
					//System.out.println("s");
					mojGlEventListener.pomakniIzvorZa(0.0f, 0.0f, 0.1f);
					break;
				case KeyEvent.VK_W:
					//System.out.println("w");
					mojGlEventListener.pomakniIzvorZa(0.0f, 0.0f, -0.1f);
					break;
				case KeyEvent.VK_R:
					//System.out.println("gore");
					mojGlEventListener.pomakniIzvorZa(0.0f, 0.1f, 0.0f);
					break;
				case KeyEvent.VK_F:
					//System.out.println("dolje");
					mojGlEventListener.pomakniIzvorZa(0.0f, -0.1f, 0.0f);
					break;
				case KeyEvent.VK_UP:
					//System.out.println("gore rotacija");
					mojGlEventListener.promijeniXZa(1.0f);
					break;
				case KeyEvent.VK_DOWN:
					//System.out.println("dolje rotacija");
					mojGlEventListener.promijeniXZa(-1.0f);
					break;
				case KeyEvent.VK_LEFT:
					//System.out.println("lijevo rotacija");
					mojGlEventListener.promijeniYZa(1.0f);
					break;
				case KeyEvent.VK_RIGHT:
					//System.out.println("desno rotacija");
					mojGlEventListener.promijeniYZa(-1.0f);
					break;
				case KeyEvent.VK_P:
					//System.out.println("desno rotacija");
					mojGlEventListener.pauzirano = mojGlEventListener.pauzirano == false ? true : false;
					break;
				}

			}
		});


		frame.setSize(400,400);  
		frame.setVisible(true);

		animator.start();

	}


}

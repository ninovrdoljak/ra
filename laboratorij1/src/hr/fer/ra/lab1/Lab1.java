package hr.fer.ra.lab1;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Lab1 {

	public static void main(String[] args) {
		final GLProfile gp = GLProfile.get(GLProfile.GL2);  
		GLCapabilities cap = new GLCapabilities(gp);  

		final GLCanvas gc = new GLCanvas(cap);
		
		
		MojObjekt mojObjekt = new MojObjekt("C:\\Users\\ninov\\Desktop\\objekti\\bird.obj");
		BsplajnKrivulja krivulja = new BsplajnKrivulja("C:\\Users\\ninov\\Desktop\\objekti\\mojaKrivulja.obj");
		
		
		MojGlEventListener mojGlEventListener = new MojGlEventListener(mojObjekt, krivulja);
		
		
		gc.addGLEventListener(mojGlEventListener);  
		gc.setSize(400, 400);
		
		final FPSAnimator animator = new FPSAnimator(gc, 300, true );

		final JFrame frame = new JFrame("Racunalna animacija: Laboratorij 1");  

		frame.getContentPane().add(gc);

		//Shutdown
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if(animator.isStarted())
					animator.stop();
				System.exit(0);
			}
		});

		
		frame.setSize(400,400);  
		frame.setVisible(true);
		
		animator.start();

	}

}

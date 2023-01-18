package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame prozor = new JFrame();
		prozor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		prozor.setResizable(false);
		prozor.setTitle("Invazija robota");
		
		GamePanel gamePanel = new GamePanel(prozor);
		prozor.add(gamePanel);
		prozor.pack();
		
		prozor.setLocationRelativeTo(null);
		prozor.setVisible(true);
		
		gamePanel.startdretvaIgre();
	}
}

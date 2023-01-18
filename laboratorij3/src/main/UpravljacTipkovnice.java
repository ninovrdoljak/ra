package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UpravljacTipkovnice implements KeyListener {
	
	
	public boolean pritisnutGumbGore;
	public boolean pritisnutGumbDolje;
	public boolean pritisnutGumbLijevo;
	public boolean pritisnutGumbDesno;
	private GamePanel gamePanel;
	
	public UpravljacTipkovnice(GamePanel gamePanel) {
		super();
		this.gamePanel = gamePanel;
		this.pritisnutGumbGore = false;
		this.pritisnutGumbDolje = false;
		this.pritisnutGumbLijevo = false;
		this.pritisnutGumbDesno = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch(code) {
		case KeyEvent.VK_W:
			this.pritisnutGumbGore = true;
			break;
		case KeyEvent.VK_A:
			this.pritisnutGumbLijevo = true;
			break;
		case KeyEvent.VK_S:
			this.pritisnutGumbDolje = true;
			break;
		case KeyEvent.VK_D:
			this.pritisnutGumbDesno = true;
			break;
		case KeyEvent.VK_R:
			if (!gamePanel.isIgraTraje()) {
				gamePanel.reset();
			}
			break;
		default:
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch(code) {
		case KeyEvent.VK_W:
			this.pritisnutGumbGore = false;
			break;
		case KeyEvent.VK_A:
			this.pritisnutGumbLijevo = false;
			break;
		case KeyEvent.VK_S:
			this.pritisnutGumbDolje = false;
			break;
		case KeyEvent.VK_D:
			this.pritisnutGumbDesno = false;
			break;
		default:
		}
	}

}

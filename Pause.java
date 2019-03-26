package SaueSpillet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Pause extends MouseAdapter {

	private Game game;
	private Handler handler;

	public Pause(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (Game.gameState == Game.State.Pause) {
			if (Menu.mouseOver(mx, my, 330, 350, 200, 64)) {
				Game.gameState = Game.State.Game;
			}
		}
	}
	

	public void mouseReleased(MouseEvent e) {

	}

	public void tick() {
	
	}

	public void render(Graphics g) {
		Font fnt = new Font("arial", 1, 90);
		Font fn2 = new Font("arial", 1, 50);
		g.setColor(Color.red);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		g.setFont(fnt);
		g.setColor(Color.white);
		g.drawString("PAUSE", 280, 250);
		
		
		g.setFont(fn2);
		g.drawRect(330, 350, 200, 64);
		g.drawString("Back", 370, 400);
		


	}
}

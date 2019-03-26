package SaueSpillet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class Help extends MouseAdapter {

	private Game game;
	private Handler handler;
	private Image backgroundPic;
	private Image helpPic;

	public Help(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		loadpic();
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (Game.gameState == Game.State.Help) {
			if (Menu.mouseOver(mx, my, 100, 400, 200, 64)) {
				Game.gameState = Game.State.Menu;
			}
		}
	}
	

	public void mouseReleased(MouseEvent e) {

	}
	
	public void loadpic() {
		backgroundPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\sheeps.png")
						.getImage();
		helpPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\Help.png")
						.getImage();
	}

	public void tick() {
	
	}

	public void render(Graphics g) {
		Font fnt = new Font("arial", 1, 50);
		Font fn2 = new Font("arial", 1, 30);
		g.drawImage(backgroundPic, 0,0,null);
		g.drawImage(helpPic, 800, 0, null);
		
		g.setColor(Color.white);
		
		g.setFont(fn2);
		g.drawString("Du er en taper,", 100, 280);
		g.drawString("kun tapere leser hjelp siden", 100, 330);
		
		Graphics2D g2d = (Graphics2D) g;
		g.setFont(fnt);
		g2d.setComposite(Menu.makeTransparent(0.5f));
		g.setColor(new Color(210,105,30));
		g.fillRect(100, 400, 200, 64);
		g2d.setComposite(Menu.makeTransparent(1));
		g.setColor(new Color(124,252,0));
		g.drawString("Back", 140, 450);
	}
}

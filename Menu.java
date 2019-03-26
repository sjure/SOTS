package SaueSpillet;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class Menu extends MouseAdapter {

	private Game game;
	private Handler handler;
	private Image backgroundPic;

	public Menu(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		loadpic();
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (Game.gameState == Game.State.Menu) {
			// Play button
			if (mouseOver(mx, my, 80, 250, 200, 64)) {
				Game.gameState = Game.State.Game;
				handler.addObject(new Player(10, 300, ID.Player, handler, 0));
				handler.clearEnemys();

				// handler.addObject(new BasicEnemy(200, 200, ID.BasicEnemy, handler));
			}
			// Quit button
			if (mouseOver(mx, my, 300, 350, 200, 64)) {
				System.exit(1);
			}
			// Help button
			if (mouseOver(mx, my, 300, 250, 200, 64)) {
				Game.gameState = Game.State.Help;
			}
			if (mouseOver(mx, my, 80, 350, 200, 64)) {
				handler.object.add(new MenuSheep(100, 350, ID.MenuSheep, handler));
			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public static boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	public void tick() {

	}

	public void loadpic() {
		backgroundPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\sheeps.png")
						.getImage();
	}

	static AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return AlphaComposite.getInstance(type, alpha);

	}

	public void render(Graphics g) {
		g.drawImage(backgroundPic, 0, 0, null);
		Font fnt = new Font("arial", 1, 50);

		int xValue = 80;
		int yValue = 250;
		g.setFont(fnt);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(0.5f));
		g.setColor(new Color(210, 105, 30));
		g.fillRect(300, 250, 200, 64);
		g.fillRect(xValue, 350, 200, 64);
		g.fillRect(xValue, yValue, 200, 64);
		g.fillRect(300, 350, 200, 64);
		g2d.setComposite(makeTransparent(1));

		g.setColor(new Color(124, 252, 0));
		g.drawString("Play!", xValue + 45, yValue + 50);
		g.drawString("HELP!!", 325, 300);
		g.drawString("Sheep", xValue + 30, 400);
		g.drawString("QUIT", 345, 400);

	}
}

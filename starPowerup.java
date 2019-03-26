package SaueSpillet;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class starPowerup extends GameObject {

	private int width = 50;
	private int height = 48;
	public static Image starPic;
	private boolean flimmer = false;
	private long start, end;
	private int fuse = 420;
	
	public starPowerup(float x, float y, ID id, Handler handler) {
		super(x, y, id, handler);
		loadpic();
		
		handler.addObject(new Trail((int) x -35, (int) y-30, ID.InitBoom, handler, 0.025f));
		start = System.currentTimeMillis();
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void tick() {

		x = Game.clamp(x, 0, Game.WIDTH-300);
		y = Game.clamp(y, 200, Game.HEIGHT - 132);
		fuse--;
		if (fuse < 50 && fuse %10 == 0) {
			flimmer = true;
			start = System.currentTimeMillis();
		}
		if (fuse <=0)
			handler.removeObject(this);

	}

	public void loadpic() {
		starPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\star.png")
						.getImage();
	}


	public void render(Graphics g) {
		end = System.currentTimeMillis();
		if (!((end - start) / 10 < 5)) {
			if ((end - start) / 10 > 10 && flimmer) {
				start = System.currentTimeMillis();
				flimmer = false;
			}
			g.drawImage(starPic, (int) x, (int) y, null);
		}
	}

}

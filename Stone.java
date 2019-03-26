package SaueSpillet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Stone extends GameObject {

	private int width = 48;
	private int height = 31;
	private double gravity = 0.2;
	private Image stonePic;
	
	public Stone(float x, float y, ID id, Handler handler, float velX, int hit) {
		super(x, y, id, handler, hit);
		this.velX = velX;
		loadpic();
		this.velY = (float) (-velX*1.4);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void loadpic() {
		stonePic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\stone.png")
						.getImage();
	}

	public void tick() {
		velY += gravity;
		x += velX;
		y += velY;
		if (this.hit > Hud.getEnemysPerStone()) {
			handler.removeObject(this);
		}
		if (y >= Game.HEIGHT - 58 || x >= Game.WIDTH)
			handler.object.remove(this);
		handler.addObject(new Trail((int) x, (int) y, ID.Trail, handler, width, height, 0.11, Color.LIGHT_GRAY, 1f));
	}

	public void render(Graphics g) {
		g.drawImage(stonePic, (int) x, (int) y, null);
	}

}
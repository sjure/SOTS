package SaueSpillet;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;


import javax.swing.ImageIcon;

public class StonesPickup extends GameObject {

	private int width = 70;
	private int height = 70;
	public static Image stonesPic;
	private int fuse = 600;

	
	public StonesPickup(float x, float y, ID id, Handler handler) {
		super(x, y, id, handler);
		loadpic();
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void tick() {
		x = Game.clamp(x, 0, Game.WIDTH-300);
		y = Game.clamp(y, 200, Game.HEIGHT - 132);
		
		fuse--;
		if (fuse <=0)
			handler.removeObject(this);

	}

	public void loadpic() {
		stonesPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\stones.png")
						.getImage();
	}


	public void render(Graphics g) {
		g.drawImage(stonesPic, (int) x, (int) y, null);
		
	}

}

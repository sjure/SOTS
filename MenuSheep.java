package SaueSpillet;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class MenuSheep extends GameObject {

	private int width = 16;
	private int height = 16;
	private Image sheepPic;
	
	Random r = new Random();
	
	int dir = 0;
	
	public MenuSheep(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		loadpic();
		dir = r.nextInt(3);
		if (dir == 0) {
			velX = 2;
			velY = 6;
			
		} else if (dir==1) {
			velX = 6;
			velY = 2;
		} else if (dir ==2) {
			velX = 1 + r.nextInt(7);
			velY = 1 + r.nextInt(7);
		}
		

	}

	public Rectangle getBounds() {
		return new Rectangle((int) x,(int) y, width, height);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		if (y <= 0 || y >= Game.HEIGHT - 110) 
			velY *= -1;
		if (x <= 0 || x >= Game.WIDTH - 100) 
			velX *= -1;

	}
	
	public void loadpic() {
		sheepPic = new ImageIcon("C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\sheep.png").getImage();
	}

	public void render(Graphics g) {
		
		g.drawImage(sheepPic, (int) x, (int) y, null);
		
	}

}

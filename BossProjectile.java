package SaueSpillet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;


public class BossProjectile extends GameObject {

	private int width = 70;
	private int height = 20;
	private Random r;
	private GameObject player;
	private static Image projectilePic;
	
	public BossProjectile(float x, float y, ID id, Handler handler, float velY) {
		super(x, y, id, handler);
		r = new Random();
		velX = -3;
		this.velY = velY;
		loadpic();
		for (int i =0; i< handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player) player = handler.object.get(i);
		}
		handler.addObject(new Trail((int) x - 50, (int) y - 20, ID.InitBoom, handler, 0.025));
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void tick() {

		r = new Random();
		x += velX;
		y += velY;
		
		float diffX = x -player.getX();
		float diffY = y - player.getY();
		double distance = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
		
		velY += (float) ((-diffY*0.02/distance));
		
		if (hit == 1) {
			handler.removeObject(this);
		}
		if (x<-70) {
			handler.removeObject(this);
		}
		if (y<-10 || y > Game.HEIGHT) handler.removeObject(this);
		
		handler.addObject(new Trail((int) x+40, (int) y, ID.Trail, handler, 20, height, 0.01, Color.red, 0.5f));

	}

	public void loadpic() {
		projectilePic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\laser.png")
						.getImage();
	}


	public void render(Graphics g) {
		g.drawRect((int)x,(int) y, width, height);
		g.drawImage(projectilePic, (int) x, (int) y, null);
		
	}

}

package SaueSpillet;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;

public class BasicFarmer extends GameObject {

	private int width = 68;
	private int height = 100;
	private Random r;
	private static Image farmerPic;
	private int health;
	private boolean flimmer = false;
	private long start, end;
	private GameObject stoneHit;
	public static ArrayList<ID> hitList = new ArrayList<ID>(Arrays.asList(ID.Stone));

	public BasicFarmer(float x, float y, ID id, Handler handler, int hit, int health) {
		super(x, y, id, handler, hit);
		r = new Random();
		velX = (float) (-0.8 - r.nextFloat()/1.5);
		velY = 0;
		this.health = health;
		loadpic();
		handler.addObject(new Trail((int) x - 50, (int) y - 20, ID.InitBoom, handler, 0.025));
		start = System.currentTimeMillis();
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void tick() {
		r = new Random();
		x += velX;
		// y += velY;
		if (r.nextInt(5) == 1) {
			y += 5 - r.nextInt(10);
		}

		if (y <= 0 || y >= Game.HEIGHT - 131)
			velY *= -1;
		if (x < 0) {
			Hud.health -= 4;
			handler.removeObject(this);
		}
		// if (x <= 0 || x >= Game.WIDTH)
		// velX *= -1;

		// x = Game.clamp(x, 0, Game.WIDTH);
		y = Game.clamp(y, 0, Game.HEIGHT - 132);

		collision();
		if (hit == 1) {
			hit = 0;
			health -= 10;
		}

		if (health < 1) {
			handler.removeObject(this);
			Hud.increaseKills();
		}

	}

	public void loadpic() {
		farmerPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\farmer.png")
						.getImage();
	}

	private void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject obj = handler.object.get(i);
			for (ID idi : hitList) {
				if (obj.getId() == idi) {
					if (getBounds() != null && obj.getBounds() != null) {
						if (getBounds().intersects(obj.getBounds()) && obj != stoneHit) {
							health -= Hud.stoneDamage;
							stoneHit = obj;
							obj.increaseHit();
							Hud.score += 5;
							if (health > 0) {
								start = System.currentTimeMillis();
								flimmer = true;
							}
						}
					}
				}
			}
		}
	}

	public void render(Graphics g) {
		end = System.currentTimeMillis();
		if (!((end - start) / 10 < 5)) {
			if ((end - start) / 10 > 10 && flimmer) {
				start = System.currentTimeMillis();
				flimmer = false;
			}
			//g.drawRect((int) x, (int) y, width, height);
			g.drawImage(farmerPic, (int) x, (int) y, null);
		}
	}

}

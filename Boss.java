package SaueSpillet;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;

public class Boss extends GameObject {

	private int width = 300;
	private int height = 236;
	private Random r;
	private static Image bossPic;
	private int health;
	private boolean flimmer = false;
	private long start, end;
	private GameObject stoneHit;
	private int timer1, timer2, timer3;
	public static ArrayList<ID> hitList = new ArrayList<ID>(Arrays.asList(ID.Stone));

	public Boss(float x, float y, ID id, Handler handler, int hit, int health) {
		super(x, y, id, handler, hit);
		velX = -1;
		velY = 0;
		timer1 = 220;
		timer2 = 100;
		timer3 = 120;
		this.health = health;
		loadpic();
		start = System.currentTimeMillis();
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void tick() {

		r = new Random();

		if (timer1 > 0) {
			timer1--;
			x += velX;
			Hud.stoneDamage = 3;
		} else if (timer2 > 0) {
			timer2--;
			if (r.nextInt(40) ==1) {
				spawnLasers();
			}
		} else if (timer3 > 0) {
			timer3--;
			x += velX;
		} else {
			Hud.stoneDamage = 10;
			if (r.nextInt(100) == 1) {
				spawnLasers();
			} else if (r.nextInt(280)==1) {
				handler.addObject(new BasicFarmer(x + r.nextInt(200), y + 200 - r.nextInt(500), id, handler, 0, Hud.basicHealth));
			}
		}
		if (hit ==1) {
			hit =0;
			health -= 20;
		}
		y += velY;

		if (y <= 0 || y >= Game.HEIGHT - 131)
			velY *= -1;

		if (x < 0) {
			Hud.health -= 4;
			handler.removeObject(this);
		}

		collision();
		if (hit == 1) {
			hit = 0;
			start = System.currentTimeMillis();
			flimmer = true;
			health -= 100;
		}

		if (health < 1) {
			Hud.stoneDamage = 10;
			handler.removeObject(this);
			Hud.increaseKills();
		}

	}
	
	public void spawnLasers() {
		handler.addObject(new BossProjectile(x, y+40, ID.BossProjectile, handler, (0.5f-r.nextFloat())*2));
		handler.addObject(new BossProjectile(x, y+80, ID.BossProjectile, handler, (0.5f-r.nextFloat())*5));
		handler.addObject(new BossProjectile(x, y+120, ID.BossProjectile, handler, (0.5f-r.nextFloat())*10));
		
		
	}
	
	public void loadpic() {
		bossPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\BossLaser.png")
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
			// g.drawRect((int) x, (int) y, width, height);
			g.drawImage(bossPic, (int) x, (int) y, null);
		}
	}

}

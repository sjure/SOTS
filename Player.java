package SaueSpillet;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;

public class Player extends GameObject {

	private Image playerPic;
	private Image NUKEPic;
	private int width = 90; // 100
	private int height = 65; // 71
	GameObject lastEnemy;
	private int stoneDamage;
	private int enemysPerStone;
	private long start, end,start2,end2;
	private boolean flimmer = false;
	private Random r;
	private boolean powerInit = true;
	private int nukeTimerCount,nukeTimerCount2 = 0;
	private boolean nukeInit;
	public static ArrayList<ID> enemyList = new ArrayList<ID>(
			Arrays.asList(ID.BasicFarmer, ID.AdvancedFarmer, ID.Boss, ID.BossProjectile));
	public static ArrayList<ID> powerList = new ArrayList<ID>(Arrays.asList(ID.Star, ID.Nuke, ID.StonesPickup));

	public Player(float x, float y, ID id, Handler handler, int hit) {
		super(x, y, id, handler, hit);
		velX = 0;
		velY = 0;
		loadpic();
		r = new Random();
		start = System.currentTimeMillis();
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void tick() {
		x += velX;
		y += velY;
		x = Game.clamp(x, 0, Game.WIDTH - 220);
		y = Game.clamp(y, 115, Game.HEIGHT - 110);

		if (Hud.powerUP && powerInit) {
			this.stoneDamage = Hud.stoneDamage;
			this.enemysPerStone = Hud.enemysPerStone;
			Hud.stoneDamage = 100;
			Hud.enemysPerStone = 100;
			start = System.currentTimeMillis();
			powerInit = false;
		}
		end = System.currentTimeMillis();
		if ((end - start) / 1000 > 10 && Hud.powerUP) {
			Hud.powerUP = false;
			Hud.stoneDamage = this.stoneDamage;
			Hud.enemysPerStone = this.enemysPerStone;
			powerInit = true;
		}

		collision();
		collisionPower();

		// handler.addObject(new Trail((int) x, (int) y, ID.Trail, handler, width,
		// height, 0.15, Color.white));
	}

	private void collision() {
		for (int i = 0; handler.object.size() > i; i++) {
			GameObject obj = handler.object.get(i);
			for (ID idi : enemyList) {
				if (obj.getId() == idi) {
					if (getBounds() != null && obj.getBounds() != null) {
						if (getBounds().intersects(obj.getBounds()) && obj != lastEnemy) {
							if (obj.getId() == ID.BossProjectile) {
								Hud.health -= 1;
							} else {
								Hud.health -= 3;
							}
							start2 = System.currentTimeMillis();
							flimmer = true;
							obj.setHit(1);
							lastEnemy = obj;
						}
					}
				}
			}
		}
	}

	private void collisionPower() {
		for (int i = 0; handler.object.size() > i; i++) {
			GameObject obj = handler.object.get(i);
			for (ID idi : powerList) {
				if (obj.getId() == idi) {
					if (getBounds() != null && obj.getBounds() != null) {
						if (getBounds().intersects(obj.getBounds())) {
							if (obj.getId() == ID.Star) {
								Hud.powerUP = true;
								handler.removeObject(obj);
							}
							if (obj.getId() == ID.Nuke) {
								nukeTimerCount = 60;
								nukeTimerCount2 = 800;
								Hud.nuked = true;
								nukeInit = true;
								handler.clearEnemys();
								handler.removeObject(obj);
							}
							if (obj.getId() == ID.StonesPickup) {
								Hud.stoneCount += 5;
								handler.removeObject(obj);
							}
						}
					}
				}
			}
		}
	}

	public void loadpic() {
		playerPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\sheep.png")
						.getImage();
		NUKEPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\nuke2.png")
						.getImage();
	}

	public void render(Graphics g) {
		// g.drawRect((int)x,(int) y,width,height);
		end2 = System.currentTimeMillis();
		if (!((end2 - start2) / 10 < 5)) {
			if ((end2 - start2) / 10 > 10 && flimmer) {
				start2 = System.currentTimeMillis();
				flimmer = false;
			}
			//g.drawRect((int) x, (int) y, width, height);
			g.drawImage(playerPic, (int) x, (int) y, null);
		}
		
		if (Hud.nuked) {
			if (nukeInit) {
				nukeInit = false;
				handler.addObject(new Trail(0, 0, ID.NukeBoom, handler, 0.005, NUKEPic));
				for (int i = 0; i < 2; i++) {
					handler.addObject(new Trail(Game.WIDTH - 400 + r.nextInt(300), r.nextInt(Game.HEIGHT), ID.InitBoom,
							handler, 0.01));

				}

			}
			if (nukeTimerCount > 0) {
				nukeTimerCount--;
				handler.addObject(new Trail(Game.WIDTH - 400 + r.nextInt(300), r.nextInt(Game.HEIGHT), ID.InitBoom,
						handler, 0.01));

			} else if (nukeTimerCount2>0) {
				nukeTimerCount2--;
			} else {
				Hud.nuked = false;
			}
		}

	}

}

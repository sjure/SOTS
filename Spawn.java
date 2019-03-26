package SaueSpillet;

import java.util.Random;

public class Spawn {

	private Handler handler;
	private Hud hud;
	private Random r = new Random();
	private int rawScore = 0;
	private int enemys;
	private int stars;
	private boolean init = true;

	public Spawn(Handler handler, Hud hud) {
		this.handler = handler;
		this.hud = hud;
		init = true;
	}

	public void tick() {
		rawScore++;

		if (r.nextInt((int) Game.clamp(3000 - hud.getLevel() * 10, 300, 10000)) == 1 && starCount() < 2) {
			handler.addObject(new starPowerup(500 - r.nextInt(500), 500 - r.nextInt(250), ID.Star, handler));
		}
		if (r.nextInt(300) == 1) {
			handler.addObject(new StonesPickup(500 - r.nextInt(500), 500 - r.nextInt(250),ID.StonesPickup, handler));
		}
		if (r.nextInt((int) Game.clamp(10000 - hud.getLevel() * 30, 300, 10000)) == 1) {
			handler.addObject(new NukePowerup(800 - r.nextInt(500), 500 - r.nextInt(250), ID.Nuke, handler));
		}
		
		if (hud.getLevel() == 1 && init) {
			handler.addObject(new BasicFarmer(900, 400, ID.BasicFarmer, handler, 0, Hud.basicHealth));
			init = false;
		}
		
		if (rawScore >= 200 && enemyCount() == 0 && !Hud.nuked) {
			Hud.advancedHealth += 2;
			Hud.basicHealth++;
			rawScore = 0;
			hud.setLevel(hud.getLevel() + 1);
			if (hud.getLevel() % 10 == 0) {
				Hud.bossHealth = hud.getLevel() * 15;
				handler.addObject(new Boss(1000, 250, ID.Boss, handler, 0, Hud.bossHealth));
			} else {

				int spawnCount = Math.round(hud.getLevel() / 15);

				for (int i = 0; i < spawnCount; i++) {
					for (int k = 0; k < 15; k++) {
						spawnEnemy(150 * i, 150);
					}
				}
				for (int i = 0; i < hud.getLevel() % 15; i++) {
					spawnEnemy(0, 150);
				}
			}
		}
	}

	public void spawnEnemy(int offset, int var) {
		if (r.nextInt(4) == 0) {
			handler.addObject(new AdvancedFarmer(offset + 850 + r.nextInt(var), r.nextInt(Game.HEIGHT - 100),
					ID.AdvancedFarmer, handler, 0, Hud.advancedHealth));
		} else {
			handler.addObject(new BasicFarmer(offset + 850 + r.nextInt(var), r.nextInt(Game.HEIGHT - 100),
					ID.BasicFarmer, handler, 0, Hud.basicHealth));
		}
	}

	public int enemyCount() {
		enemys = 0;
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject obj = handler.object.get(i);
			for (ID id : Player.enemyList) {
				if (obj.getId() == id) {
					enemys += 1;
				}

			}
		}
		return enemys;
	}

	public int starCount() {
		stars = 0;
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject obj = handler.object.get(i);
			if (obj.getId() == ID.Star) {
				stars += 1;

			}
		}
		return stars;
	}
}

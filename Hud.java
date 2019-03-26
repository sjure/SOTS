package SaueSpillet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Hud {

	public static int health = 10;
	private int greenValue = 255;

	public static int score = 0;
	private int level = 0;
	private int fps;
	private static int kills = 0;
	public static boolean powerUP = false;
	public static int enemysPerStone = 2;
	public static int stoneCount = 10;
	public static int stoneDamage = 10; 
	public static int basicHealth = 6;
	public static int bossHealth = 200;
	public static double displayPower;
	public static boolean nuked = false;
	public static int advancedHealth = 16;

	public void tick() {
		greenValue = health * 20;
		health = (int) Game.clamp(health, 0, 10);
		greenValue = (int) Game.clamp(greenValue, 0, 255);
		
	}

	public void render(Graphics g) {
		Font fnt = new Font("arial", 1, 12);
		Font fnt2 = new Font("arial", 1, 30);
		g.setFont(fnt); // rendering health
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 12);
		g.setColor(new Color(80, greenValue, 0));
		g.fillRect(15, 15, health * 20, 12);
		g.setColor(Color.lightGray);
		g.drawRect(15, 15, health * 20, 12);
		//Power render
		int powX = 15;
		int powY = 32;
		g.setColor(Color.gray);
		g.fillRect(powX, powY, (int) (7.5*80/3), 12);
		g.setColor(Color.red);
		g.fillRect(powX, powY, (int) (displayPower* 80/3), 12);
		g.setColor(Color.lightGray);
		g.drawRect(powX, powY, (int) (displayPower* 80/3), 12);
		
		g.setColor(Color.darkGray);
		g.drawString("Score: " + score, 15, 64);
		g.drawString("Level: " + level, 15, 80);
		g.drawString((stoneCount==1 ? "Stone: " : "Stones: ") + stoneCount, 15, 96);
		
		g.drawString("FPS: " + fps, 15, 112);
		
		if (Hud.powerUP && System.currentTimeMillis()/100 %2 == 0) {
			g.drawImage(starPowerup.starPic, 900, 16 ,null);
			
		}
	}

	public static int getEnemysPerStone() {
		return enemysPerStone;
	}
	public static void increaseKills() {
		kills++;
	}

	public int getKills() {
		return kills;
	}

	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

}

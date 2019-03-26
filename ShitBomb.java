package SaueSpillet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

public class ShitBomb extends GameObject {

	private int width = 300;
	private int height = 300;
	private Image shitPic;
	private int fuse;
	public static ArrayList<ID> Entitys = new ArrayList<ID>(
			Arrays.asList(ID.Player, ID.AdvancedFarmer, ID.BasicFarmer, ID.Star, ID.Boss, ID.BossProjectile, ID.StonesPickup, ID.Nuke));

	public ShitBomb(float x, float y, ID id, Handler handler, int fuse) {
		super(x, y, id, handler);
		this.fuse = fuse;
		loadpic();

		Game.clamp(x, 0, Game.WIDTH);
		Game.clamp(y, 0, Game.HEIGHT);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x - 130, (int) y - 120, width, height);
	}

	public void loadpic() {
		shitPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\shit.png")
						.getImage();
	}

	public void tick() {
		fuse--;
		
		if (fuse <= 0) {
			collisionExp();
			handler.addObject(new Trail((int) x - 170, (int) y - 100, ID.ShitBoom, handler, 0.028));
			handler.object.remove(this);
		}

	}

	private void collisionExp() {
		for (int i = 0; handler.object.size() > i; i++) {
			GameObject obj = handler.object.get(i);
			if (Entitys.contains(obj.getId())) {
				if (getBounds() != null && obj.getBounds() != null) {
					if (getBounds().intersects(obj.getBounds())) {
						Hud.score += 5;
						if (obj.getId() == ID.Player) {
							Hud.health -= 3;
						} else if (obj.getId() == ID.Boss) {
							obj.hit = 1;
						}
						else if (obj.getId() == ID.Star) {
							handler.removeObject(obj);
							i--;
						} else if (obj.getId() == ID.AdvancedFarmer || obj.getId() == ID.BasicFarmer) {
							handler.removeObject(obj);
							i--;
						} else {
							handler.removeObject(obj);
							i--;
						}

					}
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		//g.drawRect((int) x - 130, (int) y - 120, width, height);
		g.drawImage(shitPic, (int) x, (int) y, null);
	}

}
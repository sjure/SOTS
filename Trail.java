package SaueSpillet;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Trail extends GameObject {

	private float alpha;
	private Color color;
	private int width, height;
	private double life;
	private Image BoomPic;
	private Image ExpPic;

	public Trail(int x, int y, ID id, Handler handler, int width, int height, double life, Color color, float alpha) {
		super(x, y, id, handler);
		this.width = width;
		this.height = height;
		this.life = life;
		this.color = color;
		this.alpha = alpha;

	}
	
	public Trail(int x, int y, ID id, Handler handler, double life) {
		super(x, y, id, handler);
		this.life = life;
		this.alpha = 1;
		if (id == ID.InitBoom) {
			loadBOOMPic();
		}
		if (id == ID.ShitBoom) {
			loadShitPic();
		}
	}
	
	public Trail(int x, int y, ID id, Handler handler, double life , Image pic) {
		super(x, y, id, handler);
		this.life = life;
		this.alpha = 1;
		this.BoomPic = pic;
	}
	public void loadBOOMPic() {
		BoomPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\boom.png")
				.getImage();
	}
	public void loadShitPic() {
		ExpPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\exp.png")
				.getImage();
	}
	
	public void tick() {
		if(alpha > life) {
			alpha -= life- 0.001;
		} else handler.removeObject(this);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		if (BoomPic == null && ExpPic == null) {
		g.setColor(color);
		g.fillRect((int) x,(int)  y, width, height);
		} else if (ExpPic == null){
			g.drawImage(BoomPic, (int) x, (int) y, null); 
		} else {
			g.drawImage(ExpPic, (int) x, (int) y, null);
		}
		g2d.setComposite(makeTransparent(1));
	}

	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return AlphaComposite.getInstance(type, alpha);

	}
	
	public Rectangle getBounds() {
		return null;
	}

}

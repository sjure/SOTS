package SaueSpillet;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

	protected float x, y;
	protected ID id;
	protected float velX, velY;
	protected int hit;
	Handler handler;
	
	public GameObject(float x, float y, ID id,Handler handler, int hit) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.hit = 0;
		this.handler = handler;
	}
	
	public GameObject(float x, float y, ID id,Handler handler) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.handler = handler;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	
	public int isHit() {
		return hit;
	}
	public void increaseHit() {
		hit ++;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	
}

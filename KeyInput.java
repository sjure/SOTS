package SaueSpillet;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private int xp, xm, yp, ym;
	private long start, end;
	private long start2, end2;
	private boolean spacePress, shiftPress = false;
	private double power;

	public KeyInput(Handler handler) {
		this.handler = handler;

	}
	
	public void tick() {
		end = System.currentTimeMillis();
		double pow = ((double) (end-start) /30);
		if (spacePress && Hud.stoneCount >0) {
			if (pow >=7.5) {
				Hud.displayPower = 7.5;
			} else {
				Hud.displayPower = pow;
			}
		} else {
			Hud.displayPower = 0;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (Game.gameState == Game.State.Pause) {
			if (key == KeyEvent.VK_P)
				Game.gameState = Game.State.Game;
		} else if (Game.gameState == Game.State.Game) {
			if (key == KeyEvent.VK_P)
				Game.gameState = Game.State.Pause;
			int v = 7;
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject obj = handler.object.get(i);
				if (obj.getId() == ID.Player) {
					// Key events for player 1
					if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
						ym = v;
					if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
						xp = v;
					if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
						xm = v;
					if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
						yp = v;
					obj.setVelX(xp - xm);
					obj.setVelY(yp - ym);
					if (key == KeyEvent.VK_SHIFT) {
						if (!shiftPress) {
							end2 = System.currentTimeMillis();
							if ((end2 - start2) / 1000 > 5) {
								handler.object
										.add(new ShitBomb(obj.getX(), obj.getY() + 30, ID.ShitBomb, handler, 150));
								start2 = System.currentTimeMillis();
								shiftPress = true;
							}

						}
					}
					if (key == KeyEvent.VK_SPACE) { // Shooting stones power
						if (!spacePress) {
							start = System.currentTimeMillis();
							spacePress = true;

						}

					}
				}
			}
		}

		if (key == KeyEvent.VK_ESCAPE)
			System.exit(1);
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject obj = handler.object.get(i);
			if (obj.getId() == ID.Player) {
				// Key events for player 1
				if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
					ym = 0;
				if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
					xp = 0;
				if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
					xm = 0;
				if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
					yp = 0;
				obj.setVelX(xp - xm);
				obj.setVelY(yp - ym);
				if (key == KeyEvent.VK_SHIFT) {
					shiftPress = false;
				}
				if (key == KeyEvent.VK_SPACE) {
					if (spacePress) {
						spacePress = false;
						end = System.currentTimeMillis();
						power =  ((double) (end-start) /30);
						if (power >= 7.5) {
							power = 7.5;
						}
						if (Hud.stoneCount > 0) {
							Hud.stoneCount--;
							handler.object
									.add(new Stone(obj.getX() + 50, obj.getY() + 50, ID.Stone, handler,(float) power, 0));

						}
					}

				}

			}
		}
	}

}

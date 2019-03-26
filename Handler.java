package SaueSpillet;

import java.awt.Graphics;
import java.util.ArrayList;

public class Handler {

	ArrayList<GameObject> object = new ArrayList<GameObject>();

	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			GameObject gameobj = object.get(i);
			gameobj.tick();
		}
	}

	public void render(Graphics g) { // begynte bakerst på grunn av trail
		for (int i = object.size() - 1; i >= 0; i--) {
			GameObject gameObj = object.get(i);
			if (gameObj != null) {
				gameObj.render(g);
			}
		}

	}

	public void clearEnemys() {
		for (int i = 0; i < object.size(); i++) {
			GameObject gameObj = object.get(i);

			if (gameObj.getId() == ID.Player && Game.gameState == Game.State.End) {
				object.remove(gameObj);
				i--;
			}

			if (gameObj.getId() != ID.Player) {
				object.remove(gameObj);
				i--;
			}

		}
	}

	public void addObject(GameObject object) {
		this.object.add(object);
	}

	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

}

package SaueSpillet;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.ImageIcon;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 1000, HEIGHT = 560; // = WIDTH / 12 * 9;
	
	private Thread thread;
	private boolean running = false;
	private Random r;
	
	private Handler handler;
	private Hud hud;
	private Spawn spawner;
	private Menu menu;
	private Help help;
	private End end;
	private Pause pause;
	private KeyInput key;

	private Image backgroundPic;

	public enum State {
		Menu, Help, Game, End, Pause;
	}

	public static State gameState = State.Menu;

	public Game() {
		System.out.println("\n\t Survival Of The Sheepest\n\nMade By: \n\n\n \t Sjur Brekke Espedal \n\n\nI do not own the rights to the images in this game \n\n " + "------------------------------------------") ;
		handler = new Handler();
		initStates();
		spawner = new Spawn(handler, hud);
		this.addKeyListener(key);
		new Window(WIDTH, HEIGHT, "Survival Of The Sheepest", this);
		loadpic();
		
		r = new Random();

		if (gameState == State.Game) {
			handler.addObject(new Player(100, 300, ID.Player, handler, 0));
			handler.addObject(new starPowerup(200, 300, ID.Star, handler));
			handler.addObject(new NukePowerup(500, 250, ID.Nuke, handler));
			handler.addObject(new StonesPickup(200,200,ID.StonesPickup, handler));
			handler.addObject(new BasicFarmer(500, 100, ID.BasicFarmer, handler, 0, Hud.basicHealth));
			//handler.addObject(new Boss(1000, 250, ID.Boss,handler, 0, Hud.bossHealth));
			
		}
	}

	public void initStates() {
		menu = new Menu(this, handler);
		help = new Help(this, handler);
		hud = new Hud();
		end = new End(this, handler, hud);
		pause = new Pause(this, handler);
		key = new KeyInput(handler);
		this.addMouseListener(menu);
		this.addMouseListener(help);
		this.addMouseListener(end);
		this.addMouseListener(pause);
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				hud.setFps(frames);
				frames = 0;
			}
		}
		stop();

	}

	private void tick() {
		if (gameState == State.Game) {
			handler.tick();
			hud.tick();
			spawner.tick();
			key.tick();
			if (Hud.health <= 0) {
				Hud.health = 100;
				gameState = State.End;
				End.init = true;
				handler.object.clear();
			}
		} else if (gameState == State.Menu) {
			menu.tick();
			handler.tick();
		} else if (gameState == State.Help) {
			help.tick();
			handler.tick();
		} else if (gameState == State.End) {
			end.tick();
		} else if (gameState == State.Pause) {
			pause.tick();
		}

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.red);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Background rendering
		if (gameState == State.Game) {
			g.drawImage(backgroundPic, 0, 0, null);
		} else if (gameState == State.Menu) {
			menu.render(g);
		} else if (gameState == State.Help) {
			help.render(g);
		}
		handler.render(g);

		// HUD rendering
		if (gameState == State.Game) {
			hud.render(g);
		}  else if (gameState == State.End) {
			end.render(g);
		} else if (gameState == State.Pause) {
			pause.render(g);
		}

		g.dispose();
		bs.show();

	}

	public void loadpic() {
		backgroundPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\backgroundv2.png")
						.getImage();
	}

	public static float clamp(float var, float min, float max) {
		if (var >= max)
			return max;
		if (var <= min)
			return min;
		else {
			return var;
		}
	}

	public static void main(String[] args) {
		new Game();
	}

}

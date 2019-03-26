package SaueSpillet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class End extends MouseAdapter {

	private Game game;
	private Handler handler;
	private Hud hud;
	private static int highscore = 0;
	public static boolean init;
	private Image endPic;
	public boolean newHighscore = false;

	public End(Game game, Handler handler, Hud hud) {
		this.game = game;
		this.hud = hud;
		this.handler = handler;
		loadpic();
		
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (Game.gameState == Game.State.End) {
			if (Menu.mouseOver(mx, my, 400, 350, 200, 64)) {
				Game.gameState = Game.State.Menu;
				hud.setLevel(1);
				Hud.score = 0;

			}
		}
	}

	public static int getHighscore(){
		Scanner in = null;
		try {
			try {
			in = new Scanner(new FileReader("src\\SaueSpillet\\playerScores.txt"));
			} catch (Exception e) {
				System.err.println(e);
			}
			if (in == null)
			in = new Scanner(new FileReader("playerScores.txt"));
			if (in.hasNext()) {
				highscore = Integer.valueOf(in.nextLine().split(" ")[1]);
			}
			in.close();
			return highscore;
		} catch (FileNotFoundException e) {
			System.err.println("Error when reading from file " + e);
		}
		return highscore;
	}

	public static void setHighscore(int score) {
		highscore = score;
		try {
			PrintWriter outFile = null;
			try {
				outFile = new PrintWriter("src\\SaueSpillet\\playerScores.txt");
			} catch (Exception e) {
				System.err.println(e);
			}
			if (outFile == null)
				outFile = new PrintWriter("playerScores.txt");
			outFile.println("Highscore: " + score);
			outFile.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error writing file " + e);
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void loadpic() {
		endPic = new ImageIcon(
				"C:\\Users\\Sjur\\tdt4100-2018-master\\git\\tdt4100-2018\\minegenkode\\GamepackRes\\end.png")
						.getImage();
	}

	public void tick() {
		if (init) {
			newHighscore = false;
			try {
				highscore = getHighscore();
			} catch (IllegalAccessError e) {
				highscore = 0;
			}
			init = false;
			System.out.println(hud.getScore());
			System.out.println(highscore);
			if (hud.getScore() > highscore) {
				newHighscore = true;
				End.highscore = hud.getScore();
				setHighscore(hud.getScore());
			}
		}

	}

	public void render(Graphics g) {
		Font fnt = new Font("arial", 1, 50);
		Font fn2 = new Font("arial", 1, 30);
		Font fn3 = new Font("arial", 1, 35);
		Font fn4 = new Font("arial", 1, 60);

		g.drawImage(endPic, 0, 0, null);

		g.setColor(new Color(218, 165, 32));
		g.setFont(fn2);
		g.drawString("You Lost", 100, 200);
		g.drawString("Level: " + hud.getLevel(), 100, 250);
		g.drawString("Score: " + hud.getScore(), 100, 300);
		g.drawString("HighScore: " + End.highscore, 100, 400);
		g.drawString("Kills: " + hud.getKills(), 100, 350);

		g.setFont(fn3);
		g.drawString("Menu", 450, 395);
		g.setColor(new Color(255, 130, 0));
		g.drawRect(400, 350, 200, 64);

		g.setFont(fn4);
		g.setColor(Color.yellow);
		if (newHighscore) {
			g.drawString("NEW HIGHSCORE!!!", 320, 250);
		}
	}
}

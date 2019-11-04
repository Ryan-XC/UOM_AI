package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game.Game;
import game.IMainGame;

public class Finish implements IGameState {
	private final Game game;
	
	public Finish(Game game) {
		this.game = game;
	}

	@Override
	public void play() {
	
	}

	@Override
	public void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.BOLD, 14));
		g.drawString(game.getPoint() + " PTS | LVL " + game.getLevel(), 10, 20);

		g.setColor(new Color(0, 0, 0, 127));
		g.fillRect(0, 0, 1024, 768);

		g.setColor(new Color(0,255,60));
		g.setFont(new Font("SansSerif", Font.BOLD, 48));
		String s = "YOU WIN";
		g.drawString(s, (int) ((1024 - g.getFontMetrics().stringWidth(s)) / 2), 280);

		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.BOLD, 20));
		s = "Total Points: " + game.getPoint() + " | Level: "+ game.getLevel();
		g.drawString(s, (int) ((1024 - g.getFontMetrics().stringWidth(s)) / 2), 380);
		
		s = "Press Q to quit or R to restart";
		g.drawString(s, (int)((1024-g.getFontMetrics().stringWidth(s))/2), 420);	
	}

	@Override
	public void changeState() {
		if (game.isQuit())
			System.exit(0);
		else if (game.isRestart())
			game.restart();
	}

	

}

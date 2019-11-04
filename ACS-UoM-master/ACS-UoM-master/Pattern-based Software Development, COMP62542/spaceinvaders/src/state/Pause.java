package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game.Game;

public class Pause implements IGameState{

	private final Game game;

	public Pause(Game game) {
		this.game = game;
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		game.drawBoard(g);
		try {
            Thread.sleep(50);
        } catch (Exception e) {
        }
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.white);
		g.setFont(new Font( "SansSerif", Font.BOLD, 14));
		g.drawString(game.getPoint()+" PTS | LVL "+game.getLevel(), 10, 20);
		
		g.setColor(new Color(0,0,0,127));
		g.fillRect(0, 0, 1024, 768);
		
		g.setColor(Color.white);
		
		g.setFont(new Font( "SansSerif", Font.BOLD, 48));
		String s = "Pause";
		g.drawString(s, (int)((1024-g.getFontMetrics().stringWidth(s))/2), 280);
		
		g.setFont(new Font( "SansSerif", Font.BOLD, 20));
		s = "Press any key to resume";
		g.drawString(s, (int)((1024-g.getFontMetrics().stringWidth(s))/2), 380);
		s = "Press the arrow keys to move the player";
		g.drawString(s, (int)((1024-g.getFontMetrics().stringWidth(s))/2), 410);
		s = "Press space to shoot enemies";
		g.drawString(s, (int)((1024-g.getFontMetrics().stringWidth(s))/2), 440);
		
	}

	@Override
	public void changeState() {
		game.moveToPlay();
	}

}
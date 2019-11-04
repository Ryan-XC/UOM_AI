package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game.Game;

public class LevelUp implements IGameState {

	private final Game game;
	private String loading;
	private boolean levelUp;
	private int nextLevel = 0;

	public LevelUp(Game game) {
		this.game = game;
		this.loading = "-";
		this.levelUp = false;
		this.nextLevel = game.getLevel()+1;
	}

	@Override
	public void play() {
		if (game.isLastLevel() && !this.levelUp){
			//Congratulations
			game.moveToFinish();
		}
			
			game.getUi().drawUI();
			if (!this.levelUp){
				this.levelUp = true;
				game.newLevel();
			}
			
			if (this.loading.length()>=100)
			{
				try {
					Thread.sleep(3000);
				} 
				catch (Exception e) 
				{
					
				}
				game.moveToPlay();
			}
			
		
		
	}

	@Override
	public void draw(Graphics2D g) {
		if (this.loading.length()<100){
			this.loading += "---";
		}
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.BOLD, 14));
		g.drawString(game.getPoint() + " PTS | LVL " + game.getLevel(), 10, 20);

		g.setColor(new Color(0, 0, 0, 127));
		g.fillRect(0, 0, 1024, 768);

		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.BOLD, 48));
		String s = "Level up";
		g.drawString(s, (int) ((1024 - g.getFontMetrics().stringWidth(s)) / 2), 280);
		
		g.setFont(new Font("SansSerif", Font.BOLD, 20));
		s = "Level " + nextLevel;
		g.drawString(s, (int) ((1024 - g.getFontMetrics().stringWidth(s)) / 2), 380);

	}

	@Override
	public void changeState() {
		// TODO Auto-generated method stub

	}

}

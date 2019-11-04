package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import creation.Factory;
import creation.IGameObject;
import creation.MakeFactory;
import game.Game;
import game.IMainGame;
import game.UI;

public class Init implements IGameState{

	private final Game game;
	private boolean isInAnotherState = false;
	
	public Init(Game game) {
		this.game = game;
	}



	@Override
	public void changeState() {
		game.moveToPlay();
		this.isInAnotherState = true;
	}

	@Override
	public void play() {
		// call the factory to create the objects

		while(!(game.isAnyKeyPressed()) && !isInAnotherState){
			System.out.println("init ");
		}
		System.out.println("init finish");
	}

	@Override
	public void draw(Graphics2D g) {
		//print the "press a key to play message
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.white);
		
		g.setFont(new Font( "SansSerif", Font.BOLD, 48));
		String s = "Welcome to Pattern Invaders";
		g.drawString(s, (int)((1024-g.getFontMetrics().stringWidth(s))/2), 280);
		
		g.setFont(new Font( "SansSerif", Font.BOLD, 20));
		s = "Press any key to start";
		g.drawString(s, (int)((1024-g.getFontMetrics().stringWidth(s))/2), 380);
		s = "Press the arrow keys to move the player";
		g.drawString(s, (int)((1024-g.getFontMetrics().stringWidth(s))/2), 410);
		s = "Press space to shoot enemies";
		g.drawString(s, (int)((1024-g.getFontMetrics().stringWidth(s))/2), 440);
		
		//credits
		g.setFont(new Font( "SansSerif", Font.BOLD, 15));
		s = "Group 5";
		g.drawString(s, (int)((1024-g.getFontMetrics().stringWidth(s))/2), 530);
		
		g.setFont(new Font( "SansSerif", Font.PLAIN, 15));
		s = "Aurélie Bing Carlos";
		g.drawString(s, (int)((1024-g.getFontMetrics().stringWidth(s))/2), 550);
		s = "Martin Olivier Salim";
		g.drawString(s, (int)((1024-g.getFontMetrics().stringWidth(s))/2), 570);
	}
	
	

}

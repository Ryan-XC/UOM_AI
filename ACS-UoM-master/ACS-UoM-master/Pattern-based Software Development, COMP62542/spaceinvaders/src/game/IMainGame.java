package game;

import java.awt.Graphics;

import state.IGameState;

//Subject
public interface IMainGame {
	public IGameState getState();	
	public void moveBoard();
	public void drawBoard(Graphics g);
}

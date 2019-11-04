package state;
import java.awt.Graphics2D;


public interface IGameState {
		
	public void play();
	
	public void draw(Graphics2D g);
	
	public void changeState();

}

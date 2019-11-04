package creation;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Created by Martin on 21/04/2016.
 */
public interface IGameObject {
    public void move();
    public int checkCollision();
    public void draw(Graphics g);
	public void fire();
    
}


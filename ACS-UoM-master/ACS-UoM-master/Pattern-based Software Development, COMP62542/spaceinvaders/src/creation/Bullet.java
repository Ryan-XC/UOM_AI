package creation;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import movement.AbstractMovement;
import movement.BulletMovement;

/**
 * Created by Bing on 21/04/2016.
 */
public class Bullet extends BulletType  implements IGameObject{

	private int x;
	private int y;
	private Rectangle r;
	private int speed;
	private int direction;
	private boolean isAlive = true;
	private AbstractMovement movement;
	
    public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public AbstractMovement getMovement() {
		return movement;
	}

	public void setMovement(AbstractMovement movement) {
		this.movement = movement;
	}

	public void setR(Rectangle r) {
		this.r = r;
	}


	public Rectangle getR() {
		return r;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Bullet(BulletType bulletType, int direction) {
		super(bulletType.getType(), bulletType.getImage());

		this.r = new Rectangle(bulletType.getImage().getWidth(), bulletType.getImage().getHeight());
		this.direction = direction;
		this.movement = new BulletMovement(this);
	}

	@Override
	public void move() {

		movement.move(this.speed, 0, this.direction);
	}

	public void checkCollision(ArrayList<IGameObject> enemiesList) {
		this.r.setLocation(this.x, this.y);
		for (IGameObject enemy : enemiesList) {
			if (((Enemy) enemy).getR().intersects(this.r)) {
				this.setAlive(false);
			}
		}

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(this.getImage(), this.x, this.y, null);

	}

	@Override
	public int checkCollision() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}

}
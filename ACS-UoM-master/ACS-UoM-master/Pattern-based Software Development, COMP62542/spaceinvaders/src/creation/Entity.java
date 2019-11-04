package creation;

import movement.AbstractMovement;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Created by Martin on 21/04/2016.
 */
public abstract class Entity implements IGameObject {

	protected int x;
    protected int y;
    protected SpaceShip plane;
    protected String bulletType;
    protected ArrayList<Bullet> bulletList;
    protected Rectangle r;
    protected boolean isAlive = true;
    protected int bulletDir;
    protected int bulletSpeed;
    protected AbstractMovement movement;
    
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public ArrayList<Bullet> getBulletList() {
		return bulletList;
	}

	public void setBulletList(ArrayList<Bullet> bulletList) {
		this.bulletList = bulletList;
	}

	public Rectangle getR() {
		return r;
	}

	public void setR(Rectangle r) {
		this.r = r;
	}

	public SpaceShip getPlane() {
		return plane;
	}

	public void setPlane(SpaceShip plane) {
		this.plane = plane;
	}


	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;

		this.r.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.r.x = x;
	}

	@Override
	public void move(){
		for (Bullet temp: bulletList){
			temp.move();
		}
		
	}
	
	public void move(int speed, int xDirection, int yDirection){
		movement.move(speed, xDirection, yDirection);
	}
    
	@Override
	public void draw(Graphics g){
		for (Bullet temp: bulletList){
			if (temp.isAlive()){
				
				g.drawImage(temp.getImage(), temp.getX(), temp.getY(), null);
			}
		}
		if (this.isAlive) {
			g.drawImage(plane.getImage(), this.x, this.y, null);
		}
		
	}
	
	public void fire(){
		try {
			Bullet bullet = (Bullet)MakeFactory.getFactory().createBullet(this.bulletType, this.bulletDir);
			bullet.setX(this.getX()+( (int)(this.plane.getImage().getWidth()/2)) - (int)((bullet.getImage().getWidth())/2) );
			bullet.setY(this.getY()+15);
			bullet.setSpeed(this.bulletSpeed);
			bullet.setAlive(true);
			this.bulletList.add(bullet);
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}

}

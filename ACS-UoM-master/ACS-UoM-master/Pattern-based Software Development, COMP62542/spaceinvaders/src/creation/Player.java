package creation;

import movement.PlayerMovement;
import java.awt.Rectangle;
import java.util.ArrayList;


/**
 * Created by Aurelie on 25/04/2016.
 */
public class Player extends Entity {


	public boolean checkCollision(ArrayList<IGameObject> enemiesList) {
		
		for(IGameObject enemy:enemiesList){
			if(((Enemy) enemy).getR().intersects(this.r)){
				return false;
			}
			for(Bullet bullet:((Enemy) enemy).bulletList){
				if(bullet.isAlive() && bullet.getR().intersects(this.r)){
					bullet.setAlive(false);
					return false;
				}
			}
		}
		/*for(Bullet bullet:this.bulletList)
		{
			bullet.checkCollision();
		}*/
		return true;
	}

	public Player(SpaceShip plane, String bulletType, int x, int y) {
		super();
		this.x = x;
        this.y = y;
        this.bulletType = bulletType;
        this.plane = plane;
        this.movement = new PlayerMovement(this);
        this.bulletDir = -1;
        this.bulletSpeed = 10;
        this.bulletList = new ArrayList<Bullet>();
        this.r = new Rectangle(this.x,this.y,this.plane.getImage().getWidth(), this.plane.getImage().getHeight());
        
	}


	@Override
	public int checkCollision() {
		return 0;
	}

    
}
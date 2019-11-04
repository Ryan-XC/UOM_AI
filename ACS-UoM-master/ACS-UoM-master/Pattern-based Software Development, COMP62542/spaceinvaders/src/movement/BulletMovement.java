package movement;

import creation.Bullet;
import creation.IGameObject;
import creation.Player;

public class BulletMovement extends AbstractMovement {


    public BulletMovement(IGameObject moveObj) {
        this.moveObj = moveObj;
    }
	@Override
	public void move(int speed, int xDirection, int yDirection) {
		 if (moveObj instanceof Bullet) {
	            Bullet bullet = (Bullet) moveObj;
	            int next = bullet.getY() + (speed * yDirection);
	            if (next < 0 || next > 768)
	               bullet.setAlive(false);
	            bullet.setY(next);
	            bullet.getR().setLocation(bullet.getX(), bullet.getY());
	        }
		 

	}

}

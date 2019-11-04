package creation;

import movement.EnemyHorizontalVerticalMovement;
import movement.EnemyVerticalMovement;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Aurelie on 25/04/2016.
 */
public class Enemy extends Entity {
	
	private boolean winning = false;

	public boolean isWinning() {
		return winning;
	}

	public void setWinning(boolean winning) {
		this.winning = winning;
	}

	public Enemy(int level, SpaceShip plane, String bulletType, int x, int y) {
		super();
        this.x = x;
        this.y = y;
        this.bulletType = bulletType;
        this.bulletDir = 1;
        this.plane = plane;
        this.bulletSpeed = 10;
        if (level == 1){
            this.movement = new EnemyHorizontalVerticalMovement(this);        	
        }else if (level == 2){
            this.movement = new EnemyVerticalMovement(this);        	
        } else if (level == 3){
        	this.movement = new EnemyHorizontalVerticalMovement(this); 
        }
        this.bulletList = new ArrayList<Bullet>();
        this.r = new Rectangle(this.x, this.y, this.plane.getImage().getWidth(), this.plane.getImage().getHeight());
	}

	@Override
	public void move(){
		this.movement.move(1, 0, 4);
		for (Bullet temp: bulletList){
			temp.move();
		}
	}

	public int checkCollision(Player player) {
		int points = 0;
		if (this.isAlive) {
			if (player.bulletList != null) {
				for (Bullet bullet : player.bulletList) {
					if (bullet.isAlive() && bullet.getR().intersects(this.r)) {
						this.setAlive(false);
						bullet.setAlive(false);
						++points;
						break;
					}
				}
			}
		}
		
		for(Bullet enemyBullet: this.bulletList)
		{
			for(Bullet playerBullet: player.bulletList)
			{
				if(playerBullet.isAlive() && enemyBullet.isAlive() && playerBullet.getR().intersects(enemyBullet.getR()))
				{
					playerBullet.setAlive(false);
					enemyBullet.setAlive(false);
				}
			}
		}

		return points;
	}

	@Override
	public int checkCollision() {
		return 0;
	}
}
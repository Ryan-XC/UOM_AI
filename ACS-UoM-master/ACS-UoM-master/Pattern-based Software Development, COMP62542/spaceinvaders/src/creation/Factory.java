package creation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Martin on 21/04/2016.
 */
public class Factory implements IFactory {
	/**
	 * Factory Pattern. Create Object depend on different type.
	 *
	 * @return
	 */

	private final int ENEMY_PER_ROW = 8;
	private FlyWeightFactory fFactory;
	private BulletFactory bFactory;

	public Factory() {
		fFactory = new FlyWeightFactory();
		bFactory = new BulletFactory();
	}

	public IGameObject createPlayer(String playerSprite, String playerBullet) {
		Player p =  new Player((SpaceShip) fFactory.getPlaneFromFactory(playerSprite), 
				playerBullet, 500, 630);
		p.setX((int)((1024-p.getPlane().getImage().getWidth())/2));
		return p;
	}

	public ArrayList<IGameObject> createEnemy(int count, int level, String enemySprite, String enemyBullet) {
		ArrayList<IGameObject> enemies = new ArrayList<IGameObject>();
		
		if(level==1)
		{
			int x = 20;
			int y = 20;
			
			for (int i = 1; i <= count; ++i) {
				Enemy enemy = new Enemy(level, (SpaceShip) fFactory.getPlaneFromFactory(enemySprite),
						enemyBullet, x, y);
				enemies.add(enemy);
				x += enemy.plane.getImage().getWidth() + 5;
				if (i % ENEMY_PER_ROW == 0 && i != 0) {
					x = 20;
					y += enemy.plane.getImage().getHeight() + 5;
				}
			}
		}
		
		if (level==2)
		{
			Random rand = new Random();
			Enemy temp = new Enemy(level, (SpaceShip) fFactory.getPlaneFromFactory(enemySprite),enemyBullet, 0,0);
			int bound = 1024 / temp.getPlane().getImage().getWidth();
			int width = temp.getPlane().getImage().getWidth();
			int height = temp.getPlane().getImage().getHeight();
			System.out.println(bound);
			int y = 0;
			
			for (int i = 1; i <= count; ++i)
			{
				int x = rand.nextInt(bound);
				y = y - height;
				Enemy enemy = new Enemy(level, (SpaceShip) fFactory.getPlaneFromFactory(enemySprite),enemyBullet, x*width, y);
				enemies.add(enemy);
			}
		}
		
		if(level==3)
		{
			int x = 20;
			int y = 20;
			
			for (int i = 1; i <= count; ++i) {
				Enemy enemy = new Enemy(level, (SpaceShip) fFactory.getPlaneFromFactory(enemySprite),
						enemyBullet, x, y);
				enemies.add(enemy);
				x += enemy.plane.getImage().getWidth() + 5;
				if (i % ENEMY_PER_ROW == 0 && i != 0) {
					x = 20;
					y += enemy.plane.getImage().getHeight() + 5;
				}
			}
		}
		
		return enemies;
	}
	
	public IGameObject createBullet(String bulletType, int direction){
		return new Bullet(bFactory.getBulletTypeFromFactory(bulletType), direction);
	}

}

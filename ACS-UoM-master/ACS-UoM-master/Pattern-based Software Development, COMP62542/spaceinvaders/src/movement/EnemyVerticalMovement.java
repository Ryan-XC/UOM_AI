package movement;

import creation.Enemy;
import creation.IGameObject;

public class EnemyVerticalMovement extends AbstractMovement {

	public EnemyVerticalMovement(IGameObject moveObj) {
		this.moveObj = moveObj;
	}

	@Override
	public void move(int speed, int xDirection, int yDirection) {
		if (speed < 0) {
			speed *= -1;
		}
		if (moveObj instanceof Enemy) {
			Enemy enemy = (Enemy) moveObj;
			if (enemy.isAlive()) {
				int next = enemy.getY() + speed;
				if (next > 768 - enemy.getR().getHeight()) {
					enemy.setWinning(true);
					return;
				}
				enemy.setY(next);
			}

		}
	}

}

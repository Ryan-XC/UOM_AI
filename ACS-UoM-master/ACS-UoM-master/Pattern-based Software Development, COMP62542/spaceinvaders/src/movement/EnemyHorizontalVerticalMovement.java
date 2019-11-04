package movement;

import creation.Enemy;
import creation.IGameObject;

public class EnemyHorizontalVerticalMovement extends AbstractMovement {

	private final int InitialY;
	private static int eDirection;
	private static int eNextRow;

	static {
		eDirection = 1;
		eNextRow = 0;
	}
	
	public static void setStartRow(int row){
		eNextRow = row;
	}
	
	public static void setEDirection(int dir){
		eDirection = dir;
	}

	public EnemyHorizontalVerticalMovement(IGameObject moveObj) {
		this.moveObj = moveObj;
		if (moveObj instanceof Enemy) {
			this.InitialY = ((Enemy) moveObj).getY();
		} else {
			this.InitialY = 0;
		}
	}

	@Override
	public void move(int speed, int xDirection, int yDirection) {

		if (moveObj instanceof Enemy) {
			Enemy enemy = (Enemy) moveObj;
			if (enemy.isAlive()) {
				int next = enemy.getX() + (speed * eDirection);
				if (next < 0) {
					next = 0;
					eDirection *= -1;
					eNextRow += 30;
				}
				if (next > 1024 - enemy.getR().getWidth()) {
					next = (int) (1024 - enemy.getR().getWidth());
					eDirection *= -1;
					eNextRow += 30;
				}
				enemy.setX(next);

				next = this.InitialY + eNextRow;
				if (next > 768 - enemy.getR().getHeight()) {
					enemy.setWinning(true);
				}
				enemy.setY(next);
			}
		}
	}

}

package creation;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameBoard implements IGameObject {
	// TODO getter setter
	private ArrayList<IGameObject> enemies;
	private IGameObject player;

	public GameBoard(int level, String playerSprite,String enemySprite,int enemyCount,String playerBullet,String enemyBullet){
		enemies = new ArrayList<IGameObject>();
		Factory factory = (Factory)MakeFactory.getFactory();
		player = factory.createPlayer(playerSprite, playerBullet);
		enemies.addAll(factory.createEnemy(enemyCount, level, enemySprite, enemyBullet));
		
	}

	@Override
	public void move() {
		for (IGameObject ob : enemies) {
			ob.move();
		}
		player.move();
	}

	@Override
	public int checkCollision() {
		int points= 0;
		for (IGameObject ob : enemies) {
			points += ((Enemy) ob).checkCollision((Player) player);
			
		}
		if (!((Player) player).checkCollision(enemies)) {
			((Player) player).setAlive(false);
		}
		return points;
	}

	public void draw(Graphics g) {
		player.draw(g);
		for (IGameObject en : enemies) {
			en.draw(g);
		}
	}

	public ArrayList<IGameObject> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<IGameObject> enemies) {
		this.enemies = enemies;
	}

	public IGameObject getPlayer() {
		return player;
	}

	public void setPlayer(IGameObject player) {
		this.player = player;
	}

	public boolean isLevelOver() {
		for (IGameObject en : enemies) {
			if (((Enemy) en).isAlive())
				return false;
		}
		return true;
	}
	
	public boolean isGameOver(){
		for (IGameObject enemy : enemies) {
			if (((Enemy) enemy).isWinning())
				return true;
		}
		
		if(!((Player)this.player).isAlive)
			return true;
		
		return false;
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub

	}

}

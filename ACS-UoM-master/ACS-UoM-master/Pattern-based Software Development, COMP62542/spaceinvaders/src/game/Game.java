package game;
//Subject

import creation.GameBoard;
import creation.GameObjectType;
import creation.IGameObject;
import movement.EnemyHorizontalVerticalMovement;
import state.*;

import java.awt.*;

public class Game implements IMainGame {

	private IGameState state;
	private int point;
	private int level;
	private boolean isMoveLeft;
	private boolean isMoveRight;
	private boolean isFire;
	private boolean isPause;
	private boolean isQuit;
	private boolean isRestart;
	private boolean isAnyKeyPressed;
	private IGameObject gameBoard;
	private UI ui;
	private final int totalLevel = 2;

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public Game() {
		this.point = 0;
		this.level = 0;
		moveToInit();
		this.newLevel();
		this.ui = new UI(this);
	}

	public void start() {
		while (true) {
			System.out.println();
			this.state.play();

		}
	}

	public void moveToPlay() {
		this.state = new Play(this);
	}

	public void moveToInit() {
		this.state = new Init(this);
	}

	public void moveToPause() {
		this.state = new Pause(this);
	}

	public void moveToGameOver() {
		this.state = new GameOver(this);
	}

	public void moveToLevelUp() {
		this.state = new LevelUp(this);
	}

	public void moveToFinish() {
		this.state = new Finish(this);
	}

	public IGameState getState() {
		return state;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isMoveLeft() {
		return isMoveLeft;
	}

	public void setMoveLeft(boolean isMoveLeft) {
		this.isMoveLeft = isMoveLeft;
	}

	public boolean isMoveRight() {
		return isMoveRight;
	}

	public void setMoveRight(boolean isMoveRight) {
		this.isMoveRight = isMoveRight;
	}

	public boolean isFire() {
		return isFire;
	}

	public void setFire(boolean isFire) {
		this.isFire = isFire;
	}

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

	public boolean isAnyKeyPressed() {
		return isAnyKeyPressed;
	}

	public void setAnyKeyPressed(boolean isKeyPressed) {
		this.isAnyKeyPressed = isKeyPressed;
	}

	public boolean isRestart() {
		return isRestart;
	}

	public void setRestart(boolean isRestart) {
		this.isRestart = isRestart;
	}

	@Override
	public void moveBoard() {
		this.gameBoard.move();
	}

	public UI getUi() {
		return ui;
	}

	public GameBoard getGameBoard() {
		return (GameBoard) gameBoard;
	}

	@Override
	public void drawBoard(Graphics g) {
		((GameBoard) this.gameBoard).draw(g);

	}

	public void addPoints(int points) {
		this.point += points;
	}

	
	public boolean isQuit() {
		return isQuit;
	}

	public void setQuit(boolean isQuit) {
		this.isQuit = isQuit;
	}

	public void newLevel() {
		++this.level;
		// this.point += 100; //Bonus
		if (this.level <= this.totalLevel) {
			// Decide which features are in this level
			String player = "";
			String enemy = "";
			int enemyCount = 0;
			String playerBullet = "";
			String enemyBullet = "";
			if (this.level == 1) {
				player = GameObjectType.PLAYER_LEVEL_1;
				enemy = GameObjectType.ENEMY_LEVEL_1;
				enemyCount = 24;
				playerBullet = GameObjectType.BULLET_TYPE_1;
			} else if (this.level == 2 ) {
				player = GameObjectType.PLAYER_LEVEL_1;
				enemy = GameObjectType.ENEMY_LEVEL_2;
				enemyCount = 35;
				playerBullet = GameObjectType.BULLET_TYPE_1;
				enemyBullet = GameObjectType.BULLET_TYPE_2;
			} else if (this.level == 3) {
				player = GameObjectType.PLAYER_LEVEL_1;
				enemy = GameObjectType.ENEMY_LEVEL_1;
				enemyCount = 24;
				playerBullet = GameObjectType.BULLET_TYPE_1;
			} 
			this.gameBoard = new GameBoard(this.level, player, enemy, enemyCount, playerBullet, enemyBullet);
		}
	}

	public boolean isLastLevel() {
		if (this.level == this.totalLevel)
			return true;
		return false;
	}

	
	public void restart(){
		EnemyHorizontalVerticalMovement.setStartRow(0);
		EnemyHorizontalVerticalMovement.setEDirection(1);
		this.level = 0;
		this.point = 0;
		this.newLevel();
		this.moveToPlay();
	}
}

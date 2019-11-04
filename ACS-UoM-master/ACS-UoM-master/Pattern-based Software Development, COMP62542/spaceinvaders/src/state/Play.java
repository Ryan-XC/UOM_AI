package state;

import creation.Enemy;
import creation.Entity;
import creation.IGameObject;
import creation.Player;
import game.Game;

import java.awt.*;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

public class Play implements IGameState {

	private final Game game;
	private boolean isRunning;
	private long lastTimeFired;

	public Play(Game game) {
		this.game = game;
		isRunning = true;
		this.lastTimeFired = 0;
	}

	@Override
	public void changeState() {
		if (game.isPause()) {
			isRunning = false;
			game.moveToPause();
			// game.getState().play();
		}

	}

	@Override
	public void play() {
		long lastLoopTime = currentTimeMillis();
		// AbstractMovement playerMove = new
		// PlayerMovement(game.getGameBoard().getPlayer());

		
		while (isRunning) {
			Random rand = new Random();
			long delta = currentTimeMillis() - lastLoopTime;
			lastLoopTime = currentTimeMillis();
			// Main game loop

			if (game.isMoveLeft() && !game.isMoveRight()) {
				((Entity) game.getGameBoard().getPlayer()).move(15, -1, 0);
			}

			if (!game.isMoveLeft() && game.isMoveRight()) {
				((Entity) game.getGameBoard().getPlayer()).move(15, 1, 0);
			}

			if (game.isFire() && ((lastLoopTime - lastTimeFired) >= 200)) {
				game.getGameBoard().getPlayer().fire();
				game.setFire(false);
				lastTimeFired = currentTimeMillis();
			}

			if (game.getLevel() == 2) {
				int bound;
				for (bound = 0; bound < game.getGameBoard().getEnemies().size(); bound++) {
					Enemy en = (Enemy)game.getGameBoard().getEnemies().get(bound);
					if(en.getY() < 0)
						break;
				}

				int fire = bound > 0 ? rand.nextInt(bound) : rand.nextInt(1);
				Enemy en = (Enemy) game.getGameBoard().getEnemies().get(fire);
				if (en.isAlive() && en.getBulletList().size() < 1) {
					en.fire();
				}

			}

			// make everyone move
			game.moveBoard();

			// check collision
			game.addPoints(game.getGameBoard().checkCollision());

			if (game.getGameBoard().isLevelOver()) {
				game.moveToLevelUp();
				game.getUi().drawUI();
				break;
			}

			if (game.getGameBoard().isGameOver()) {
				game.moveToGameOver();
				game.getUi().drawUI();
				break;
			}

			// draw the ui
			game.getUi().drawUI();

			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}

		}

	}

	@Override
	public void draw(Graphics2D g) {
		// draw game board
		game.drawBoard(g);

		// draw pause instruction
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.BOLD, 14));
		String s = "Press Esc to pause";
		g.drawString(s, 1024 - g.getFontMetrics().stringWidth(s) - 10, 20);
		// draw lvl and points
		g.drawString(game.getPoint() + " PTS | LVL " + game.getLevel(), 10, 20);
	}

}

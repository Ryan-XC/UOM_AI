package creation;

import java.util.ArrayList;

/**
 * Created by Martin on 22/04/2016.
 */
public interface IFactory {
    /**
     * Interface for factory.
     * @return
     */
    IGameObject createPlayer(String playerSprite, String playerBullet);

    ArrayList<IGameObject> createEnemy(int count, int level, String enemySprite, String enemyBullet);
    
    IGameObject createBullet(String bulletType, int direction);
}

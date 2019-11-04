package movement;

import creation.IGameObject;
import creation.Player;

public class PlayerMovement extends AbstractMovement {

    public PlayerMovement(IGameObject moveObj) {
        this.moveObj = moveObj;
    }

    @Override
    public void move(int speed, int xDirection, int yDirection) {
        if (moveObj instanceof Player) {
            Player player = (Player) moveObj;
            int next = player.getX() + (speed * xDirection);
            if (next < 0)
                next = 0;
            if (next > 1024 - player.getPlane().getImage().getWidth())
                next = 1024 - player.getPlane().getImage().getWidth();
            player.setX(next);
            player.getR().x = next;
        }
    }
}

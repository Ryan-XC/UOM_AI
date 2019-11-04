package movement;

import creation.IGameObject;

public abstract class AbstractMovement {
	protected IGameObject moveObj;
	
	public abstract void move(int speed, int xDirection, int yDirection);

}

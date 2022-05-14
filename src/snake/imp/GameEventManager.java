package snake.imp;

public interface GameEventManager {
	public boolean handleCollision();
	public boolean handleMovement();
	public boolean handleTick();
	
	public void addCollisionListener(GameObject c);
	public void addMovementListener(Moveable m);
	public void addTickListener(Tickable t);
	public void setRenderer(GameRenderer2D<?> r);
	
	public boolean tick();
}

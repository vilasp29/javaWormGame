package snake.imp;

public interface Tickable {
	public void onTick();
	
	public void queueTickEvent(GameEvent e);
}

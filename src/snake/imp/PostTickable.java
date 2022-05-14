package snake.imp;

public interface PostTickable {
	public void onPostTick();
	
	public void queuePostTickEvent(GameEvent e);
}

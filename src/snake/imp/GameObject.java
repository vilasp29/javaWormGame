package snake.imp;

public interface GameObject extends Tickable, Collidable {
	public class EmptyGameObject implements GameObject {
		@Override public void onTick() {}
		@Override public void onCollide(Collidable collider) {}
		@Override public DirectionalVector2D getPos() {return null;}
		@Override public void queueTickEvent(GameEvent e) {};
		@Override
		public String toString() {
			return " ";
		}
	}
}

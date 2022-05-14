package snake.imp;

public interface Collidable {
	public void onCollide(Collidable collider);
	
	public DirectionalVector2D getPos();
}

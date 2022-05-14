package snake.game;

import snake.Main;
import snake.imp.Collidable;
import snake.imp.Direction;
import snake.imp.DirectionalVector2D;
import snake.imp.GameEvent;
import snake.imp.GameObject;

public class Segment implements GameObject {
	private DirectionalVector2D pos;
	private SnakeType type;
	private Snake parent;
	
	public Segment(DirectionalVector2D pos, SnakeType type, Snake parent) {
		this.pos = pos;
		this.setType(type);
		this.parent = parent;
	}
	
	public DirectionalVector2D getPos() {
		return pos;
	}
	
	public void setY(long y) {
		this.pos.setY(y);
	}
	
	public void setX(long x) {
		this.pos.setX(x);
	}
	
	public void setDir(Direction dir) {
		this.pos.setDir(dir);
	}

	public SnakeType getType() {
		return type;
	}

	public void setType(SnakeType type) {
		this.type = type;
	}
	
	public Snake getParent() {
		return parent;
	}

	public void onCollide(Collidable collider) {
		if (collider instanceof Segment) {
			Main.snakeGame.gameOver();
		}
		if (collider instanceof Apple) {
			getParent().queuePostTickEvent(SnakeEvents.GROW);
			((Apple) collider).queuePostTickEvent(SnakeEvents.APPLE_EATEN);
		}
	}
	
	@Override
	public String toString() {
		return type == SnakeType.BODY ? "=" : "@";
	}
	
	@Override public void onTick() {}
	@Override public void queueTickEvent(GameEvent e) {}
}

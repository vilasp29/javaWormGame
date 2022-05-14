package snake.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import snake.imp.Collidable;
import snake.imp.Direction;
import snake.imp.DirectionalVector2D;
import snake.imp.GameEvent;
import snake.imp.GameObject;
import snake.imp.PostTickable;

public class Apple implements GameObject, PostTickable {
	private Random random;
	private DirectionalVector2D pos;
	private List<SnakeEvents> tickQueue = new ArrayList<SnakeEvents>();
	private List<SnakeEvents> postTickQueue = new ArrayList<SnakeEvents>();
	
	public Apple() {
		this.random = new Random();
		this.pos = new DirectionalVector2D(random.nextInt(10), random.nextInt(10), Direction.NONE);
	}
	
	@Override 
	public void onTick() {
		tickQueue.forEach((t) -> {
			switch (t) {
			default: break;
			}
		});
		tickQueue.clear();
	}
	
	@Override 
	public void onPostTick() {
		postTickQueue.forEach((t) -> {
			switch (t) {
			case APPLE_EATEN : 
				this.random(); 
				break;
			default: break;
			}
		});
		postTickQueue.clear();
	}
	
	@Override 
	public void queueTickEvent(GameEvent e) {
		tickQueue.add((SnakeEvents) e);
	}
	
	@Override 
	public void queuePostTickEvent(GameEvent e) {
		postTickQueue.add((SnakeEvents) e);
	}
	
	public void random() {
		this.pos = new DirectionalVector2D(random.nextInt(10), random.nextInt(10), Direction.NONE);
	}

	@Override
	public void onCollide(Collidable collider) {
		if (collider instanceof Segment) {
			((Segment) collider).getParent().queuePostTickEvent(SnakeEvents.GROW);
			this.queuePostTickEvent(SnakeEvents.APPLE_EATEN);
		}
	}

	@Override
	public DirectionalVector2D getPos() {
		return pos;
	}
	
	@Override
	public String toString() {
		return "*";
	}
}

package snake.game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import snake.imp.Collidable;
import snake.imp.Controllable;
import snake.imp.Direction;
import snake.imp.DirectionalVector2D;
import snake.imp.GameEvent;
import snake.imp.GameObject;
import snake.imp.Moveable;
import snake.imp.PostTickable;

public class Snake implements GameObject, Controllable, Moveable, PostTickable {
	private SnakeEventManager manager;
	private List<Segment> segments = new ArrayList<>();
	private List<SnakeEvents> tickQueue = new ArrayList<>();
	private List<SnakeEvents> postTickQueue = new ArrayList<>();
	private Segment first;
	private Segment last;
	private String currentKeyDir = "none";
	private int lastIndex;
	
	public Snake(DirectionalVector2D startingPos, SnakeEventManager manager) {
		this.manager = manager;
		first = new Segment(startingPos, SnakeType.HEAD, this);
		last = first;
		lastIndex = 0;
		segments.add(first);
	}

	@Override
	public void onTick() {
		tickQueue.forEach((t) -> {
			switch (t) {
			case MOVE : move();
			default : break;
			}
		});
		tickQueue = new ArrayList<>();
	}
	
	@Override
	public void onPostTick() {
		postTickQueue.forEach((t) -> {
			switch (t) {
			case GROW : 
				grow();
				break;
			default : break;
			}
		});
		postTickQueue = new ArrayList<>();
	}
	
	public void grow() {
		last = new Segment(new DirectionalVector2D(last.getPos().getX() + -(last.getPos().getDir().getXIncrement()),
				last.getPos().getY() + -(last.getPos().getDir().getYIncrement()), last.getPos().getDir()), SnakeType.BODY, this);
		segments.add(lastIndex + 1, last);
		lastIndex += 1;
		manager.addCollisionListener(last);
		//lastIndex = segments.size();
	}
	
	private void UpdateLastIndex() {
		lastIndex--;
		if (lastIndex < 0) {
			lastIndex = segments.size() - 1;
		}
	}
	
	public void move() {
		Direction dir = first.getPos().getDir();
		//String input = scanner.nextLine();
		switch (currentKeyDir) {
			case "right" : first.setDir(dir = Direction.RIGHT); break;
			case "left" : first.setDir(dir = Direction.LEFT); break;
			case "down" : first.setDir(dir = Direction.DOWN); break;
			case "up" : first.setDir(dir = Direction.UP); break;
			default : break;
		}
		first.setType(SnakeType.BODY);
		last.setX(first.getPos().getX() + first.getPos().getDir().getXIncrement());
		last.setY(first.getPos().getY() + first.getPos().getDir().getYIncrement());
		first = last;
		first.setType(SnakeType.HEAD);
		first.setDir(dir);
		UpdateLastIndex();
		last = segments.get(lastIndex);
	}
	
	@Override
	public void onKeyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT: currentKeyDir = "right"; break;
		case KeyEvent.VK_LEFT: currentKeyDir = "left"; break;
		case KeyEvent.VK_UP: currentKeyDir = "up"; break;
		case KeyEvent.VK_DOWN: currentKeyDir = "down"; break;
		default: break;
		}
	}
	
	@Override
	public void queueTickEvent(GameEvent e) {
		tickQueue.add((SnakeEvents) e);
	}
	
	@Override
	public void queuePostTickEvent(GameEvent e) {
		postTickQueue.add((SnakeEvents) e);
	}
	
	@Override
	public void onMove() {
		//queueTickEvent(SnakeEvents.MOVE);
		move();
	}
	
	public Segment getFirst() {
		return first;
	}
	
	@Override
	@Deprecated
	public DirectionalVector2D getPos() {
		return null;
	}

	@Override
	public void onCollide(Collidable collider) {
		if (collider instanceof Apple) {
			queuePostTickEvent(SnakeEvents.GROW);
			((Apple) collider).queuePostTickEvent(SnakeEvents.APPLE_EATEN);
		}
	}
}

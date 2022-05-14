package snake.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import snake.imp.Collidable;
import snake.imp.GameEventManager;
import snake.imp.GameObject;
import snake.imp.GameObject.EmptyGameObject;
import snake.imp.GameRenderer2D;
import snake.imp.Moveable;
import snake.imp.PostTickable;
import snake.imp.Tickable;

public class SnakeEventManager implements GameEventManager {
	private Collidable[][] collisions = new Collidable[10][10];
	private List<Collidable> collisionListeners = new ArrayList<>();
	private List<Moveable> movementListeners = new ArrayList<>();
	private List<Tickable> tickListeners = new ArrayList<>();
	
	private GameRenderer2D<Collidable> renderer;

	@Override
	public boolean handleCollision() {
		for (int i = 0; i < collisions.length; i++) {
			Arrays.fill(collisions[i], null);
		}
		collisionListeners.forEach((t) -> {
			if (collisions[(int) t.getPos().getY()][(int) t.getPos().getX()] == null) {
				collisions[(int) t.getPos().getY()][(int) t.getPos().getX()] = t;
			} else {
				System.out.println(t + " collided with: " + collisions[(int) t.getPos().getY()][(int) t.getPos().getX()]);
				t.onCollide(collisions[(int) t.getPos().getY()][(int) t.getPos().getX()]);
			}
		});
		return true;
	}

	@Override
	public boolean handleMovement() {
		movementListeners.forEach(Moveable::onMove);
		return true;
	}
	
	@Override
	public boolean handleTick() {
		tickListeners.forEach(Tickable::onTick);
		return true;
	}
	
	public boolean handlePostTick() {
		tickListeners.forEach((t) -> {
			if (t instanceof PostTickable) {
				((PostTickable) t).onPostTick();
			}
		});
		return true;
	}
	
	public boolean handleRendering() {
		FillTypes(collisions);
		renderer.render(collisions);
		return true;
	}
	
	@Override
	public void addCollisionListener(GameObject c) {
		this.collisionListeners.add(c);
	}

	@Override
	public void addMovementListener(Moveable m) {
		this.movementListeners.add(m);
	}
	
	@Override
	public void addTickListener(Tickable t) {
		this.tickListeners.add(t);
	}
	
	public void setRenderer(GameRenderer2D<?> renderer) {
		this.renderer = (SnakeRenderer) renderer;
		this.renderer.init();
	}

	@Override
	public boolean tick() {
		boolean movement = handleMovement();
		boolean tick = handleTick();
		boolean collision = handleCollision();
		boolean postTick = handlePostTick();
		boolean rendering = handleRendering();
		return movement && tick && collision && postTick && rendering;
	}
	
	public void initialTick() {
		handleCollision();
		handleRendering();
	}
	
	private void FillTypes(Collidable[][] types) {
		for(int i=0; i<types.length; i++){
			for(int j=0; j<types[i].length; j++) {
				if (types[i][j] == null) {
					types[i][j] = new EmptyGameObject();
				}
			}
		}
	}
}

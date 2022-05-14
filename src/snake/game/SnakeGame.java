package snake.game;

import javax.swing.JFrame;
import javax.swing.JToggleButton;
import snake.imp.Direction;
import snake.imp.DirectionalVector2D;

public class SnakeGame {
	private Thread game;
	
	private SnakeEventManager manager;
	private SnakeController controller;
	private long score;
	private boolean paused = true;
	private boolean alive = true;
	private JFrame frame;
	private JToggleButton button;
	
	public SnakeGame() {
		this.manager = new SnakeEventManager();
		this.controller = new SnakeController();
		this.button = new JToggleButton("Pause");
		this.button.setBounds(0, 0, 0, 0);
		Snake snake = new Snake(new DirectionalVector2D(4, 4, Direction.RIGHT), manager);
		Apple apple = new Apple();
		SnakePixelRenderer renderer = new SnakePixelRenderer(new JFrame("Snake"));
		this.controller.addKeyListener(snake);
		this.frame = renderer.getFrame();
		this.frame.addKeyListener(controller);
		this.manager.addTickListener(snake);
		this.manager.addTickListener(apple);
		this.manager.addCollisionListener(snake.getFirst());
		this.manager.addCollisionListener(apple);
		this.manager.addMovementListener(snake);
		this.manager.setRenderer(renderer);
		this.game = new Thread(new Runnable() {
			@Override
			public void run() {
				manager.initialTick();
				while (alive) {
					if (!paused) {
						manager.tick();
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	public boolean startGame() {
		try {
			game.start();
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void gameOver() {
		System.out.print("GAME OVER");
		this.alive = false;
	}
	
	public void togglePause() {
		paused = !paused;
	}
}

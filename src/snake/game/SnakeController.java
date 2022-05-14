package snake.game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import snake.Main;
import snake.imp.Controllable;
import snake.imp.GameController;

public class SnakeController implements GameController {
	private List<Controllable> keyListeners = new ArrayList<>();
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyListeners.forEach((c) -> {
			c.onKeyPressed(e);
		});
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Main.snakeGame.togglePause();
		}
	}
	
	public void addKeyListener(Controllable c) {
		keyListeners.add(c);
	}
}

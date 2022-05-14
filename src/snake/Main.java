package snake;

import snake.game.SnakeGame;

public final class Main {
	public static SnakeGame snakeGame;
	
	public static void main(String[] args) {
		snakeGame = new SnakeGame();
		snakeGame.startGame();
	}
}

package snake.imp;

public enum Direction {
	NONE(0, 0),
	UP(0, -1),
	RIGHT(1, 0),
	DOWN(0, 1),
	LEFT(-1, 0);
	
	private final int x;
	private final int y;
	
	private Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getXIncrement() {
		return x;
	}
	
	public int getYIncrement() {
		return y;
	}
}

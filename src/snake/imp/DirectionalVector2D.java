package snake.imp;

public class DirectionalVector2D {
	private long x;
	private long y;
	private Direction dir;
	
	public DirectionalVector2D(long x, long y, Direction dir) {
		this.setX(x);
		this.setY(y);
		this.setDir(dir);
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}	
}

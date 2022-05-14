package snake.imp;

public interface GameRenderer2D<T> {
	default public void init() {}
	
	public void render(T[][] toRender);
}

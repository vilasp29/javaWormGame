package snake.game;

import snake.imp.Collidable;

public class SnakeTextRenderer extends SnakeRenderer {
	@Override
	public void render(Collidable[][] toRender) { 
		System.out.println("/----------\\");
		for (Collidable[] j : toRender) {
			System.out.print("|");
			for(int i=0; i<j.length; i++){
				System.out.print(j[i]);
			}
			System.out.print("|\n");
		}
		System.out.println("\\----------/");
	}
}

package snake.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import snake.imp.Collidable;
import snake.imp.Direction;
import snake.imp.DirectionalVector2D;

public class SnakePixelRenderer extends SnakeRenderer {
	private final BufferedImage CLEAR = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
	
	private JFrame frame;
	private BufferedImage pixels;
	
	public SnakePixelRenderer(JFrame frame) {
		pixels = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
		this.frame = frame;
		this.frame.add(new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
	            Graphics2D g2d = (Graphics2D) g.create();
	            g2d.drawImage(pixels, 0, 0, this);
	            g2d.dispose();
			}
		});
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.pack();
		this.frame.setVisible(true);

	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	@Override
	public void render(Collidable[][] toRender) {
		pixels.setData(CLEAR.getData());
		Graphics2D g = pixels.createGraphics();
		g.setColor(Color.BLACK);
		g.drawRect(19, 19, 710, 710);
		for (Collidable[] c1 : toRender) {
			for (Collidable c : c1) {
				if (c instanceof Segment) {
					DirectionalVector2D pos = ((Segment) c).getPos();
					if (((Segment) c).getType() == SnakeType.HEAD) {
						g.setColor(new Color(0, 200, 0));
						drawRect((int) pos.getX() + 1, (int) pos.getY() + 1, g); 
					} else {
						g.setColor(Color.GREEN);
						drawRect((int) pos.getX() + 1, (int) pos.getY() + 1, g); 
						drawConnector((int) pos.getX() + 1, (int) pos.getY() + 1, ((Segment) c).getPos().getDir(), g);
					}
				} 
				if (c instanceof Apple) {
					g.setColor(Color.RED);
					drawRect((int) ((Apple) c).getPos().getX() + 1, (int) ((Apple) c).getPos().getY() + 1, g);
				}
			}
		}
		g.dispose();
		frame.repaint();
	}
	
	private void drawRect(int x, int y, Graphics g) {
		g.drawRect(((x+1) * 20) + (x-1) * 50, ((y+1) * 20) + ((y-1) * 50), 40, 40);
		g.fillRect(((x+1) * 20) + (x-1) * 50, ((y+1) * 20) + ((y-1) * 50), 40, 40);
	}
	
	private void drawConnector(int x, int y, Direction dir, Graphics g) {
		int width = dir == Direction.UP || dir == Direction.DOWN ? 40 : 29;
		int height = dir == Direction.UP || dir == Direction.DOWN ? 29 : 40;
		int var1 = dir == Direction.LEFT ? 29 : (dir == Direction.RIGHT ? 40 : 0);
		int var2 = dir == Direction.UP ? 29 : (dir == Direction.DOWN ? 40 : 0);;
		g.drawRect((x+1) * 20 + (x-1) * 50 + dir.getXIncrement() * var1, 
				(y+1) * 20 + (y-1) * 50 + dir.getYIncrement() * var2, width, height);
		g.fillRect((x+1) * 20 + (x-1) * 50 + dir.getXIncrement() * var1, 
				((y+1) * 20) + ((y-1) * 50) + dir.getYIncrement() * var2, width, height);
	}
}

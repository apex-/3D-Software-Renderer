package renderer.test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JPanel;

public class DirectDrawPanel extends JPanel {

	private BufferedImage bufferedImage;
	
	public DirectDrawPanel(int width, int height) {
		
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		this.fillCanvas(Color.BLACK);
		
		
		
		
		Canvas c = new Canvas();
		c.createBufferStrategy(1);
		Graphics drawGraphics = c.getBufferStrategy().getDrawGraphics();
		
	}
	
	/**
	 * Returns the preferred size of this panel. This method is needed
	 * otherwise the Window.pack() method will shrink the window to practically zero
	 */
	public Dimension getPreferredSize() {
	        return new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight());
	}
	
	/**
	 * Draws the canvas object holding the BufferedImage
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(bufferedImage, null, null);
		//byte[] data = ((DataBufferByte)canvas.getRaster().getDataBuffer()).getData();
	}
	
	
	public void swapBuffers() {
		repaint();
	}
	
	
	public void setPixel(int x, int y, byte r, byte g, byte b, byte a) {
		byte[] data = ((DataBufferByte)bufferedImage.getRaster().getDataBuffer()).getData();
		int idx = y * bufferedImage.getHeight() + x;
		//data[idx] = a;
//		data[idx * 4 + 2] = r;
//		data[idx * 4 + 1] = g;
//		data[idx * 4] = b;
		
		 bufferedImage.setRGB(x, y, Color.WHITE.getRGB());
	}
	
	
	public void clear() {
		this.fillCanvas(Color.BLACK);
	}
	
	public void fillCanvas(Color c) {
		int color = c.getRGB();
	    for (int x = 0; x < bufferedImage.getWidth(); x++) {
	        for (int y = 0; y < bufferedImage.getHeight(); y++) {
	            bufferedImage.setRGB(x, y, color);
	        }
	    }
	}
	
}

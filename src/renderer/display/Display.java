package renderer.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JFrame;

public class Display extends Canvas {

	int width;
	int height;

	private byte[] data;

	private BufferedImage bufferedImage;
	private BufferStrategy bufferStrategy;
	private Graphics drawGraphics;

	/**
	 * Creates a new {@link JFrame} and an associates this {@link Display}
	 * object to the frame as it extends a {@link Canvas}.
	 * It also creates an {@link BufferedImage} which holds 
	 * a data buffer which is the target for the rendered image.
	 * It also creates a {@link BufferStrate}
	 * 
	 * @param width the width of the render window in [pixels]
	 * @param height the height of the render window in [pixels]
	 */
	public Display(int width, int height, String title) {

		this.width = width;
		this.height = height;
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		this.createBufferStrategy(1);
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		bufferStrategy = this.getBufferStrategy();
		data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
		
		JFrame frame = new JFrame(title);
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
	
	
	/**
	 * Returns a new {@link RenderContext} containing the reference
	 * to the frame back-buffer and the z-buffer for instance
	 * 
	 * @return a new {@link RenderContext} containing the reference
	 * to the back-buffer and the z-buffer for instance
	 */
	public RenderContext getRenderContext() {
		
		return new RenderContext(width, height, this.data);
	}

	public void clear() {
		
		// Creating a new BufferedImage and setting the alpha value seems to be faster
		// than resetting all elements
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
		
		for (int i=0; i< data.length; i+=4) {
				data[ i] = (byte) 0xFF;
		}
	}

	/**
	 * Renders the image by drawing the
	 */
	public void updateAndRender() {

		// Render single frame
		do {
			// The following loop ensures that the contents of the drawing
			// buffer are consistent in case the underlying surface was recreated
			do {
				// Get a new graphics context every time through the loop
				// to make sure the strategy is validated
				drawGraphics = bufferStrategy.getDrawGraphics();
				data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
				drawGraphics.drawImage(bufferedImage, 0, 0, width, height, null);
				// Dispose the graphics
				drawGraphics.dispose();
				// Repeat   rendering if the drawing buffer contents
				// were restored
			} while (bufferStrategy.contentsRestored());
			// Display the buffer
			bufferStrategy.show();
			// Repeat the rendering if the drawing buffer was lost
		} while (bufferStrategy.contentsLost());
	}

}

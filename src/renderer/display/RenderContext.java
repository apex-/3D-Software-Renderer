package renderer.display;

public class RenderContext {
	
	
	private int width;
	private int height;
	private byte[] image;
	
	/**
	 * Creates a {@link RenderContext}
	 * 
	 * @param width the width of the rendered image in [pixels]
	 * @param height the height of the rendererd image in [pixels]
	 */
	public RenderContext(int width, int height, byte[] image) {
		
		this.width = width;
		this.height = height;
		this.image = image;
	}
	

	/**
	 * Sets a single pixel of the image (back-buffer)
	 * 
	 * @param x the x coordinate in pixels
	 * @param y the y coordinate in pixels
	 * @param a the alpha channel
	 * @param r the red channel
	 * @param g the green channel
	 * @param b the blue channel
	 */
	public void setPixel(int x, int y, byte a, byte r, byte g, byte b) {
		
		int idx = y * width + x;
		image[idx * 4] = a;
		image[idx * 4 + 1] = b;
		image[idx * 4 + 2] = g;
		image[idx * 4 + 3] = r;
	}
	
}

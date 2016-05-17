package renderer.rasterize;

import renderer.geometry.Vertex;

public class Edge {

	// input
	private Vertex v1, v2;
	
	// precalculated values
	private int yStart, yEnd;
	private float dxdy;
	
	// re-calculated for every call to step()
	private float currentX;
	
	
	/**
	 * Creates an Edge of a primitive
	 * 
	 * @param v1 the vertex with the lower (or equal) value of the y value 
	 * @param v2 the vertex with the larger (or equal) value of the y value	 
	 */
	public Edge(Vertex v1, Vertex v2) {
		
		this.v1 = v1;
		this.v2 = v2;
		
		float xDist = v2.getPos().GetX() - v1.getPos().GetX();
		float yDist = v2.getPos().GetY() - v1.getPos().GetY();
		if (yDist <= 0) {
			return;
		}
		
		this.yStart = (int) Math.ceil(v1.getPos().GetY());
		this.yEnd = (int) Math.ceil(v2.getPos().GetY());
		
		float ySubpixelOffset = yStart - v1.getPos().GetY();
		this.dxdy = xDist / yDist;
		this.currentX =  v1.getX() + ySubpixelOffset * dxdy;
		
		// TODO: Will be required later for the texture and depth information etc.
		// float xPrestep = currentX - v1.getX();
	}
	
	
	/**
	 * Re-calculates all values for a step in the (positive) y-direction.
	 */
	public void step() {
		
		this.currentX += this.dxdy;
	}


	/**
	 * Returns the first y coordinate corresponding to this {@link Edge}
	 * @return the first y coordinate corresponding to this {@link Edge}
	 */
	public int getYStart() {
		return yStart;
	}


	/**
	 * Returns the last y coordinate corresponding to this {@link Edge}
	 * @return the last y coordinate corresponding to this {@link Edge}
	 */
	public int getYEnd() {
		return yEnd;
	}


	/**
	 * Returns the X position corresponding to a particular y position. The y position
	 * is shifted with every call to {@link #step()}
	 * @return the X position corresponding to a particular y position
	 */
	public float getCurrentX() {
		return currentX;
	}

}

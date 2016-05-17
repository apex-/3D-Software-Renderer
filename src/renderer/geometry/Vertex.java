package renderer.geometry;

import renderer.math.Vector4f;

public class Vertex {

	private Vector4f pos;
	private Vector4f normal;
	private Vector4f textureCoord;
	
	
	public Vertex(float x, float y, float z, float w) {
		this.pos = new Vector4f(x, y, z, w);
	}
	
	/** 
	 * Returns the vertex position 
	 * @return the vertex position 
	 */
	public Vector4f getPos() {
		return pos;
	}
	
	/**
	 * Returns the vertex normal
	 * @return the vertex normal
	 */
	public Vector4f getNormal() {
		return normal;
	}
	
	/**
	 * Returns the texture coordinates
	 * @return the texture coordinates
	 */
	public Vector4f getTextureCoord() {
		return textureCoord;
	}
	
	
	public float getX() {
		return this.getPos().GetX();
	}
	
	public float getY() {
		return this.getPos().GetY();
	}
	
	public float getZ() {
		return this.getPos().GetZ();
	}
}

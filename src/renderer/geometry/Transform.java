package renderer.geometry;

import renderer.math.Matrix4f;
import renderer.math.Quaternion;
import renderer.math.Vector4f;

public class Transform {
	
	private Vector4f pos;
	private Quaternion rot;
	private Vector4f scale;
	
	
	public Transform(Vector4f pos) {
		this.pos = pos;
		this.rot = new Quaternion(0,0,0,1);
		this.scale = new Vector4f(1,1,1,1);
	}
	
	public Transform(Vector4f pos, Quaternion rot, Vector4f scale) {
		this.pos = pos;
		this.rot = rot;
		this.scale = scale;
	}
	
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.GetX(), pos.GetY(), pos.GetZ());
		Matrix4f rotationMatrix = rot.ToRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.GetX(), scale.GetY(), scale.GetZ());

		return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
	}
	
	/** Returns the position */
	public Vector4f getPos() {
		return pos;
	}
	
	/**
	 * Sets the position
	 * @param pos the position
	 */
	public void setPos(Vector4f pos) {
		this.pos = pos;
	}
	/**
	 * Returns the rotation quaternion
	 * @return the rotation quaternion
	 */
	public Quaternion getRot() {
		return rot;
	}
	
	/**
	 * Sets the rotation quaternion
	 * @param rot the rotation quaternion
	 */
	public void setRot(Quaternion rot) { 
		this.rot = rot;
	}
	
	/**
	 * Returns the scale (vector)
	 * @return the scale (vector)
	 */
	public Vector4f getScale() {
		return scale;
	}
	
	/**
	 * Sets the scale (vector)
	 * @param scale the scale (vector)
	 */
	public void setScale(Vector4f scale) {
		this.scale = scale;
	}
}
